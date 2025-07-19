package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//TODO: riguardare

@Entity
@DiscriminatorValue( "MR" )
public class WoodElementMergeAction
	extends WoodElementAction
	implements MergeAction<WoodElement, WoodElementAction, User, Time> {

	@Transient
	private ObservableEntityMergeAction observableEntityMergeAction;

	public WoodElementMergeAction(String uuid) {
		super(uuid);
		this.observableEntityMergeAction = new ObservableEntityMergeAction(uuid);
	}

	protected WoodElementMergeAction() {
		super();
		this.observableEntityMergeAction = new ObservableEntityMergeAction();
	}

	// Getters and setters
	public ObservableEntityMergeAction<WoodElement, WoodElementAction> getObservableEntityMergeAction() {
		return observableEntityMergeAction;
	}
	public void setObservableEntityMergeAction(ObservableEntityMergeAction<WoodElement, WoodElementAction> delegate) {
		this.observableEntityMergeAction = delegate;
	}
	@Transient
	public MergeActionImpl<WoodElement, WoodElementAction, User, Time> getDelegate() {
		return (MergeActionImpl<WoodElement, WoodElementAction, User, Time>)super.getDelegate();
	}

	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public WoodElement getSource1() {
		return (WoodElement) observableEntityMergeAction.getSource1();
	}
	public void setSource1(WoodElement source1) {
		observableEntityMergeAction.setSource1(source1);
	}
	public void assignSource1(WoodElement newSource1) {
		observableEntityMergeAction.assignSource1(newSource1);
	}


	@ManyToOne
	@JoinColumn( name = "source2_id" )
	public WoodElement getSource2() {
		return (WoodElement) observableEntityMergeAction.getSource2();
	}
	public void setSource2(WoodElement source2) {
		observableEntityMergeAction.setSource2(source2);
	}
	public void assignSource2(WoodElement newSource2) {
		observableEntityMergeAction.assignSource2(newSource2);
	}


	@ManyToOne
	@JoinColumn( name = "target_id" )
	public WoodElement getTarget() {
		return (WoodElement) observableEntityMergeAction.getTarget();
	}
	public void setTarget(WoodElement target) {
		observableEntityMergeAction.setTarget(target);
	}
	public void assignTarget(WoodElement newTarget) {
		observableEntityMergeAction.assignTarget(newTarget);
	}

}
