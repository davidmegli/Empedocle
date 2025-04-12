package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;

import java.util.UUID;

public class MeasurementSessionFactory {

	public static MeasurementSession createMeasurementSession() {
		return new MeasurementSession( UUID.randomUUID().toString() );
	}
}
