package it.unifi.ing.stlab.empedocle.dao.health;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionDetails;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeDaoBean;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;

//XXX check
@Ignore
public class MeasurementSessionFindByIdTest extends JpaTest {

	protected MeasurementSessionDao measurementSessionDao;
	 
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
		
		TypeDao typeDao = new TypeDaoBean();
		FieldUtils.assignField( typeDao, "entityManager", entityManager );
		
		measurementSessionDao = new MeasurementSessionDaoBean();
		FieldUtils.assignField( measurementSessionDao, "entityManager", entityManager );
		FieldUtils.assignField( measurementSessionDao, "factDao", factDao );
		FieldUtils.assignField( measurementSessionDao, "viewerDao", viewerDao );
		FieldUtils.assignField( measurementSessionDao, "typeDao", typeDao );
		
		entityManager.clear();
	}
	

	@Test
	public void testFindById() {
		assertNotNull( measurementSessionDao.findById( new Long( 181896 ) ));
	}
	
	@Test
	public void testFetchById() {
		MeasurementSessionDetails measurementSessionDetails = measurementSessionDao.fetchById( new Long( 181896 ), new Long( 1 ), MeasurementSessionTypeContext.VIEW );
	
		// TODO inspect MeasurementSessionDetails
	}
	
	
}




