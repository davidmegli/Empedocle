package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "MR" )
public class WoodElementMergeAction
	extends WoodElementAction
	implements MergeAction<WoodElement,WoodElementAction,User,Time> {

	public WoodElementMergeAction(String uuid) {
		super(uuid);
		setDelegate( new MergeActionImpl<WoodElement,WoodElementAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected WoodElementMergeAction() {
		super();
		setDelegate( new MergeActionImpl<WoodElement,WoodElementAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected MergeActionImpl<WoodElement, WoodElementAction, User, Time> getDelegate() {
		return (MergeActionImpl<WoodElement, WoodElementAction, User, Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public WoodElement getSource1() {
		return getDelegate().getSource1();
	}
	protected void setSource1(WoodElement source1) {
		getDelegate().setSource1(source1);
	}
	public void assignSource1(WoodElement newSource1) {
		getDelegate().assignSource1(newSource1);
	}

	
	@ManyToOne
	@JoinColumn( name = "source2_id" )
	public WoodElement getSource2() {
		return getDelegate().getSource2();
	}
	protected void setSource2(WoodElement source2) {
		getDelegate().setSource2(source2);
	}
	public void assignSource2(WoodElement newSource2) {
		getDelegate().assignSource2(newSource2);
	}

	
	@ManyToOne
	@JoinColumn( name = "target_id" )
	public WoodElement getTarget() {
		return getDelegate().getTarget();
	}
	protected void setTarget(WoodElement target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(WoodElement newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
