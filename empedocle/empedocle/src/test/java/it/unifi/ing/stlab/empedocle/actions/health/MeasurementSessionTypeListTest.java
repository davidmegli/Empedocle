package it.unifi.ing.stlab.empedocle.actions.health;

import it.unifi.ing.stlab.empedocle.actions.health.measurementSession.types.MeasurementSessionTypeFilter;
import it.unifi.ing.stlab.empedocle.actions.health.measurementSession.types.MeasurementSessionTypeList;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.test.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class MeasurementSessionTypeListTest {

	protected MeasurementSessionTypeList measurementSessionTypeList;
	protected MeasurementSessionTypeDao measurementSessionTypeDao;
	protected MeasurementSessionTypeFilter measurementSessionTypeFilter;

	@Before
	public void setUp() {
		measurementSessionTypeFilter = new MeasurementSessionTypeFilter();
		measurementSessionTypeList = new MeasurementSessionTypeList();

		measurementSessionTypeDao = mock( MeasurementSessionTypeDao.class );

		when( measurementSessionTypeDao.count(anyObject()) )
				.thenReturn( new Integer( 510 ) );

		FieldUtils.assignField( measurementSessionTypeList, "measurementSessionTypeDao", measurementSessionTypeDao );
		FieldUtils.assignField( measurementSessionTypeList, "measurementSessionTypeFilter", measurementSessionTypeFilter );

		measurementSessionTypeList.init();
	}

	@Test
	public void testGetPageCount() {
		assertEquals( new Integer( 51 ), measurementSessionTypeList.getPageCount() );
	}

	@Test
	public void testGetResultList() {
		measurementSessionTypeList.getResultList();

		verify( measurementSessionTypeDao ).find(anyObject(), anyInt(), anyInt() );
	}
}
