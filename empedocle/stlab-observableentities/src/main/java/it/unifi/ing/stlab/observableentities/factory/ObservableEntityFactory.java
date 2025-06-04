package it.unifi.ing.stlab.observableentities.factory;

import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;

import java.util.UUID;

public abstract class ObservableEntityFactory <T extends ObservableEntity, I extends ObservableEntityIdentifier> {

	public static T createObservableEntity() {
		T result = new T( UUID.randomUUID().toString() );
		result.init();
		return result;
	}
	
	public static I createObservableEntityIdentifier() {
		I result = new I( UUID.randomUUID().toString() );
		return result;
	}
}
