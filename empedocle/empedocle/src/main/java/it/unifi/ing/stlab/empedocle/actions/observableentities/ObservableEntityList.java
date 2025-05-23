package it.unifi.ing.stlab.empedocle.actions.observableentities;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.*;

import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.dao.messages.MessageDao;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.navigation.Navigator;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.users.model.RoleType;

@Named
@RequestScoped
public class ObservableEntityList extends Navigator {

	private static final String ENROLLING_FILTER_NAME = "Visit for Agenda:";
	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;

	@Resource
	private UserTransaction utx;
	
	@Inject 
	protected ObservableEntityFilter observableEntityFilter;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private Conversation conversation;
	
	//
	// EJB injections
	//
	@Inject
	private ObservableEntityDao observableEntityDao;
	
	@Inject
	private MeasurementSessionDao measurementSessionDao;

	@Inject
	private AgendaDao agendaDao;
	
	@Inject
	private MessageDao messageDao;
	
	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;

	private String measurementSessionId;

	@PostConstruct
	public void init() {
		setNavigationStatus( observableEntityFilter );
		refreshCurrentPage();

	}

	public String runDateless( Long observableEntityId){ // when starting a "recovery" surveySchedule where a date can be chosen
		if( !observableEntityFilter.isFilterSet( ENROLLING_FILTER_NAME ) ) {
			message( FacesMessage.SEVERITY_WARN,
					"It is necessary to specify the clinical study for enrollment "
							+ "through the 'Visit for Agenda:' filter before proceeding with the visit!", true );
			return "list";
		}

		String selectedAgendaUuid = (String) observableEntityFilter
				.getFilterByFilterDefName( ENROLLING_FILTER_NAME ).getValue();
		Agenda agenda = agendaDao.findByUuid( selectedAgendaUuid );
		Date date = new Date();

		SurveySchedule surveySchedule = SurveyScheduleFactory.createSurveySchedule();
		surveySchedule.setAgenda( agenda );
		surveySchedule.setObservableEntity( observableEntityDao.findById( observableEntityId ) );
		//surveySchedule.setDate( date );
		surveySchedule.setStatus( SurveyScheduleStatus.ACCEPTED );
		String bookingCode = "BOOK" + DateUtils.getString( date, "yyyyMMddHHmmss" ) + "AG" + agenda.getCode();
		surveySchedule.setBookingCode( bookingCode  );
		String acceptanceCode = "ACC" + DateUtils.getString( date, "yyyyMMddHHmmss" ) +  "AG" + agenda.getCode();
		surveySchedule.setAcceptanceCode( acceptanceCode );

		MeasurementSession measurementSession = MeasurementSessionFactory.createMeasurementSession();
		measurementSession.setStatus( MeasurementSessionStatus.TODO );
		measurementSession.setSurveySchedule( surveySchedule );
		measurementSession.setLastUpdate( date );

		try {
			utx.begin();
			measurementSessionDao.save( measurementSession );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		MeasurementSession measurementSessionRecov = null;
		try {
			utx.begin();
			measurementSessionRecov = measurementSessionDao.findBySurveyScheduleCodes( bookingCode, acceptanceCode );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		if ( measurementSessionRecov != null ) {
			measurementSessionId = Long.toString( measurementSessionRecov.getId() );
			conversation.begin();
			return "run";
		} else
			throw new RuntimeException( "measurementSession not found" );
	}

	public String run( Long observableEntityId ) {
		if( !observableEntityFilter.isFilterSet( ENROLLING_FILTER_NAME ) ) {
			message( FacesMessage.SEVERITY_WARN,
					"It is necessary to specify the clinical study for enrollment "
							+ "through the 'Visit for Agenda:' filter before proceeding with the visit!", true );
			return "list";
		}

		String selectedAgendaUuid = (String) observableEntityFilter
				.getFilterByFilterDefName( ENROLLING_FILTER_NAME ).getValue();
		Agenda agenda = agendaDao.findByUuid( selectedAgendaUuid );
		Date date = new Date();

		SurveySchedule surveySchedule = SurveyScheduleFactory.createSurveySchedule();
		surveySchedule.setAgenda( agenda );
		surveySchedule.setObservableEntity( observableEntityDao.findById( observableEntityId ) );
		surveySchedule.setDate( date );
		surveySchedule.setStatus( SurveyScheduleStatus.ACCEPTED );
		String bookingCode = "BOOK" + DateUtils.getString( date, "yyyyMMddHHmmss" ) + "AG" + agenda.getCode();
		surveySchedule.setBookingCode( bookingCode  );
		String acceptanceCode = "ACC" + DateUtils.getString( date, "yyyyMMddHHmmss" ) +  "AG" + agenda.getCode();
		surveySchedule.setAcceptanceCode( acceptanceCode );

		MeasurementSession measurementSession = MeasurementSessionFactory.createMeasurementSession();
		measurementSession.setStatus( MeasurementSessionStatus.TODO );
		measurementSession.setSurveySchedule( surveySchedule );
		measurementSession.setLastUpdate( date );

		try {
			utx.begin();
			measurementSessionDao.save( measurementSession );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		MeasurementSession measurementSessionRecov = null;
		try {
			utx.begin();
			measurementSessionRecov = measurementSessionDao.findBySurveyScheduleCodes( bookingCode, acceptanceCode );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		if ( measurementSessionRecov != null ) {
			measurementSessionId = Long.toString( measurementSessionRecov.getId() );
			conversation.begin();
			return "run";
		} else
			throw new RuntimeException( "measurementSession not found" );
	}
	
	@Produces @RequestScoped @Named( "observableEntityResults" )
	public List<ObservableEntity> getResults() {
		if ( getItemCount().intValue() == 0 )
			return new ArrayList<>();

		return observableEntityDao.find( observableEntityFilter,  getOffset(),  getLimit() );
	}
	
	public Boolean checkHistory( Long observableEntityId ){
		Set<Agenda> agendas = loggedUser.getAgendas();
		
		Set<MeasurementSessionStatus> statuses = new HashSet<MeasurementSessionStatus>(
				Arrays.asList(
						MeasurementSessionStatus.SUSPENDED,
						MeasurementSessionStatus.DONE, 
						MeasurementSessionStatus.CONCLUDED));
		
		return measurementSessionDao.hasObservableEntityHistory( observableEntityId, statuses, agendas );
	}

	public boolean isRemovable( Long observableEntityId ) {
		return !measurementSessionDao.hasObservableEntityHistory( observableEntityId );
	}
	
	public boolean checkRoleFor( String operation ) {
		
		switch ( operation ) {
		case "add-new":
		case "edit":
		case "delete":
			return loggedUser.hasRole( RoleType.PATIENT_EDITOR ) || loggedUser.hasRole(RoleType.ADMINISTRATOR);
		
		default:
			return false;
		}
	}
	
	
	//
	// navigation methods
	//
	public String view( Long id ){
		selection = id.toString();
		return "view";
	}	
	
	public String edit( Long id ) {
		selection = id.toString();

		conversation.begin();
		return "edit";
	}	
	
	public String addNew() {
		conversation.begin();
		return "add-new";
	}
	
	public String delete( Long observableEntityId ) {
		observableEntityDao.deleteById( observableEntityId, loggedUser.getUser() );
		
		message( FacesMessage.SEVERITY_INFO, "ObservableEntity successfully deleted!", true );
		return "delete";
	}

	
	//
	// get methods
	//
	public String getSelection() {
		return selection;
	}	
	
	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = observableEntityDao.count( observableEntityFilter );
		}
		return itemCount;
	}
	public String getMeasurementSessionId() {
		return measurementSessionId;
	}


	//
	// private methods
	//
	private void message(Severity severityInfo, String message, boolean keepMessages) {
		facesContext.addMessage(null, new FacesMessage(severityInfo, message, null));
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );				
	}	
}
