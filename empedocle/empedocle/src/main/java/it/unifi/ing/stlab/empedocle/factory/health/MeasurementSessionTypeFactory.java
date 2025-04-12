package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;

import java.util.UUID;

public class MeasurementSessionTypeFactory {

	
	public static MeasurementSessionType createMeasurementSessionType() {
		return new MeasurementSessionType( UUID.randomUUID().toString() );
	}
}
