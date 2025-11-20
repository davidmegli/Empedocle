package it.unifi.ing.stlab.empedocle.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.model.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.model.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.model.Viewer;

//XXX check
@Ignore
public class MeasurementSessionTypeDaoTest extends JpaTest{

	protected MeasurementSessionTypeDao measurementSessionTypeDao;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		measurementSessionTypeDao = new MeasurementSessionTypeDaoBean();
		FieldUtils.assignField(measurementSessionTypeDao, "entityManager", entityManager);

		entityManager.clear();
	}

	//TODO: rifare test, questi non hanno senso!!!

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
	
}
