package it.unifi.ing.stlab.empedocle.actions.woodelements;

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
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.dao.messages.MessageDao;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.navigation.Navigator;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.users.model.RoleType;

@Named
@RequestScoped
public class WoodElementList extends Navigator {

	private static final String ENROLLING_FILTER_NAME = "Visit for Agenda:";
	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;

	@Resource
	private UserTransaction utx;
	
	@Inject 
	protected WoodElementFilter woodElementFilter;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private Conversation conversation;
	
	//
	// EJB injections
	//
	@Inject
	private WoodElementDao woodElementDao;
	
	@Inject
	private ExaminationDao examinationDao;

	@Inject
	private AgendaDao agendaDao;
	
	@Inject
	private MessageDao messageDao;
	
	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;

	private String examinationId;

	@PostConstruct
	public void init() {
		setNavigationStatus( woodElementFilter );
		refreshCurrentPage();

	}

	public String runDateless( Long woodElementId){ // when starting a "recovery" survey_schedule where a date can be chosen
		if( !wood_elementFilter.isFilterSet( ENROLLING_FILTER_NAME ) ) {
			message( FacesMessage.SEVERITY_WARN,
					"It is necessary to specify the clinical study for enrollment "
							+ "through the 'Visit for Agenda:' filter before proceeding with the visit!", true );
			return "list";
		}

		String selectedAgendaUuid = (String) wood_elementFilter
				.getFilterByFilterDefName( ENROLLING_FILTER_NAME ).getValue();
		Agenda agenda = agendaDao.findByUuid( selectedAgendaUuid );
		Date date = new Date();

		SurveySchedule survey_schedule = SurveyScheduleFactory.createSurveySchedule();
		survey_schedule.setAgenda( agenda );
		survey_schedule.setWoodElement( wood_elementDao.findById( wood_elementId ) );
		//survey_schedule.setDate( date );
		survey_schedule.setStatus( SurveyScheduleStatus.ACCEPTED );
		String bookingCode = "BOOK" + DateUtils.getString( date, "yyyyMMddHHmmss" ) + "AG" + agenda.getCode();
		survey_schedule.setBookingCode( bookingCode  );
		String acceptanceCode = "ACC" + DateUtils.getString( date, "yyyyMMddHHmmss" ) +  "AG" + agenda.getCode();
		survey_schedule.setAcceptanceCode( acceptanceCode );

		Examination examination = ExaminationFactory.createExamination();
		examination.setStatus( ExaminationStatus.TODO );
		examination.setSurveySchedule( survey_schedule );
		examination.setLastUpdate( date );

		try {
			utx.begin();
			examinationDao.save( examination );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		Examination examRecov = null;
		try {
			utx.begin();
			examRecov = examinationDao.findBySurveyScheduleCodes( bookingCode, acceptanceCode );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		if ( examRecov != null ) {
			examinationId = Long.toString( examRecov.getId() );
			conversation.begin();
			return "run";
		} else
			throw new RuntimeException( "exam not found" );
	}

	public String run( Long wood_elementId ) {
		if( !wood_elementFilter.isFilterSet( ENROLLING_FILTER_NAME ) ) {
			message( FacesMessage.SEVERITY_WARN,
					"It is necessary to specify the clinical study for enrollment "
							+ "through the 'Visit for Agenda:' filter before proceeding with the visit!", true );
			return "list";
		}

		String selectedAgendaUuid = (String) wood_elementFilter
				.getFilterByFilterDefName( ENROLLING_FILTER_NAME ).getValue();
		Agenda agenda = agendaDao.findByUuid( selectedAgendaUuid );
		Date date = new Date();

		SurveySchedule survey_schedule = SurveyScheduleFactory.createSurveySchedule();
		survey_schedule.setAgenda( agenda );
		survey_schedule.setWoodElement( wood_elementDao.findById( wood_elementId ) );
		survey_schedule.setDate( date );
		survey_schedule.setStatus( SurveyScheduleStatus.ACCEPTED );
		String bookingCode = "BOOK" + DateUtils.getString( date, "yyyyMMddHHmmss" ) + "AG" + agenda.getCode();
		survey_schedule.setBookingCode( bookingCode  );
		String acceptanceCode = "ACC" + DateUtils.getString( date, "yyyyMMddHHmmss" ) +  "AG" + agenda.getCode();
		survey_schedule.setAcceptanceCode( acceptanceCode );

		Examination examination = ExaminationFactory.createExamination();
		examination.setStatus( ExaminationStatus.TODO );
		examination.setSurveySchedule( survey_schedule );
		examination.setLastUpdate( date );

		try {
			utx.begin();
			examinationDao.save( examination );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		Examination examRecov = null;
		try {
			utx.begin();
			examRecov = examinationDao.findBySurveyScheduleCodes( bookingCode, acceptanceCode );
			utx.commit();
		} catch ( NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e ) {
			e.printStackTrace();
		}

		if ( examRecov != null ) {
			examinationId = Long.toString( examRecov.getId() );
			conversation.begin();
			return "run";
		} else
			throw new RuntimeException( "exam not found" );
	}
	
	@Produces @RequestScoped @Named( "wood_elementResults" ) 
	public List<WoodElement> getResults() {
		if ( getItemCount().intValue() == 0 )
			return new ArrayList<>();

		return wood_elementDao.find( wood_elementFilter,  getOffset(),  getLimit() );
	}
	
	public Boolean checkHistory( Long wood_elementId ){
		Set<Agenda> agendas = loggedUser.getAgendas();
		
		Set<ExaminationStatus> statuses = new HashSet<ExaminationStatus>(
				Arrays.asList(
						ExaminationStatus.SUSPENDED,
						ExaminationStatus.DONE, 
						ExaminationStatus.CONCLUDED));
		
		return examinationDao.hasWoodElementHistory( wood_elementId, statuses, agendas );
	}

	public boolean isRemovable( Long wood_elementId ) {
		return !examinationDao.hasWoodElementHistory( wood_elementId );
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
	
	public String delete( Long wood_elementId ) {
		wood_elementDao.deleteById( wood_elementId, loggedUser.getUser() );
		
		message( FacesMessage.SEVERITY_INFO, "WoodElement successfully deleted!", true );
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
			itemCount = wood_elementDao.count( wood_elementFilter );
		}
		return itemCount;
	}
	public String getExaminationId() {
		return examinationId;
	}


	//
	// private methods
	//
	private void message(Severity severityInfo, String message, boolean keepMessages) {
		facesContext.addMessage(null, new FacesMessage(severityInfo, message, null));
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );				
	}	
}
