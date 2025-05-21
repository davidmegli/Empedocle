package it.unifi.ing.stlab.empedocle.actions.viewer;

import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.Address;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.Sex;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.User;

import java.util.Date;
import java.util.UUID;

public class MeasurementSessionRandomInitializer {
	/*

	public FactContext initNewMeasurementSession(){
		MeasurementSession measurementSession = MeasurementSessionFactory.createMeasurementSession();
		Agenda agenda = initAgenda();
		Service service = initService( agenda );
		ObservableEntity observableEntity = initObservableEntity();
		SurveySchedule surveySchedule = initSurveySchedule( observableEntity, service );
		measurementSession.setSurveySchedule( surveySchedule );
		measurementSession.setLastUpdate( new Date() );
		measurementSession.setAuthor( initUser() );
		
		return measurementSession;
	}
	
	private SurveySchedule initSurveySchedule(ObservableEntity observableEntity, Service service){
		SurveySchedule surveySchedule = new SurveySchedule(UUID.randomUUID().toString());
		surveySchedule.setDate(new Date());
		surveySchedule.setAcceptanceCode("AXX120XX83149");
		surveySchedule.setBookingCode("0901XXX882XXX32");
		surveySchedule.setNumber("123456");
		surveySchedule.setObservableEntity(observableEntity);
		surveySchedule.addService(service);
		surveySchedule.setAgenda(service.getAgenda());
		
		return surveySchedule;
	}
	
	private Service initService(Agenda agenda) {
		Service service = ServiceFactory.createService();
		service.setCode("SERVICE CODE XYZ");
		service.setDescription("SERVICE DESCRIPTION XYZ");
		service.setAgenda(agenda);
		
		return service;
	}
	
	private ObservableEntity initObservableEntity() {
		ObservableEntity observableEntity = ObservableEntityFactory.createObservableEntity();
		
		observableEntity.setAsl("AZ. USL 10 - FIRENZE");
		observableEntity.setTaxCode("RSSGVN14F56P543D");
		observableEntity.setSsnCode("XYZ");
		observableEntity.setSurname("ROSSI");
		observableEntity.setBirthDate(new Date());
		observableEntity.setBirthPlace("FIRENZE");
		observableEntity.setNationality("ITALIA");
		observableEntity.setName("GIOVANNI");
		observableEntity.setRegion("TOSCANA");
		observableEntity.setSex(Sex.M);
		observableEntity.setHomePhone("055 667788");
		observableEntity.setWorkPhone("329 345678901");
		
		Address domicile = new Address();
		domicile.setPlace("VIA DI VILLA MAGNA 76 FIRENZE");
		observableEntity.setDomicile(domicile);
		
		Address residence = new Address();
		residence.setPlace("PARCO DELLA VITTORIA XX FIRENZE");
		observableEntity.setResidence(residence);
		
		return observableEntity;
	}
	
	private Agenda initAgenda(){
		Agenda agenda = AgendaFactory.createAgenda();
		
		agenda.setCode("50X3");
		agenda.setDescription("DESCRIZIONE AGENDA - PROVA PER TEST");
		
		return agenda;
	}
	
	private User initUser(){
		User user = UserFactory.createUser();
		user.setName("Nome");
		user.setSurname("Cognome");
		
		Qualification qualification1 = QualificationFactory.createQualification();
		qualification1.setName("medico");
		user.addQualification(qualification1);
		
		return user;
	}


	 */
}
