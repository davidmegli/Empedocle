
package it.unifi.ing.stlab.empedocle.actions.health.services;

import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignStatusVisitor;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Singleton
@Startup
@TransactionManagement( TransactionManagementType.BEAN )
public class CloseMeasurementSessionBean implements CloseMeasurementSession {
	
	// TODO da testare 
	
	private static final long INTERVAL = 60 * 60 * 1000;
	
	private static final int PAGE_SIZE = 20;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction utx;
	
	@Resource
	private TimerService timerservice;

	@EJB
	private TypeDao typeDao;
	
	@EJB
	private FactDao factDao;
	
	@Inject
	private Logger logger;
	
	@Inject
	private MeasurementSessionTypeDao measurementSessionTypeDao;
	
	@PostConstruct
	public void init() {
		startService();
		logger.info("*** CloseMeasurementSessionBean started - interval: " + INTERVAL + "ms ***");
	}
	
	@PreDestroy
	public void destroy() {
		stopService();
		logger.info("*** CloseMeasurementSessionBean stopped ***");
	}
	
	@Override
	public void startService() {
		if ( closeMeasurementSessionTimer() != null ) return;
		
		timerservice.createTimer( initialExpiration(), INTERVAL, "close-measurementSession" );
	}
	
	@Override
	public void stopService() {
		if ( closeMeasurementSessionTimer() == null ) return;
		
		closeMeasurementSessionTimer().cancel();
	}

	@Override
	@Timeout
	public void timeout(Timer timer) {
		
		logger.info("*** CloseMeasurementSessionBean - begin timeout method ***");
		
		try {
			Date now = new Date( System.currentTimeMillis() );
			FactManager factManager = new FactManager();
			
			for(MeasurementSessionType et : measurementSessionTypeDao.findAll()) {
				int pages = pages(now, et).intValue();
				
				for(int i = 0; i < pages; i++) {
					utx.setTransactionTimeout(3000);
					utx.begin();
					
					List<MeasurementSession> measurementSessions = getMeasurementSessions(now, et);
					for(MeasurementSession ex : measurementSessions) {
						Type type = typeDao.findById( ex.getType().getType().getId() );
						Fact fact = factDao.findByContextId( ex.getId(), type.getId() );
						
						factDao.fetchById( ClassHelper.cast( fact, FactImpl.class ).getId() );
						
						// Close measurementSession
						ex.setStatus( MeasurementSessionStatus.CONCLUDED );
						ex.setLastUpdate( now );
						factManager.clean( ClassHelper.cast( fact, FactImpl.class ) );
						
						// assign status
						fact.accept( new AssignStatusVisitor( FactStatus.ACTIVE ) );
					}
					
					GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
					
					entityManager.flush();
					entityManager.clear();
					utx.commit();
					
				}
				
			}
			
			logger.info("*** CloseMeasurementSessionBean - end timeout method ***");

			
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	private Long pages(Date baseDate, MeasurementSessionType et) {
		Long nMeasurementSessions = (Long)entityManager.createQuery(
				"select count(e) from MeasurementSession e " +
				"where e.status = :status " +
				"and e.type = :type " +
				"and e.lastUpdate < :time")
				.setParameter("status", MeasurementSessionStatus.DONE)
				.setParameter("type", et)
				.setParameter("time", computeTimeLimit(baseDate, et))
				.getSingleResult();
		
		Long nPages = nMeasurementSessions / PAGE_SIZE + (nMeasurementSessions % PAGE_SIZE == 0 ? 0 : 1);
		
		return nPages;
		
		
	}
	
	@SuppressWarnings("unchecked")
	private List<MeasurementSession> getMeasurementSessions(Date baseDate, MeasurementSessionType et) {
		return entityManager.createQuery( 
				"select e from MeasurementSession e " +
				"where e.status = :status " +
				"and e.lastUpdate < :time " +
				"and e.type = :type" )
				.setParameter("status", MeasurementSessionStatus.DONE )
				.setParameter("time", computeTimeLimit(baseDate, et))
				.setParameter("type", et)
				.setMaxResults(PAGE_SIZE)
//				.setFirstResult(pageNumber * PAGE_SIZE)
				.getResultList();
	}
	
	private Date computeTimeLimit(Date baseDate, MeasurementSessionType et) {
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate);
		c.add(Calendar.HOUR, -et.getTimeToLive());
		return c.getTime();
	}
	
	private Timer closeMeasurementSessionTimer() {
		for (Timer timer : timerservice.getTimers()) {
			if ( "close-measurementSession".equals( timer.getInfo())) {
				return timer;
			}
		}
		return null;
	}

	private Date initialExpiration() {
		// today
		Calendar date = new GregorianCalendar();
		
//		// add some minutes
		date.add( Calendar.MINUTE, 1 );
		
		return date.getTime();
	}
}
