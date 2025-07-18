package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//TODO: riguardare

@Entity
@DiscriminatorValue( "MD" )
public class WoodElementModifyAction
	extends ObservableEntityModifyAction<
		WoodElement,
		WoodElementAction> {

	public WoodElementModifyAction(String uuid) {
		super(uuid);
	}
	protected WoodElementModifyAction() {
		super();
	}

}
