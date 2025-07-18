package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityDeleteAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "DL" )
public class WoodElementDeleteAction
	extends ObservableEntityDeleteAction<
		WoodElement,
		WoodElementDeleteAction>{

	public WoodElementDeleteAction(String uuid) {
		super(uuid);
	}
	protected WoodElementDeleteAction() {
		super();
	}

}
