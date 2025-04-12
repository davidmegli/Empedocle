package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import java.util.Date;

import org.junit.Test;

public class SurveyScheduleJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		SurveySchedule surveySchedule = SurveyScheduleFactory.createSurveySchedule();
		surveySchedule.setDate( new Date() );
		surveySchedule.setNumber( "number" );
		surveySchedule.setBookingCode( "bookingCode" );
		surveySchedule.setAcceptanceCode( "acceptanceCode" );
		surveySchedule.setStatus( SurveyScheduleStatus.BOOKED );
		
		entityManager.persist( surveySchedule );

		uuid = surveySchedule.getUuid();
	}
	
	@Test
	public void testSurveySchedule() {
		SurveySchedule surveySchedule = (SurveySchedule)
			entityManager
				.createQuery( 
					"select p " +
					" from SurveySchedule p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( surveySchedule );
		
		assertNotNull( surveySchedule.getDate() );
		assertEquals( "number", surveySchedule.getNumber());
		assertEquals( "bookingCode", surveySchedule.getBookingCode());
		assertEquals( "acceptanceCode", surveySchedule.getAcceptanceCode());
		assertEquals( SurveyScheduleStatus.BOOKED, surveySchedule.getStatus() );
	}
	
}
