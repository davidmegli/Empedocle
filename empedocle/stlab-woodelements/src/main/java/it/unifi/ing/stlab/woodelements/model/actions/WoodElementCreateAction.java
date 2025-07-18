package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityCreateAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue( "CR" )
public class WoodElementCreateAction
	extends ObservableEntityCreateAction<
		WoodElement,
		WoodElementAction>{

	public WoodElementCreateAction(String uuid) {
		super(uuid);
	}
	protected WoodElementCreateAction() {
		super();
	}
}
