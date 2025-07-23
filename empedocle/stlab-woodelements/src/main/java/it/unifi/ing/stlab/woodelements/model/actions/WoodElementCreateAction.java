package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityCreateAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "CR" )
public class WoodElementCreateAction
	extends WoodElementAction
	implements CreateAction<WoodElement, WoodElementAction, User, Time> {

	public WoodElementCreateAction(String uuid) {
		super(uuid);
		setDelegate(new CreateActionImpl<>());
		getDelegate().setDelegator(this);
	}
	protected WoodElementCreateAction() {
		super();
		setDelegate(new CreateActionImpl<>());
		getDelegate().setDelegator(this);
	}

	// Delegated methods
	@ManyToOne
	@JoinColumn(name = "target_id")
	public WoodElement getTarget() {
		return (WoodElement) getDelegate().getTarget();
	}

	public void setTarget(WoodElement target) {
		getDelegate().setTarget(target);
	}

	public void assignTarget(WoodElement newTarget) {
		getDelegate().assignTarget(newTarget);
	}

	@Transient
	public CreateActionImpl<WoodElement, WoodElementAction, User, Time> getDelegate() {
		return (CreateActionImpl<WoodElement, WoodElementAction, User, Time>) super.getDelegate();
	}
}
