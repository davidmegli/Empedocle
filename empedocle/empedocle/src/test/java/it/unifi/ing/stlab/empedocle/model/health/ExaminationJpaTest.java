package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;

import java.util.Date;

import org.junit.Test;

public class ExaminationJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		ExaminationType type = ExaminationTypeFactory.createExaminationType();
		type.setName( "Examination type" );
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
		
		Examination examination = ExaminationFactory.createExamination();
		examination.setSurveySchedule( surveySchedule );
		examination.setType( type );
		examination.setAuthor( user );
		examination.setStatus( ExaminationStatus.RUNNING );
		
		entityManager.persist( examination );

		uuid = examination.getUuid();
	}
	
	@Test
	public void testExamination() {
		Examination examination = (Examination)
			entityManager
				.createQuery( 
					"select p " +
					" from Examination p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( examination );
		assertNotNull( examination.getSurveySchedule() );
		assertNotNull( examination.getType() );
		assertNotNull( examination.getAuthor() );
		assertEquals( ExaminationStatus.RUNNING, examination.getStatus() );
	}
	
}
