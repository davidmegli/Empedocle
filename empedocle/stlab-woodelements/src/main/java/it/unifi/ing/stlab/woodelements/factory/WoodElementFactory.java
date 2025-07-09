package it.unifi.ing.stlab.woodelements.factory;

import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.observablenetities.model.ObservableEntityFactory;

import java.util.UUID;

//TODO: extend class

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
	protected WoodElementIdentifier createIdentifier() {
		WoodElementIdentifier identifier = super.createIdentifier();
		return identifier;
}
