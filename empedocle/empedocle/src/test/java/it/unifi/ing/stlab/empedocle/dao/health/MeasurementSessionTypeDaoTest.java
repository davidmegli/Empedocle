package it.unifi.ing.stlab.empedocle.dao.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.actions.health.measurementSession.types.MeasurementSessionTypeFilter;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.model.Viewer;

//XXX check
@Ignore
public class MeasurementSessionTypeDaoTest extends JpaTest{

	protected MeasurementSessionTypeDao measurementSessionTypeDao;
	protected MeasurementSessionTypeFilter measurementSessionTypeFilter;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		measurementSessionTypeDao = new MeasurementSessionTypeDaoBean();
		FieldUtils.assignField(measurementSessionTypeDao, "entityManager", entityManager);
		
		measurementSessionTypeFilter = new MeasurementSessionTypeFilter();
		
		entityManager.clear();
	}
	
	
	@Test
	public void testFindNoFilter(){
		List<MeasurementSessionType> result = measurementSessionTypeDao.find(measurementSessionTypeFilter, 0, 50);
		
		assertEquals(1, result.size());
	}
	
	@Test
	public void testFindById(){
		MeasurementSessionType result = measurementSessionTypeDao.findById( new Long(1) );
		
		assertEquals("Cardiologia Dr. Fabio Mori - II rev", result.getName());	
	}
	
	@Test
	public void testFindByMeasurementSessionId() {
		MeasurementSessionType type = measurementSessionTypeDao.findByMeasurementSessionId( new Long(181896) );
		assertNotNull( type );
		assertEquals( "Cardiologia Dr. Fabio Mori - II rev", type.getName() );
	}
	
	@Test
	public void testFindAssociatedViewer() {
		List<Viewer> result = measurementSessionTypeDao.findAssociatedViewer( new Long(1), new Long(1), MeasurementSessionTypeContext.REPORT );
		assertNotNull( result );
		assertEquals( 2, result.size() );
		assertEquals( "Cardiologia Dr. Fabio Mori - II rev - ETICHETTA", result.get(0).getName() );
		assertEquals( "Cardiologia Dr. Fabio Mori - II rev - REPORT", result.get(1).getName() );
	}
	
}
