package it.unifi.ing.stlab.observableentities.factory;

import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;

import java.util.UUID;

public abstract class ObservableEntityFactory
		<T extends ObservableEntity<T, ?, I, ?>, I extends ObservableEntityIdentifier> {

	public T create() {
		T entity = createConcreteEntity();
		entity.setUuid(generateUuid());
		entity.init();
		I id = createIdentifier();
		entity.setIdentifier(id);
		return entity;
	}
	public I createIdentifier(){
		I identifier = createConcreteIdentifier();
		identifier.setUuid(generateUuid());
		return identifier;
	}

	protected abstract T createConcreteEntity();

	protected abstract I createConcreteIdentifier();

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
