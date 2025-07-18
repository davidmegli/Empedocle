package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntitySplitAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//TODO: rivedere

@Entity
@DiscriminatorValue( "SP" )
public class WoodElementSplitAction
		extends ObservableEntitySplitAction<
		WoodElement,
		WoodElementSplitAction> {

	public WoodElementSplitAction(String uuid) {
		super(uuid);
	}
	protected WoodElementSplitAction() {
		super();
	}

}
