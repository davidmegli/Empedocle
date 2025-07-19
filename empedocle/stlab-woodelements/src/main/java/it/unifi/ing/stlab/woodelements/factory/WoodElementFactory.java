package it.unifi.ing.stlab.woodelements.factory;

import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;

import java.util.UUID;

public class WoodElementFactory extends ObservableEntityFactory<WoodElement, WoodElementIdentifier> {

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
	public WoodElementIdentifier createIdentifier() {
		WoodElementIdentifier identifier = super.createIdentifier();
		return identifier;
	}
}

