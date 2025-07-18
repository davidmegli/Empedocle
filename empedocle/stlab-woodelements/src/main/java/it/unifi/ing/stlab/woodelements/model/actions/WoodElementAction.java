package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;


@Entity
@Table(name = "wood_element_actions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "action_type",
		discriminatorType = DiscriminatorType.STRING
)
public abstract class WoodElementAction
		extends ObservableEntityAction<WoodElement, WoodElementAction, User, Time> implements Action<WoodElement, WoodElementAction, User, Time>{

	public WoodElementAction(String uuid) {
		super(uuid);
	}

	protected WoodElementAction() {
		super();
	}
}
