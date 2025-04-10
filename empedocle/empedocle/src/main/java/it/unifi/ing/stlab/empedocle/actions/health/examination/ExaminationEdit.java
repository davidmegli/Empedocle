package it.unifi.ing.stlab.empedocle.actions.health.examination;

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
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.AppointmentStatus;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.wood-elements.dao.WoodElementDao;
import it.unifi.ing.stlab.wood-elements.model.WoodElement;

@Named
@ConversationScoped
public class ExaminationEdit implements Serializable {

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
	private ExaminationDao examinationDao;
	
	@Inject
	private AgendaDao agendaDao;
	
	@Inject
	private WoodElementDao wood_elementDao;
	
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
	private Examination current;

	private AgendaSuggestion agendaSuggestion;
	private WoodElementSuggestion wood_elementSuggestion;
	

	@PostConstruct
	public void init() {
		if( isNew() ) {
			current = ExaminationFactory.createExamination();
			current.setAppointment( AppointmentFactory.createAppointment() );
		} else {
			current = examinationDao.findById( Long.parseLong( id ) );
		}
		
		initAgendaSuggestion();
		initWoodElementSuggestion();
	}
	
	public String cancel() {
		conversation.end();
		return "cancel";
	}

	public String save() {
		current.setLastUpdate( new Date() );
		
		String date = DateUtils.getString( current.getAppointment().getDate(), "yyyyMMddHHmm" );
		String agendaCode = current.getAppointment().getAgenda().getCode();
		
		current.getAppointment().setBookingCode( "BOOK" + date + "AG" + agendaCode );
		current.getAppointment().setAcceptanceCode( "ACC" + date +  "AG" + agendaCode );
		
		try {
			if ( isNew() ) {
				current.setStatus( ExaminationStatus.TODO );
				current.getAppointment().setStatus( AppointmentStatus.ACCEPTED );

				examinationDao.save( current );
			} else {
				examinationDao.update( current );
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
								+ DateUtils.getString( current.getAppointment().getDate(), "dd/MM/yyyy HH:mm" )
								+ "' already in use on the agenda'"
								+ current.getAppointment().getAgenda().toString() + "'!",
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
	
	public Examination getCurrent() {
		return current;
	}
	
	public String getId() {
		return id;
	}	

	public AgendaSuggestion getAgendaSuggestion() {
		return agendaSuggestion;
	}
	
	public WoodElementSuggestion getWoodElementSuggestion() {
		return wood_elementSuggestion;
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
	// Internal class for wood_element suggestion
	//
	public class WoodElementSuggestion implements SuggestionInterface {
		
		private String suggestion;

		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			List<WoodElement> pList = wood_elementDao.findBySuggestion( suggestion, 0 );
			
			for( WoodElement p : pList ){
				result.add( new SelectItem( p.getUuid(), WoodElementUtils.toString( p )));
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
	private static class WoodElementUtils {
		
		public static String toString( WoodElement p ) {
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
					current.getAppointment().getAgenda().toString() );
	}

	private void initWoodElementSuggestion() {
		wood_elementSuggestion = new WoodElementSuggestion();
		if ( !isNew() )
			wood_elementSuggestion.setSuggestion( 
					WoodElementUtils.toString( current.getAppointment().getWoodElement()) );
	}
	
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
