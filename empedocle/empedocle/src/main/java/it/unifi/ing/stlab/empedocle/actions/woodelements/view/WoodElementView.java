package it.unifi.ing.stlab.empedocle.actions.woodelements.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.actions.health.measurementSession.MeasurementSessionPrint;
import it.unifi.ing.stlab.empedocle.actions.health.measurementSession.MeasurementSessionRowStyleHelper;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.model.health.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.factquery.dao.FactQueryConstructor;
import it.unifi.ing.stlab.navigation.Navigator;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.users.model.RoleType;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.model.Viewer;

@Named
@ViewScoped
public class WoodElementView extends Navigator {
	
	//
	// CDI injections
	//
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject 
	protected WoodElementMeasurementSessionFilter measurementSessionFilter;
	
	@Inject
	private MeasurementSessionPrint measurementSessionPrint;

	@Inject
	private FactQueryConstructor queryConstructor;
	
	//
	// HttpParam injections
	//
	@Inject @HttpParam("id")
	private String id;
	
	@Inject @HttpParam("from")
	private String from;
	
	@Inject @HttpParam("eid")
	private String measurementSessionId;
	
	//
	// EJB injections
	//
	@Inject
	private MeasurementSessionDao measurementSessionDao;
	
	@Inject
	private WoodElementDao woodElementDao;
	
	@Inject
	private ViewerDao viewerDao;
	
	//
	// Local attributes
	//
	private WoodElement current;

	// Attributes for list
	private String selection;
	
	// Attribute for merge
	private List<WoodElement> matchingWoodElements;
//
	private List<Viewer> factPanels;
	private List<Viewer> reports;


	@PostConstruct
	public void init() {
		current = wood_elementDao.findById( Long.parseLong( id ) );

		initFilter();
		setNavigationStatus( measurementSessionFilter );
		refreshCurrentPage();
		
		initFactPanels();
		initMatchingWoodElements();
	}

	@Produces @RequestScoped @WoodElementMeasurementSessionResults @Named( "wood_elementMeasurementSessionResults" ) 
	public List<MeasurementSession> getResults() {
		if ( getItemCount().intValue() == 0 ) 
			return new ArrayList<MeasurementSession>();
		return measurementSessionDao.find( measurementSessionFilter,  getOffset(),  getLimit() );
	}
	
	public boolean hasView( MeasurementSession measurementSession ){
		if ( measurementSession == null ) return false;
		
		return ( hasDone(measurementSession) || hasConcluded(measurementSession) || hasSuspended( measurementSession ));
	}
	
	public boolean hasReport( MeasurementSession measurementSession ){
		if ( measurementSession == null ) return false;
		
		return ( hasDone(measurementSession) || hasConcluded(measurementSession) );
	}
	
	public boolean checkRoleFor( String operation ) {
		
		switch ( operation ) {
		case "edit":
		case "delete":
			return loggedUser.hasRole( RoleType.PATIENT_EDITOR ) || loggedUser.hasRole(RoleType.ADMINISTRATOR);
			
		case "merge":
			return loggedUser.hasRole( RoleType.PATIENT_MERGER );
			
		case "recover":
			return loggedUser.hasRole( RoleType.EXAMINATION_RESCUER );			
			
		default:
			return false;
		}
	}
	
	public boolean hasStart( MeasurementSession measurementSession ) {
		if ( measurementSession == null ) return false;
		
		return MeasurementSessionStatus.TODO.equals( measurementSession.getStatus() ) &&
				SurveyScheduleStatus.ACCEPTED.equals( measurementSession.getSurveySchedule().getStatus() );
	}
	public boolean hasModify( MeasurementSession measurementSession ) {
		if ( measurementSession == null ) return false;
		
		return MeasurementSessionStatus.DONE.equals( measurementSession.getStatus() );
	}
	public boolean hasResume( MeasurementSession measurementSession ) {
		if ( measurementSession == null ) return false;
		
		return MeasurementSessionStatus.SUSPENDED.equals( measurementSession.getStatus() );
	}
	public boolean hasRecover( MeasurementSession measurementSession ) {
		if ( measurementSession == null ) return false;
		
		return MeasurementSessionStatus.RUNNING.equals( measurementSession.getStatus() ) && checkRecoverability(measurementSession);
	}

	
	//
	// navigation methods
	//
	public String merge( Long other ) {
		WoodElement result = wood_elementDao.mergeWoodElements( getCurrent().getId(), other,
				loggedUser.getUser() );

		return "wood_element-view?faces-redirect=true&from=wood_element-list&id=" + result.getId();
	}
	
	public String run( Long id ) {
		selection = Long.toString( id );
		conversation.begin();
		return "run";
	}
	
	public void initReports( Long id ) {
		selection = Long.toString( id );
		reports = measurementSessionPrint.initSelectedReports( measurementSessionDao.findById( id ) );
	}
	
	
	//
	// get methods
	//
	public String getId() {
		return id;
	}
	
	public String getSelection() {
		return selection;
	}	
	
	public String getFrom() {
		return from;
	}
	
	public String getMeasurementSessionId() {
		return measurementSessionId;
	}
	
	public WoodElement getCurrent() {
		return current;
	}
	
	public List<Viewer> getReports(){
		return reports;
	}
	
	public String getRowStyleClasses() {
		return MeasurementSessionRowStyleHelper.getRowStyleClasses( getResults() );
	}
	
	public String getRowStyleClass( MeasurementSession measurementSession ) {
		return MeasurementSessionRowStyleHelper.getRowStyleClass( measurementSession );
	}
	
	public List<Viewer> getFactPanels() {
		return factPanels;
	}

	public List<WoodElement> getMatchingWoodElements() {
		return matchingWoodElements;
	}
	
	@Override
	public Integer getItemCount() {
		return measurementSessionDao.count( measurementSessionFilter );
	}
	
	//
	// private methods
	//
	private boolean hasDone( MeasurementSession measurementSession ) {
		return MeasurementSessionStatus.DONE.equals( measurementSession.getStatus() );
	}
	private boolean hasConcluded( MeasurementSession measurementSession ) {
		return MeasurementSessionStatus.CONCLUDED.equals( measurementSession.getStatus() );
	}
	private boolean hasSuspended( MeasurementSession measurementSession ) {
		return MeasurementSessionStatus.SUSPENDED.equals( measurementSession.getStatus() );
	}
	
	private Boolean checkRecoverability( MeasurementSession measurementSession ) {
		if ( measurementSession.getAuthor() == null )
			return false;
		else 
			return measurementSession.getAuthor().getUserid().equals( loggedUser.getUsername() ) 
					|| checkRoleFor( "recover" );
	}
	
	private void initMatchingWoodElements() {
		matchingWoodElements = wood_elementDao.findForMerge( current.getName(), current.getSurname(), current.getId() );
	}
	
	private void initFactPanels() {
		queryConstructor.addAdditionalParams( "pid", Long.parseLong( id ) );
		queryConstructor.addAdditionalParams( "notStatus", FactStatus.REFUSED );
		queryConstructor.addAdditionalParams( "agendas", loggedUser.getAgendas() );
		queryConstructor.addAdditionalParams( "contextStatuses", Arrays.asList( MeasurementSessionStatus.DONE,
																				MeasurementSessionStatus.CONCLUDED ) );
		factPanels = new ArrayList<Viewer>();
		
		//FIXME lo lascio così fino alla decisione di metterlo in un dao
		List<Long> factPanelsId = entityManager.createQuery(
				" select vu.viewer.id from ViewerUse vu"
				+ " where vu.context = :context"
				+ " and vu.qualification.id = :qualificationId ", Long.class )
				.setParameter( "context", MeasurementSessionTypeContext.HIGHLIGHTS )
				.setParameter( "qualificationId", loggedUser.getUserQualification().getId() )
				.getResultList();
		
		for ( Long id : factPanelsId ) {
			factPanels.add( viewerDao.fetchById( id ) );
		}
	}
	
	private void initFilter() {
		measurementSessionFilter.setWoodElementId( Long.parseLong( id ) );
	}
}
