package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
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
	extends WoodElementAction{

	@Transient
	private ObservableEntityCreateAction observableEntityCreateAction;

	public WoodElementCreateAction(String uuid) {
		super(uuid);
		this.observableEntityCreateAction = new ObservableEntityCreateAction(uuid);
	}
	protected WoodElementCreateAction() {
		super();
		this.observableEntityCreateAction = new ObservableEntityCreateAction();
	}

	// Getters and setters
	public ObservableEntityCreateAction<WoodElement, WoodElementAction> getObservableEntityCreateAction() {
		return observableEntityCreateAction;
	}
	public void setObservableEntityCreateAction(ObservableEntityCreateAction<WoodElement, WoodElementAction> delegate) {
		this.observableEntityCreateAction = delegate;
	}

	// Delegated methods
	@ManyToOne
	@JoinColumn(name = "target_id")
	public WoodElement getTarget() {
		return (WoodElement) observableEntityCreateAction.getTarget();
	}

	public void setTarget(WoodElement target) {
		observableEntityCreateAction.setTarget(target);
	}

	public void assignTarget(WoodElement newTarget) {
		observableEntityCreateAction.assignTarget(newTarget);
	}

	@Transient
	public CreateActionImpl<WoodElement, WoodElementAction, User, Time> getDelegate() {
		return observableEntityCreateAction.getDelegate();
	}

}
