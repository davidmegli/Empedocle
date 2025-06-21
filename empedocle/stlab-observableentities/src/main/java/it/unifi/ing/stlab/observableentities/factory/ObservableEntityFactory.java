package it.unifi.ing.stlab.observableentities.factory;

import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;

import java.util.UUID;

public abstract class ObservableEntityFactory <T extends ObservableEntity, I extends ObservableEntityIdentifier> {

	public T create() {
		T entity = createConcrateEntity();
		entity.setUuid(generateUuid());
		entity.init(); // logica comune
		return entity;
	}
	public abstract I createIdentifier(){
		I identifier = createConcrateIdentifier();
		identifier.setUuid(generateUuid());
		//identifier.init(); // logica  TODO:capire se aggiure metodo a Identifier o no
		return identifier;
	};

	protected abstract T createConcrateEntity();

	protected abstract I createConcrateIdentifier();

	private String generateUuid() {
		return UUID.randomUUID().toString(); // generatore di ID
	}
	/*
	@Override
    protected WoodElement createConcreteEntity() {
        return new WoodElement();
    }

    @Override
    protected WoodElementIdentifier createConcreteIdentifier() {
        return new WoodElementIdentifier();
    }

    @Override
    public WoodElement create() {
        WoodElement wood = super.create();
        return wood;
    }

    @Override
    protected WoodElementIdentifier createIdentifier() {
        WoodElementIdentifier identifier = super.createIdentifier();
        return identifier;
    }
	 */


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
