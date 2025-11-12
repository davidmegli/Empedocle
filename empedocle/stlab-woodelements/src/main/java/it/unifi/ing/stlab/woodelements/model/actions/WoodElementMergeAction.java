package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.*;

@Entity
@DiscriminatorValue( "MR" )
public class WoodElementMergeAction
	extends WoodElementAction
	implements MergeAction<WoodElement, WoodElementAction, User, Time> {

	public WoodElementMergeAction(String uuid) {
		super(uuid);
		setDelegate(new MergeActionImpl<>());
		getDelegate().setDelegator(this);
	}

	protected WoodElementMergeAction() {
		super();
		setDelegate(new MergeActionImpl<>());
		getDelegate().setDelegator(this);
	}

	@Transient
	public MergeActionImpl<WoodElement, WoodElementAction, User, Time> getDelegate() {
		return (MergeActionImpl<WoodElement, WoodElementAction, User, Time>)super.getDelegate();
	}

	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public WoodElement getSource1() {
		return (WoodElement) getDelegate().getSource1();
	}
	public void setSource1(WoodElement source1) {
		getDelegate().setSource1(source1);
	}
	public void assignSource1(WoodElement newSource1) {
		getDelegate().assignSource1(newSource1);
	}


	@ManyToOne
	@JoinColumn( name = "source2_id" )
	public WoodElement getSource2() {
		return (WoodElement) getDelegate().getSource2();
	}
	public void setSource2(WoodElement source2) {
		getDelegate().setSource2(source2);
	}
	public void assignSource2(WoodElement newSource2) {
		getDelegate().assignSource2(newSource2);
	}


	@ManyToOne(cascade = CascadeType.ALL )
	@JoinColumn( name = "target_id")
	public WoodElement getTarget() {
		return (WoodElement) getDelegate().getTarget();
	}
	public void setTarget(WoodElement target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(WoodElement newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
