package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.empedocle.actions.util.GarbageCollectorHelper;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.*;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.empedocle.security.LoginBean;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.FactGarbageVisitor;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.FactDefaultInitializerVisitor;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.view.model.Viewer;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.ServletException;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Named
@Stateful
@ConversationScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class MeasurementSessionRunning implements Serializable {

	private static final long serialVersionUID = -4532411287125219807L;
	
	private MeasurementSessionDetails measurementSessionDetails;
	private boolean summary;
		
	private WoodElement lastWoodElementVersion;
	private List<MeasurementSession> woodElementLastExams;

	private List<Viewer> measurementSessionReports;
	private String selection;
	
	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;	
	

	@Inject
	private Conversation conversation;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private MeasurementSessionPrint measurementSessionPrint;	
	
	//
	// EJB injections
	//
	
	@PersistenceContext( type = PersistenceContextType.EXTENDED )
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction utx;
	
	@EJB
	private MeasurementSessionTypeDao measurementSessionTypeDao;

	@EJB
	private MeasurementSessionDao measurementSessionDao;
	
	@EJB
	private TypeDao typeDao;

	@EJB
	private FactDao factDao;

	@EJB
	private UserDao userDao;
	
	@EJB
	private WoodElementDao woodElementDao;

	@EJB
	private GarbageCollectorHelper garbageCollector;
	
	private final Logger logger = Logger.getLogger(MeasurementSessionRunning.class);

	private Long tmpID;

	private Boolean dateFreeVisit = false;
	private Date visitDate;

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
	// Initialization methods
	//
	
	@PostConstruct
	public void init(){
		beginConversation();

		measurementSessionDetails = null;
		summary = false;
		
		if ( id == null )
			throw new IllegalArgumentException( "Visita non specificata" );

		try {
			utx.begin();


			MeasurementSession measurementSession = measurementSessionDao.findById( Long.parseLong( id ));

			if ( measurementSession == null ) {
				utx.rollback();
				throw new IllegalArgumentException( "Visita non trovata" );
			}
			
			initLastWoodElementVersion( measurementSession );

			if(woodElementLastExams == null){
				woodElementLastExams = measurementSessionDao.findWoodElementLastExams(lastWoodElementVersion.getId(), Long.parseLong( id ), 10);
			}

			/*TEST*/
			//caso in cui la visita è con Data libera, da specificare per completare l'erogazione
			if(measurementSession.getSurveySchedule().getDate() == null){
				setDateFreeVisit(true);
				visitDate = new Date();
			}

			/*FINE TEST*/

			switch ( measurementSession.getStatus() ) {
			case TODO:
				startMeasurementSession( measurementSession );
				break;
			case DONE:
				modifyMeasurementSession( measurementSession );
				break;
			case SUSPENDED:
				resumeMeasurementSession( measurementSession );
				break;
			case RUNNING:
				recoverMeasurementSession( measurementSession );
				break;
			default:
				utx.rollback();
				throw new IllegalArgumentException( "Visita non editabile" );
			}
			
			utx.commit();
			
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}


	//
	// Public interface
	//

	/*@RequestScoped
	public List<MeasurementSession> getResults() {
		return measurementSessionDao.find( measurementSessionFilter,  0,  10 );
	}

*/

	public List<MeasurementSession> getWoodElementLastExams(){
		return this.woodElementLastExams;
	}

	public void initReports( Long id ) {
		selection = Long.toString( id );
		measurementSessionReports = measurementSessionPrint.initSelectedReports( measurementSessionDao.findById( id ) );
	}

	public String getSelection() {
		return selection;
	}
	public List<Viewer> getExamReports(Long id) {
		tmpID = id;
		if (measurementSessionReports == null || measurementSessionReports.size() == 0) {
			initReports( id );
		}
		return measurementSessionReports;
	}


	//
	// Private methods
	//

	private void initLastWoodElementVersion( MeasurementSession measurementSession ) {
		lastWoodElementVersion = woodElementDao
				.findLastVersionById( measurementSession.getSurveySchedule().getWoodElement().getId() );		
	}

	private void beginConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}

	private boolean hasDone( MeasurementSession measurementSession ) {
		return MeasurementSessionStatus.DONE.equals( measurementSession.getStatus() );
	}
	private boolean hasConcluded( MeasurementSession measurementSession ) {
		return MeasurementSessionStatus.CONCLUDED.equals( measurementSession.getStatus() );
	}
	private boolean hasSuspended( MeasurementSession measurementSession ) {
		return MeasurementSessionStatus.SUSPENDED.equals( measurementSession.getStatus() );
	}
	
	private void startMeasurementSession( MeasurementSession measurementSession ) {
		try{
			MeasurementSessionType measurementSessionType = measurementSessionTypeDao.findByMeasurementSessionId( measurementSession.getId() );
			User user = userDao.findByUsername( loggedUser.getUsername() );

			Type type = typeDao.fetchById( measurementSessionType.getType().getId() );
			FactFactoryVisitor factFactory = new FactFactoryVisitor( user, new Time( new Date( System.currentTimeMillis())));
			type.accept( factFactory );

			Fact fact = factFactory.getResult();
			AssignContextVisitor assignContext = new AssignContextVisitor( measurementSession );
			fact.accept( assignContext );

			FactDefaultInitializerVisitor assignDefault = new FactDefaultInitializerVisitor();
			fact.accept( assignDefault );

			// recupero osservazioni ricorrenti
			RecurrentFactHelper recurrentHelper = new RecurrentFactHelper(measurementSessionDao);
			recurrentHelper.resumeRecurrentFacts(fact);
			garbageCollector.flush();

			measurementSession.setStatus( MeasurementSessionStatus.RUNNING );
			measurementSession.setType( measurementSessionType );
			measurementSession.setLastUpdate( new Date( System.currentTimeMillis() ));
			measurementSession.setAuthor( user );

			factDao.save( fact );
		}catch(Exception e){ // quando la creazione del fact fallisce, si cancellano anche l'SurveySchedule e la MeasurementSession relativi
			measurementSessionDao.deleteById(measurementSession.getId());
		}

	}

	public boolean hasView( MeasurementSession measurementSession ){
		if ( measurementSession == null ) return false;

		return ( hasDone(measurementSession) || hasConcluded(measurementSession) || hasSuspended( measurementSession ));
	}


	private void modifyMeasurementSession( MeasurementSession measurementSession ) {
		User user = userDao.findByUsername( loggedUser.getUsername() );
		Date date = new Date( System.currentTimeMillis() );
		Time time = new Time( date );
		
		Fact source = factDao.findByContextId( measurementSession.getId(), measurementSession.getType().getType().getId() );
		Long id = ClassHelper.cast( source, FactImpl.class ).getId();
		factDao.fetchById( id );

		FactManager factManager = new FactManager();
		Fact dest = factManager.modify( user, time, ClassHelper.cast( source, FactImpl.class ) );

		measurementSession.setStatus( MeasurementSessionStatus.RUNNING );
		measurementSession.setLastUpdate( date );
		measurementSession.setAuthor( user );

		factDao.save( dest );
		
	}

	
	private void resumeMeasurementSession( MeasurementSession measurementSession ) {
		modifyMeasurementSession( measurementSession );
	}
	private void recoverMeasurementSession( MeasurementSession measurementSession ) {
	}
	
	
	//
	// Persisting data on "partial submit"
	//
	public void persistData() {
		try {
			utx.begin();
			utx.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	//
	// MeasurementSession details
	//
	
	public MeasurementSessionDetails getMeasurementSessionDetails() {
		if ( measurementSessionDetails == null ) {
			initMeasurementSessionDetails();
		}
		return measurementSessionDetails;
	}
	
	private void initMeasurementSessionDetails() {
		try {
			utx.begin();
			
			measurementSessionDetails = measurementSessionDao.fetchById( 
					Long.parseLong( id ), 
					loggedUser.getUserQualification().getId(), 
					( summary ? MeasurementSessionTypeContext.VIEW : MeasurementSessionTypeContext.EDIT ));
			
			entityManager.lock(	measurementSessionDetails.getMeasurementSession(), LockModeType.OPTIMISTIC );
			
			utx.commit();
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}
	
	
	//
	// Reports
	//
	
	public List<Viewer> getReports(){
		return measurementSessionReports;
	}
	
	
	//
	// Summary
	//
	
	public boolean isSummary() {
		return summary;
	}
	public void setSummary(boolean summary) {
		this.summary = summary;
	}

	
	//
	// Permission Check Methods
	//
	
	public boolean checkEndAuthorization(){
		if( loggedUser == null ) {
			logger.info("loggedUser is null!");
		}
		if( id == null ) {
			logger.info("measurementSessionId is null!");
		}
			
		return measurementSessionDao.hasPermission( new Long( id ), 
									loggedUser.getUserQualification().getId(), 
									MeasurementSessionOperation.END_EXAMINATION );
	}
	
	//
	// Ending methods
	//
	
	public String suspend(){
		MeasurementSession measurementSession = getMeasurementSessionDetails().getMeasurementSession();
		Fact fact = getMeasurementSessionDetails().getFact();
		Date date = new Date( System.currentTimeMillis() );
		
		try {
			utx.begin();
			
			measurementSession.setStatus( MeasurementSessionStatus.SUSPENDED );
			measurementSession.setLastUpdate( date );
			FactManager factManager = new FactManager();
			factManager.purge( ClassHelper.cast( fact, FactImpl.class ) );
			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
			
			utx.commit();

			conversation.end();
			
			return "woodelement-list";
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	
	public String cancel(){
		MeasurementSession measurementSession = getMeasurementSessionDetails().getMeasurementSession();
		
		try {
			utx.begin();
			
			measurementSession.setStatus( MeasurementSessionStatus.TODO );
			Fact toRemove = measurementSessionDetails.getFact();
			toRemove.accept( new FactGarbageVisitor() );
			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ) );
			
			utx.commit();

			conversation.end();
			
			return "wood_element-list";
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	public String end(){
		MeasurementSession measurementSession = getMeasurementSessionDetails().getMeasurementSession();



		Fact fact = getMeasurementSessionDetails().getFact();
		Date date = new Date( System.currentTimeMillis() );
		
		try {
			utx.begin();
			
			measurementSession.setStatus( MeasurementSessionStatus.DONE );
			measurementSession.setWasDone( true );
			measurementSession.setLastUpdate( date );
			FactManager factManager = new FactManager();
			Fact purged = factManager.purge( ClassHelper.cast( fact, FactImpl.class ) );
			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));

			if(dateFreeVisit){
				if(visitDate != null){
					measurementSession.getSurveySchedule().setDate(visitDate);
					measurementSession.setLastUpdate(visitDate);
					/*String tmpDate = DateUtils.getString( measurementSession.getSurveySchedule().getDate(), "yyyyMMddHHmmss" );
					String bookingCode = measurementSession.getSurveySchedule().getBookingCode().replaceAll("__", tmpDate );
					String acceptanceCode = measurementSession.getSurveySchedule().getAcceptanceCode().replaceAll("__", tmpDate );
					measurementSession.getSurveySchedule().setBookingCode(bookingCode);
					measurementSession.getSurveySchedule().setAcceptanceCode(acceptanceCode);*/
				}
			}
			
			utx.commit();
			
			summary = true;
			measurementSessionDetails = null;
			measurementSessionPrint.clear();
			initReports(Long.parseLong(id));
			
			return "summary";
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	

	public String close() {
		conversation.end();
		return "wood_element-list";
	}
	
	public String switchUser() {
		try {
			suspend();
			loginBean.logout();
			return "switch-user";
		} catch (ServletException e) {
			throw new RuntimeException();
		}
	}
	
	// TODO testare
	public String reOpen() {
		summary = false;
		initMeasurementSessionDetails();
		this.setDateFreeVisit(false);
		getMeasurementSessionDetails().getMeasurementSession().setStatus( MeasurementSessionStatus.RUNNING );
		getMeasurementSessionDetails().getMeasurementSession().setLastUpdate( new Date( System.currentTimeMillis() ) );
		return "run";
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getRowStyleClasses( List<MeasurementSession> e ) {
		return MeasurementSessionRowStyleHelper.getRowStyleClasses( e );
	}
	
	//XXX metodi di servizio per inputList e immagini svg - per ora si lasciano qua
	public void addChildren(TypeLink tl, Fact f) {
		try {
			utx.begin();
			
			factDao.addChildren(tl, f, userDao.findByUsername( loggedUser.getUsername() ), new Time( new Date( System.currentTimeMillis() )));
			
			utx.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void removeChildren(FactLink fl) {
		try {
			utx.begin();
			
			factDao.removeChildren(fl);
			garbageCollector.flush();
			
			utx.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	


	public boolean hasReport( MeasurementSession measurementSession ){
		if ( measurementSession == null ) return false;
		
		return ( MeasurementSessionStatus.DONE.equals( measurementSession.getStatus() ) 
				|| MeasurementSessionStatus.CONCLUDED.equals( measurementSession.getStatus() ) );
	}

	public boolean has2Report( MeasurementSession measurementSession ){
		if ( measurementSession == null ) return false;

		if(!( MeasurementSessionStatus.DONE.equals( measurementSession.getStatus() )|| MeasurementSessionStatus.CONCLUDED.equals( measurementSession.getStatus() ) )){
			return false;
		}

		return measurementSessionTypeDao.findAssociatedViewer(measurementSession.getType().getId(), loggedUser.getUserQualification().getId(), MeasurementSessionTypeContext.REPORT).size() > 1;

	}

	
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
	
	public WoodElement getLastWoodElementVersion() {
		return lastWoodElementVersion;
	}

	public String getMeasurementSessionId() {
		return this.id;
	}

	public Boolean getDateFreeVisit() {
		return dateFreeVisit;
	}

	public void setDateFreeVisit(Boolean dateFreeVisit) {
		this.dateFreeVisit = dateFreeVisit;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
}
