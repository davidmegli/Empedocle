package it.unifi.ing.stlab.empedocle.actions.health.measurementSession.types;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;

import javax.persistence.EntityManager;

public class AgendaMeasurementSessionTypeBean {

	private Agenda agenda;
	private final MeasurementSessionType current;
	private final EntityManager entityManager;

	public AgendaMeasurementSessionTypeBean(MeasurementSessionType current, EntityManager entityManager ) {
		this.current = current;
		this.entityManager = entityManager;
	}

	public AgendaMeasurementSessionTypeBean(Agenda agenda, MeasurementSessionType current, EntityManager entityManager ) {
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
