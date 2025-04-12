package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.navigation.Navigator;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.users.model.RoleType;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@RequestScoped
public class MeasurementSessionList extends Navigator {

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	protected MeasurementSessionFilter measurementSessionFilter;
	
	@Inject
	private MeasurementSessionPrint measurementSessionPrint;
	
	//
	// EJB injections
	//
	@Inject
	private MeasurementSessionDao measurementSessionDao;
	
	@Inject
	private WoodElementDao woodElementDao;
	
	//
	// Local attributes
	//
	private String selection;
	private String woodElementId;
	private List<MeasurementSession> measurementSessionResults;
	private List<Viewer> selectedReports;
	private Integer itemCount;
	
	@PostConstruct
	public void init() {
		setNavigationStatus( measurementSessionFilter );
		refreshCurrentPage();
	}
	
	@Produces @Named( "measurementSessionResults" ) 
	public List<MeasurementSession> getResults() {
		if ( measurementSessionResults == null ) {
			initMeasurementSessionResults();
		}
		return measurementSessionResults;
	}
	
	public void initSelectedReports(MeasurementSession measurementSession) {
		selection = measurementSession.getId().toString();
		selectedReports = measurementSessionPrint.initSelectedReports(measurementSessionDao.findById(measurementSession.getId()));
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
	public boolean hasConcluded( MeasurementSession measurementSession ) {
		if ( measurementSession == null ) return false;
		
		return MeasurementSessionStatus.CONCLUDED.equals( measurementSession.getStatus() );
	}
	public boolean hasReport( MeasurementSession measurementSession ){
		if ( measurementSession == null ) return false;
		
		return ( hasModify(measurementSession) || hasConcluded(measurementSession) ); //|| hasResume(measurementSession) );
	}
	
	public String getRowStyleClasses() {
		return MeasurementSessionRowStyleHelper.getRowStyleClasses( getResults() );
	}
	
	public String getPopupContent(MeasurementSession e) {
		StringBuilder sb = new StringBuilder(e.getSurveySchedule().getAgenda().toString());
		
		if( !e.getStatus().equals(MeasurementSessionStatus.TODO) ) {
			sb.append(" ")
				.append("Author: ")
				.append(e.getAuthor().getName())
				.append(" ")
				.append(e.getAuthor().getSurname());
		}
		
		return sb.toString();
	}
	
	public boolean isRemovable( MeasurementSession ex ) {
		return ex.getStatus().equals(MeasurementSessionStatus.TODO);
	}

	public Boolean checkHistory( Long pid ){
		Set<Agenda> agendas = loggedUser.getAgendas();
		
		Set<MeasurementSessionStatus> statuses = new HashSet<MeasurementSessionStatus>(
				Arrays.asList(
						MeasurementSessionStatus.SUSPENDED,
						MeasurementSessionStatus.DONE, 
						MeasurementSessionStatus.CONCLUDED));
		
		return measurementSessionDao.hasWoodElementHistory( pid, statuses, agendas );
	}
	
	public boolean checkRoleFor( String operation ) {
		
		switch ( operation ) {
		case "add-new":
		case "edit":
		case "delete":
			return loggedUser.hasRole( RoleType.EXAMINATION_EDITOR );
		case "recover":
			return loggedUser.hasRole( RoleType.EXAMINATION_RESCUER );
			
		default:
			return false;
		}
	}
	
	
	//
	// navigation methods
	//
	public String addNew() {
		conversation.begin();
		return "add-new";
	}	
	
	public String run( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "run";
	}	
	
	public String edit( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "edit";
	}
	
	public String delete( Long id ) {
		measurementSessionDao.deleteById( id );
		
		facesContext.addMessage(null,
                new FacesMessage(
                		FacesMessage.SEVERITY_INFO, 
                		"Visit successfully deleted!", null));
		facesContext.getExternalContext().getFlash().setKeepMessages(true);		
		
		return "delete";
	}
	
	public String history( Long pid ){
		woodElementId = Long.toString( pid );
		
		return "history";
	}
	
	
	//
	// get methods
	//
	
	public WoodElement getLastWoodElementVersion( Long pid ) {
		return woodElementDao.findLastVersionById( pid );
	}
	
	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = measurementSessionDao.count( measurementSessionFilter );
		}
		return itemCount;
	}
	
	public String getSelection() {
		return selection;
	}
	
	public String getWoodElementId() {
		return woodElementId;
	}
	
	public List<Viewer> getSelectedReports(){
		if(selection != null && !"".equals(selection.trim())) {
			selectedReports = measurementSessionPrint.getReports(measurementSessionDao.findById(Long.parseLong(selection)));
		}
		
		return selectedReports;
	}
	
	//
	// private methods
	//
	private Boolean checkRecoverability(MeasurementSession measurementSession) {
		if ( measurementSession.getAuthor() == null )
			return false;
		else 
			return measurementSession.getAuthor().getUserid().equals( loggedUser.getUsername() ) 
					|| checkRoleFor( "recover" );
	}
	
	private void initMeasurementSessionResults() {
		if ( getItemCount().intValue() == 0 ) { 
			measurementSessionResults =  new ArrayList<MeasurementSession>();
		} else {
			measurementSessionResults = measurementSessionDao.find( measurementSessionFilter,  getOffset(),  getLimit() );
		}
	}
}
