package it.unifi.ing.stlab.empedocle.actions.health.measurementSession.types;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;

import javax.persistence.EntityManager;

public class AgendaExamTypeBean {

	private Agenda agenda;
	private final MeasurementSessionType current;
	private final EntityManager entityManager;

	public AgendaExamTypeBean( MeasurementSessionType current, EntityManager entityManager ) {
		this.current = current;
		this.entityManager = entityManager;
	}

	public AgendaExamTypeBean( Agenda agenda, MeasurementSessionType current, EntityManager entityManager ) {
		this.agenda = agenda;
		this.current = current;
		this.entityManager = entityManager;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda( Agenda agenda ) {
		agenda.setMeasurementSessionType( current );
		this.agenda = entityManager.merge( agenda );
	}

	public void clear() {
		if ( agenda == null )
			return;

		agenda.setMeasurementSessionType( null );
		entityManager.merge( agenda );
		agenda = null;
	}

}
