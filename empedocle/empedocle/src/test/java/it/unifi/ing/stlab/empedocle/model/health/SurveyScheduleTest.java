package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;

import org.junit.Before;
import org.junit.Test;

public class SurveyScheduleTest {

	protected SurveySchedule surveySchedule;
	
	@Before
	public void setUp() {
		surveySchedule = SurveyScheduleFactory.createSurveySchedule();
	}
	
	@Test
	public void testListServices() {
		assertEquals( 0, surveySchedule.listServices().size() );
	}
	
	@Test
	public void testAddService() {
		Service agenda = ServiceFactory.createService();
		surveySchedule.addService( agenda );

		assertEquals( 1, surveySchedule.listServices().size() );
		assertTrue( surveySchedule.listServices().contains( agenda ));
		
	}

	@Test
	public void testAddServiceNull() {
		surveySchedule.addService( null );
		assertEquals( 0, surveySchedule.listServices().size() );
	}

	@Test
	public void testRemoveService() {
		Service agenda = ServiceFactory.createService();
		surveySchedule.addService( agenda );
		surveySchedule.removeService( agenda );

		assertEquals( 0, surveySchedule.listServices().size() );
	}

	@Test
	public void testClearServices() {
		surveySchedule.addService( ServiceFactory.createService() );
		surveySchedule.clearServices();

		assertEquals( 0, surveySchedule.listServices().size() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddService() {
		surveySchedule.listServices().add( ServiceFactory.createService() );
	}

}
