package it.unifi.ing.stlab.observableentities.factory;

import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;

import java.util.UUID;

public abstract class ObservableEntityFactory <T extends ObservableEntity, I extends ObservableEntityIdentifier> {

	public abstract T createObservableEntity();

	public abstract I createObservableEntityIdentifier();
	/*
	// Example implementation of a concrete factory for a specific type of ObservableEntity

	private static WoodElementFactory instance = new WoodElementFactory(); --> DA CONFERMARE

	public static WoodElementFactory getInstance() {
        return instance;
    }

	@Override
    public WoodElement createObservableEntity() {
        WoodElement result = new WoodElement(UUID.randomUUID().toString());
        result.init();
        return result;
    }

    @Override
    public WoodElementIdentifier createObservableEntityIdentifier() {
        return new WoodElementIdentifier(UUID.randomUUID().toString());
    }
	// Example usage:
	WoodElementFactory factory = new WoodElementFactory();
	WoodElement element = factory.createObservableEntity();
	WoodElementIdentifier identifier = factory.createObservableEntityIdentifier();
	 */
}
