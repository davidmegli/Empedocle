package it.unifi.ing.stlab.empedocle.dao.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDaoBean;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

//XXX check
@Ignore
public class MeasurementSessionDaoTest extends JpaTest {

	protected MeasurementSessionDao measurementSessionDao;
	protected AgendaDao agendaDao;
	 
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		FactDao factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );

		ViewerDao viewerDao = new ViewerDaoBean();
		FieldUtils.assignField( viewerDao, "entityManager", entityManager );
		
		measurementSessionDao = new MeasurementSessionDaoBean();
		FieldUtils.assignField( measurementSessionDao, "entityManager", entityManager );
		FieldUtils.assignField( measurementSessionDao, "factDao", factDao );
		FieldUtils.assignField( measurementSessionDao, "viewerDao", viewerDao );
		
		agendaDao = new AgendaDaoBean();
		FieldUtils.assignField( agendaDao, "entityManager", entityManager );
		
		entityManager.clear();
	}
	
	@Test
	public void testFindObservableEntityHistoryNullStatusesAndAgendas() {
		List<MeasurementSession> measurementSessions = measurementSessionDao.findObservableEntityHistory( new Long(83695), new Long(181896), null, null, 0, 5 );
		assertEquals(0, measurementSessions.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindObservableEntityHistoryNullStatuses() {
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "IMPORTCARDIO" ));
		
		measurementSessionDao.findObservableEntityHistory( new Long(83695), new Long(181896), null, agendas, 0, 5 );
	}
	
	@Test
	public void testFindObservableEntityHistoryNullAgendas() {
		Set<MeasurementSessionStatus> statuses = new HashSet<MeasurementSessionStatus>();
		statuses.add( MeasurementSessionStatus.CONCLUDED );
		statuses.add( MeasurementSessionStatus.DONE );
		
		List<MeasurementSession> measurementSessions = measurementSessionDao.findObservableEntityHistory( new Long(83695), new Long(181896), statuses, null, 0, 5 );
		assertEquals(0, measurementSessions.size());
	}		
	
	@Test
	public void testFindObservableEntityHistory() {
		Set<MeasurementSessionStatus> statuses = new HashSet<MeasurementSessionStatus>();
		statuses.add( MeasurementSessionStatus.CONCLUDED );
		statuses.add( MeasurementSessionStatus.DONE );
		
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "TESTCARDIO" ));
		
		List<MeasurementSession> measurementSessions = measurementSessionDao.findObservableEntityHistory( new Long(83695), new Long(181896), statuses, agendas, 0, 5 );
		assertEquals( 1, measurementSessions.size() );
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindObservableEntityHistoryFail1() {
		measurementSessionDao.findObservableEntityHistory( null, new Long(181896), null, null, 0, 5 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindObservableEntityHistoryFail2() {
		measurementSessionDao.findObservableEntityHistory( new Long(83695), null, null, null, 0, 5 );
	}
	
	@Test
	public void testHasObservableEntityHistory() {
		Set<MeasurementSessionStatus> statuses = new HashSet<MeasurementSessionStatus>();
		statuses.add( MeasurementSessionStatus.CONCLUDED );
		statuses.add( MeasurementSessionStatus.DONE );
		
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "TESTCARDIO" ));
		
		boolean result = measurementSessionDao.hasObservableEntityHistory( new Long(83695), statuses, agendas);
		assertTrue(result);
	}
	
	@Test
	public void testCountObservableEntityHistory() {
		Set<MeasurementSessionStatus> statuses = new HashSet<MeasurementSessionStatus>();
		statuses.add( MeasurementSessionStatus.CONCLUDED );
		statuses.add( MeasurementSessionStatus.DONE );
		
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "TESTCARDIO" ));
		
		int result = measurementSessionDao.countObservableEntityHistory( new Long(83695), new Long(181896), statuses, agendas );
		assertEquals( 1, result );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCountObservableEntityHistoryFail1() {
		measurementSessionDao.countObservableEntityHistory( null, new Long(181896), null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCountObservableEntityHistoryFail2() {
		measurementSessionDao.countObservableEntityHistory( new Long(82727), null, null, null);
	}
	
}
