/*
package it.unifi.ing.stlab.empedocle.services.surveySchedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.surveySchedule.SurveySchedule;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.surveySchedule.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.surveySchedule.Service;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.surveySchedule.Services;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.observableEntity.Identifiers;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.observableEntity.ObservableEntity;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.observableEntity.Place;
import it.unifi.ing.stlab.empedocle.services.surveySchedules.jaxb.observableEntity.Sex;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.dao.UserDao;

import java.util.Date;

import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SurveyScheduleServiceTest extends PersistenceTest {
	
	private SurveyScheduleServiceBean surveyScheduleService;
	
	protected ObservableEntityDao observableEntityDao;
	protected ServiceDao serviceDao;
	protected MeasurementSessionDao measurementSessionDao;		
	protected AgendaDao agendaDao;
	protected UserDao userDao;
	protected Logger logger;
	protected UserTransaction utx;
	
	@Override 
	protected void insertData() throws Exception {
		surveyScheduleService = new SurveyScheduleServiceBean();
		
		observableEntityDao = mock( ObservableEntityDao.class );
		serviceDao = mock( ServiceDao.class );
		measurementSessionDao = mock( MeasurementSessionDao.class );
		agendaDao = mock( AgendaDao.class );
		userDao = mock( UserDao.class );
		logger = mock( Logger.class );
		utx = mock( UserTransaction.class );
		
		FieldUtils.assignField( surveyScheduleService, "entityManager", entityManager);
		FieldUtils.assignField( surveyScheduleService, "observableEntityDao", observableEntityDao );
		FieldUtils.assignField( surveyScheduleService, "serviceDao", serviceDao );
		FieldUtils.assignField( surveyScheduleService, "measurementSessionDao", measurementSessionDao );
		FieldUtils.assignField( surveyScheduleService, "agendaDao", agendaDao );
		FieldUtils.assignField( surveyScheduleService, "userDao", userDao );
		FieldUtils.assignField( surveyScheduleService, "logger", logger );
		FieldUtils.assignField( surveyScheduleService, "utx", utx );
		
		ObservableEntity observableEntityDetails = init_observableEntityDetails();
		SurveySchedule surveyScheduleDetails = init_surveyScheduleDetails();

		when( userDao.findByUsername( "administrator" )).thenReturn( null );
		when( observableEntityDao.findByIdentifier( observableEntityDetails.getIdentifiers().getIdAce() )).thenReturn( null );
		when( measurementSessionDao.findBySurveyScheduleCodes(survey_scheduleDetails.getBookingCode(), surveyScheduleDetails.getAcceptanceCode()))
			.thenReturn( null );
		when( serviceDao.find( surveyScheduleDetails.getServices().getService().get(0).getCode(), surveyScheduleDetails.getServices().getService().get(0).getDescription(), survey_scheduleDetails.getAgenda() )).thenReturn( null );
		when( agendaDao.findByCode( surveyScheduleDetails.getAgenda() )).thenReturn( null );
		
		doNothing().when( utx ).begin();
		doNothing().when( utx ).commit();
		doNothing().when( logger ).info( anyString() );
		surveyScheduleService.handleSurveySchedule( "BOOK", new Date(), observableEntityDetails, surveyScheduleDetails);
	}

	@Test
	public void testObservableEntityNotFound() {
		Agenda agenda = (Agenda) entityManager
				.createQuery(
						"select a " 
							+ " from Agenda a "
							+ " where a.code = :agendaCode")
				.setParameter( "agendaCode", "agenda_code" )
				.getSingleResult();

		assertNotNull( agenda );
		assertEquals( "agenda_code", agenda.getCode() );
	}
	
	private SurveySchedule init_surveyScheduleDetails() {
		SurveySchedule surveyScheduleDetails = new SurveySchedule();
		
		surveyScheduleDetails.setDate( new Date() );
		surveyScheduleDetails.setStatus( SurveyScheduleStatus.BOOKED );
		surveyScheduleDetails.setNumber( "number" );
		surveyScheduleDetails.setBookingCode( "booking_code" );
		surveyScheduleDetails.setAcceptanceCode( "acceptance_code" );
		surveyScheduleDetails.setAgenda( "agenda_code" );
		
		Services services = new Services();
		Service service = new Service();
		service.setCode( "service_code" );
		service.setDescription( "service_description" );
		services.getService().add( service );
		
		surveyScheduleDetails.setServices( services );
		
		return surveyScheduleDetails;
	}
	
	private ObservableEntity init_observableEntityDetails() {
		ObservableEntity observableEntityDetails = new ObservableEntity();
		
		Identifiers ids = new Identifiers();
		ids.setIdAce( "id_ace" );
		ids.setTaxCode( "GVNVRD84T03A450Z" );
		observableEntityDetails.setIdentifiers( ids );
		
		observableEntityDetails.setName( "Giovanni" );
		observableEntityDetails.setSurname( "Verdi" );
		observableEntityDetails.setBirthDate( new Date() );
		observableEntityDetails.setSex( Sex.M );
		
		Place birth_place = new Place();
		birth_place.setCity( "FIRENZE" );
		observableEntityDetails.setBirthPlace( birth_place );
		
		return observableEntityDetails;
	}

}
*/
