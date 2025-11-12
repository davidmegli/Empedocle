package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.MeasurementSession;

import java.util.UUID;

public class MeasurementSessionFactory {

	public static MeasurementSession createMeasurementSession() {
		return new MeasurementSession( UUID.randomUUID().toString() );
	}
}
