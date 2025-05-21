package it.unifi.ing.stlab.observableentities.factory;

import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;

import java.util.UUID;

public class ObservableEntityFactory {

	public static ObservableEntity createObservableEntity() {
		ObservableEntity result = new ObservableEntity( UUID.randomUUID().toString() );
		result.init();
		return result;
	}
	
	public static ObservableEntityIdentifier createObservableEntityIdentifier() {
		ObservableEntityIdentifier result = new ObservableEntityIdentifier( UUID.randomUUID().toString() );
		return result;
	}
}
