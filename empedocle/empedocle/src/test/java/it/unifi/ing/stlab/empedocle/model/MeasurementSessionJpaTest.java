package it.unifi.ing.stlab.empedocle.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.factory.MeasurementSessionTypeFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;

import java.util.Date;

import org.junit.Test;

public class MeasurementSessionJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		MeasurementSessionType type = MeasurementSessionTypeFactory.createMeasurementSessionType();
		type.setName( "MeasurementSession type" );
		entityManager.persist( type );

		SurveySchedule surveySchedule = SurveyScheduleFactory.createSurveySchedule();
		surveySchedule.setDate( new Date() );
		surveySchedule.setNumber( "1234" );
		surveySchedule.setStatus( SurveyScheduleStatus.BOOKED );
		entityManager.persist( surveySchedule );
		
		User user = UserFactory.createUser();
		user.setName( "Fabio" );
		user.setSurname( "Mori" );
		user.setUserid( "fabio.mori" );
		entityManager.persist( user );
		
		MeasurementSession measurementSession = MeasurementSessionFactory.createMeasurementSession();
		measurementSession.setSurveySchedule( surveySchedule );
		measurementSession.setType( type );
		measurementSession.setAuthor( user );
		measurementSession.setStatus( MeasurementSessionStatus.RUNNING );
		
		entityManager.persist( measurementSession );

		uuid = measurementSession.getUuid();
	}
	
	@Test
	public void testMeasurementSession() {
		MeasurementSession measurementSession = (MeasurementSession)
			entityManager
				.createQuery( 
					"select p " +
					" from MeasurementSession p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( measurementSession );
		assertNotNull( measurementSession.getSurveySchedule() );
		assertNotNull( measurementSession.getType() );
		assertNotNull( measurementSession.getAuthor() );
		assertEquals( MeasurementSessionStatus.RUNNING, measurementSession.getStatus() );
	}
	
}
