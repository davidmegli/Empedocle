package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionTypeFactory;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.test.PersistenceTest;

import javax.transaction.UserTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MeasurementSessionRunningTest extends PersistenceTest {

	// TODO MeasurementSessionRunning tests
	
	@Mock private UserTransaction utx;
//	@Mock private StaffD
	
	private MeasurementSessionRunning measurementSessionRunning;
	
	private MeasurementSession measurementSession;
	
	public void setUp() throws Exception {
		super.setUp();
		measurementSessionRunning = new MeasurementSessionRunning();
		measurementSessionRunning.setSummary(true);
		measurementSession = MeasurementSessionFactory.createMeasurementSession();
		measurementSession.setSurveySchedule(SurveyScheduleFactory.createSurveySchedule());
		measurementSession.setStatus(MeasurementSessionStatus.DONE);
		measurementSession.setType(MeasurementSessionTypeFactory.createMeasurementSessionType());
	}
	
	@Test
	public void testReopen() {
		
	}
	
//	public String reOpen() {
//		summary = false;
//		initMeasurementSessionDetails();
//		getMeasurementSessionDetails().getMeasurementSession().setStatus( MeasurementSessionStatus.RUNNING );
//		getMeasurementSessionDetails().getMeasurementSession().setLastUpdate( new Date( System.currentTimeMillis() ) );
//		return "edit";
//	}
	
}
