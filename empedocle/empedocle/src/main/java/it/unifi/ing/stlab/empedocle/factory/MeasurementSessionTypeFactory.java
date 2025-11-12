package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.MeasurementSessionType;

import java.util.UUID;

public class MeasurementSessionTypeFactory {

	
	public static MeasurementSessionType createMeasurementSessionType() {
		return new MeasurementSessionType( UUID.randomUUID().toString() );
	}
}
