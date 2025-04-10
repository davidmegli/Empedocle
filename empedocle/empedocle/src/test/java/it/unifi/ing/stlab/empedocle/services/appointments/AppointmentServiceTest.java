/*
package it.unifi.ing.stlab.empedocle.services.appointments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.AppointmentStatus;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Service;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Services;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.wood_element.Identifiers;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.wood_element.WoodElement;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.wood_element.Place;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.wood_element.Sex;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.dao.UserDao;

import java.util.Date;

import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.junit.Test;

public class AppointmentServiceTest extends PersistenceTest {
	
	private AppointmentServiceBean appointmentService;
	
	protected WoodElementDao wood_elementDao;
	protected ServiceDao serviceDao;
	protected ExaminationDao examinationDao;		
	protected AgendaDao agendaDao;
	protected UserDao userDao;
	protected Logger logger;
	protected UserTransaction utx;
	
	@Override 
	protected void insertData() throws Exception {
		appointmentService = new AppointmentServiceBean();
		
		wood_elementDao = mock( WoodElementDao.class );
		serviceDao = mock( ServiceDao.class );
		examinationDao = mock( ExaminationDao.class );
		agendaDao = mock( AgendaDao.class );
		userDao = mock( UserDao.class );
		logger = mock( Logger.class );
		utx = mock( UserTransaction.class );
		
		FieldUtils.assignField( appointmentService, "entityManager", entityManager);
		FieldUtils.assignField( appointmentService, "wood_elementDao", wood_elementDao );
		FieldUtils.assignField( appointmentService, "serviceDao", serviceDao );
		FieldUtils.assignField( appointmentService, "examinationDao", examinationDao );
		FieldUtils.assignField( appointmentService, "agendaDao", agendaDao );
		FieldUtils.assignField( appointmentService, "userDao", userDao );
		FieldUtils.assignField( appointmentService, "logger", logger );
		FieldUtils.assignField( appointmentService, "utx", utx );
		
		WoodElement wood_elementDetails = init_wood_elementDetails();
		Appointment appointmentDetails = init_appointmentDetails();

		when( userDao.findByUsername( "administrator" )).thenReturn( null );
		when( wood_elementDao.findByIdentifier( wood_elementDetails.getIdentifiers().getIdAce() )).thenReturn( null );
		when( examinationDao.findByAppointmentCodes(appointmentDetails.getBookingCode(), appointmentDetails.getAcceptanceCode()))
			.thenReturn( null );
		when( serviceDao.find( appointmentDetails.getServices().getService().get(0).getCode(), appointmentDetails.getServices().getService().get(0).getDescription(), appointmentDetails.getAgenda() )).thenReturn( null );
		when( agendaDao.findByCode( appointmentDetails.getAgenda() )).thenReturn( null );
		
		doNothing().when( utx ).begin();
		doNothing().when( utx ).commit();
		doNothing().when( logger ).info( anyString() );
		appointmentService.handleAppointment( "BOOK", new Date(), wood_elementDetails, appointmentDetails);
	}

	@Test
	public void testWoodElementNotFound() {
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
	
	private Appointment init_appointmentDetails() {
		Appointment appointmentDetails = new Appointment();
		
		appointmentDetails.setDate( new Date() );
		appointmentDetails.setStatus( AppointmentStatus.BOOKED );
		appointmentDetails.setNumber( "number" );
		appointmentDetails.setBookingCode( "booking_code" );
		appointmentDetails.setAcceptanceCode( "acceptance_code" );
		appointmentDetails.setAgenda( "agenda_code" );
		
		Services services = new Services();
		Service service = new Service();
		service.setCode( "service_code" );
		service.setDescription( "service_description" );
		services.getService().add( service );
		
		appointmentDetails.setServices( services );
		
		return appointmentDetails;
	}
	
	private WoodElement init_wood_elementDetails() {
		WoodElement wood_elementDetails = new WoodElement();
		
		Identifiers ids = new Identifiers();
		ids.setIdAce( "id_ace" );
		ids.setTaxCode( "GVNVRD84T03A450Z" );
		wood_elementDetails.setIdentifiers( ids );
		
		wood_elementDetails.setName( "Giovanni" );
		wood_elementDetails.setSurname( "Verdi" );
		wood_elementDetails.setBirthDate( new Date() );
		wood_elementDetails.setSex( Sex.M );
		
		Place birth_place = new Place();
		birth_place.setCity( "FIRENZE" );
		wood_elementDetails.setBirthPlace( birth_place );
		
		return wood_elementDetails;
	}

}
*/
