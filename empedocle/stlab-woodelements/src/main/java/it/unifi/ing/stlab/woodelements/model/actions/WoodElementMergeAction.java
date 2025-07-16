package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//TODO: riguardare

@Entity
@DiscriminatorValue( "MR" )
public class WoodElementMergeAction
	extends ObservableEntityMergeAction<
		WoodElement,
		WoodElementAction> {

	public WoodElementMergeAction(String uuid) {
		super(uuid);
	}
	protected WoodElementMergeAction() {
		super();
	}
}
