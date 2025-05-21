package it.unifi.ing.stlab.empedocle.actions.woodelements;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.empedocle.actions.util.taxcode.FiscalCodeValidator;
import it.unifi.ing.stlab.empedocle.actions.util.taxcode.FiscalCodeValidatorException;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.Address;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Named
@ConversationScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class WoodElementEdit implements Serializable {

	private static final long serialVersionUID = -9188387601950001747L;

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
	private WoodElementDao woodElementDao;
	
	@Resource
	private UserTransaction utx;
	
	//
	// HttpParam injections
	//
	@Inject @HttpParam("id")
	private String id;
	
	@Inject @HttpParam("from")
	private String from;
	
	//
	// Local attributes
	//
	private WoodElement current;
	private WoodElement original;
	private WoodElementManager woodElementManager;

	
	@PostConstruct
	public void init() {
		woodElementManager = new WoodElementManager();

		try {
			utx.begin();
			
			if ( isNew() ) {
				current = woodElementManager.createWoodElement( loggedUser.getUser(), new Time( new Date() ) );
			} else {
				original = woodElementDao.fetchById( Long.parseLong( id ) );
				current = woodElementManager.modify(
						loggedUser.getUser(), new Time( new Date() ), original );
			}
			initEmbeddedFields();
			
		} catch ( Exception e ) {
			message( FacesMessage.SEVERITY_ERROR, "Error!", true );
			
			try {
				utx.rollback();
			} catch ( Exception ute ) {
				throw new RuntimeException( e );
			}
		}
	}

	public void generateTaxCode() {
		if(birthDateOK()){
			try {
				String code = FiscalCodeValidator.computeFiscalCode(
						current.getSurname(),
						current.getName(),
						current.getBirthDate().toInstant().atZone( ZoneId.systemDefault() ).toLocalDate(),
						current.getBirthPlace(), current.getSex() );

				current.setTaxCode( code );
				message( FacesMessage.SEVERITY_WARN,
						"WARNING - Always check Tax Code before saving!", false );

			} catch ( FiscalCodeValidatorException e ) {
				message( FacesMessage.SEVERITY_ERROR, "ERROR - Unable to generate Tax Code: " + e.getMessage(), false );
			}
		}
	}
	
	public boolean isNew() {
		return id == null;
	}
	
	
	//
	// navigation methods
	//
	public String cancel() {
		conversation.end();
		return "cancel";
	}

	public String save() {
		conversation.end();
		
		try {
			utx.begin();
			
			if ( exists() ) {
				message(FacesMessage.SEVERITY_ERROR,
						"ERROR - WoodElement with Tax Code '"
								+ current.getTaxCode() + "' is already registered!", true);
			} else {
				if ( isNew() ) {
					woodElementDao.save(current);
				} else {
					WoodElement purged = woodElementManager.purge( current );
					
					if ( purged != null ) {
						woodElementDao.save( purged );
						woodElementDao.update( original );
	//					updateSurveySchedulesReferences( purged );
						
						id = purged.getId().toString();
					}
				}
				
				message(FacesMessage.SEVERITY_INFO,
						"WoodElement successfully saved!", true);
			}
		
			utx.commit();
		} catch ( Exception e ) {
			message(FacesMessage.SEVERITY_INFO,
					"Error during saving!", true);
			try {
				utx.rollback();
			} catch ( Exception e2 ) {
				throw new RuntimeException( e );
			}
		}
		
		return "save";
	}	

	
	//
	// get methods
	//
	public String getId() {
		return id;
	}	
	public String getFrom() {
		return from;
	}
	
	public WoodElement getCurrent() {
		return current;
	}
	
	
	//
	// private methods
	//

	private boolean birthDateOK(){
		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(current.getBirthDate());

		Calendar calendar = Calendar.getInstance();
		calendar.getWeekYear();

		if(birthDate.get(Calendar.YEAR) < 1900){
			message(FacesMessage.SEVERITY_ERROR,
					"The year of birth is prior to 1900. Please recheck the entered date and the generated tax code, as they may contain errors.", true);
			return false;
		} else if(birthDate.get(Calendar.YEAR) > calendar.get(Calendar.YEAR)) {
			message(FacesMessage.SEVERITY_ERROR,
					"The year of birth is a future date. Please recheck the entered date and the generated tax code, as they may contain errors.", true);
			return false;
		} else{
			return true;
		}
	}

	private boolean exists() {
		WoodElement result = woodElementDao.findByTaxCode( current.getTaxCode() );
		
		if ( result == null ) return false;
		else {
			if ( isNew() ) return true;
			else {
                return !result.equals(original);
			}
		}
	}
	
	private void initEmbeddedFields() {
		if ( current.getResidence() == null ) 
			current.setResidence( new Address() );
		
		if ( current.getDomicile() == null )
			current.setDomicile( new Address() );
	}	
	
	private void message(Severity severityInfo, String message, boolean keepMessages) {
		facesContext.addMessage(null, new FacesMessage(severityInfo, message, null));
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );				
	}	
	
//	private void updateSurveySchedulesReferences( WoodElement p ) {
//		List<SurveySchedule> survey_schedules = survey_scheduleDao.findByWoodElements( p.listBefore() );		
//		
//		for( SurveySchedule a : survey_schedules ) {
//			a.setWoodElement( p );
//			survey_scheduleDao.update( a );
//		}
//	}	
}
