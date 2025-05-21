package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.viewers.SuggestionInterface;
import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

@Named
@ConversationScoped
public class MeasurementSessionEdit implements Serializable {

	private static final long serialVersionUID = 7266327871130903674L;

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private Conversation conversation;	
	
	@Inject
	private LoggedUser loggedUser;
	
	//
	// EJB injections
	//
	@Inject
	private MeasurementSessionDao measurementSessionDao;
	
	@Inject
	private AgendaDao agendaDao;
	
	@Inject
	private ObservableEntityDao observableEntityDao;
	
	@EJB
	private ServiceDao serviceDao;
	
	//
	// HttpParam injections
	//
	@Inject @HttpParam("id")
	private String id;
	
	//
	// Local attributes
	//
	private MeasurementSession current;

	private AgendaSuggestion agendaSuggestion;
	private ObservableEntitySuggestion observableEntitySuggestion;
	

	@PostConstruct
	public void init() {
		if( isNew() ) {
			current = MeasurementSessionFactory.createMeasurementSession();
			current.setSurveySchedule( SurveyScheduleFactory.createSurveySchedule() );
		} else {
			current = measurementSessionDao.findById( Long.parseLong( id ) );
		}
		
		initAgendaSuggestion();
		initObservableEntitySuggestion();
	}
	
	public String cancel() {
		conversation.end();
		return "cancel";
	}

	public String save() {
		current.setLastUpdate( new Date() );
		
		String date = DateUtils.getString( current.getSurveySchedule().getDate(), "yyyyMMddHHmm" );
		String agendaCode = current.getSurveySchedule().getAgenda().getCode();
		
		current.getSurveySchedule().setBookingCode( "BOOK" + date + "AG" + agendaCode );
		current.getSurveySchedule().setAcceptanceCode( "ACC" + date +  "AG" + agendaCode );
		
		try {
			if ( isNew() ) {
				current.setStatus( MeasurementSessionStatus.TODO );
				current.getSurveySchedule().setStatus( SurveyScheduleStatus.ACCEPTED );

				measurementSessionDao.save( current );
			} else {
				measurementSessionDao.update( current );
			}
			
			message( FacesMessage.SEVERITY_INFO, "Visit successfully saved!");
		} catch ( EJBTransactionRolledbackException e ) {
			Throwable t = e.getCause();
			
			while ( ( t != null ) && !( t instanceof ConstraintViolationException ) ) {
				t = t.getCause();
			}
			
			if ( t instanceof ConstraintViolationException ) {
				message( FacesMessage.SEVERITY_ERROR,
						"Unable to perform the save: date '"
								+ DateUtils.getString( current.getSurveySchedule().getDate(), "dd/MM/yyyy HH:mm" )
								+ "' already in use on the agenda'"
								+ current.getSurveySchedule().getAgenda().toString() + "'!",
						true );
			}
		} catch ( Exception e ) {
			message( FacesMessage.SEVERITY_ERROR, "Error during saving!", true );
		}
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		
		conversation.end();
		return "save";
	}	

	
	//
	// get methods
	//
	public boolean isNew() {
		return id == null;
	}
	
	public MeasurementSession getCurrent() {
		return current;
	}
	
	public String getId() {
		return id;
	}	

	public AgendaSuggestion getAgendaSuggestion() {
		return agendaSuggestion;
	}
	
	public ObservableEntitySuggestion getObservableEntitySuggestion() {
		return observableEntitySuggestion;
	}
	
	//
	// Internal class for agenda suggestion
	//
	public class AgendaSuggestion implements SuggestionInterface {

		private String suggestion;
		
		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			List<Agenda> ags = agendaDao.findBySuggestion( suggestion, loggedUser.getUsername(), 0);
			
			for(Agenda a : ags){
				result.add( new SelectItem( a.getUuid(),  a.toString() ));
			}
			return result;
		}
		
		public String getSuggestion() {
			return suggestion;
		}
		public void setSuggestion( String suggestion ) {
			this.suggestion = suggestion;
		}
	}
	
	//
	// Internal class for observableEntity suggestion
	//
	public class ObservableEntitySuggestion implements SuggestionInterface {
		
		private String suggestion;

		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			List<ObservableEntity> pList = observableEntityDao.findBySuggestion( suggestion, 0 );
			
			for( ObservableEntity p : pList ){
				result.add( new SelectItem( p.getUuid(), ObservableEntityUtils.toString( p )));
			}
			return result;
		}
		
		public String getSuggestion() {
			return suggestion;
		}
		public void setSuggestion( String suggestion ) {
			this.suggestion = suggestion;
		}
	}

	//
	// Internal utility class
	//
	private static class ObservableEntityUtils {
		
		public static String toString( ObservableEntity p ) {
			return p.getSurname() + " " + p.getName() + " - " + p.getTaxCode();
		}
	}
	
	//
	// private methods
	//
	private void message( Severity severityInfo, String message ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ));
	}
	
	private void initAgendaSuggestion() {
		agendaSuggestion = new AgendaSuggestion();
		if ( !isNew() )
			agendaSuggestion.setSuggestion( 
					current.getSurveySchedule().getAgenda().toString() );
	}

	private void initObservableEntitySuggestion() {
		observableEntitySuggestion = new ObservableEntitySuggestion();
		if ( !isNew() )
			observableEntitySuggestion.setSuggestion(
					ObservableEntityUtils.toString( current.getSurveySchedule().getObservableEntity()) );
	}
	
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
