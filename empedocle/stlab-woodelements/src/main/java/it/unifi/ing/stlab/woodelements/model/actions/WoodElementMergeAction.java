package it.unifi.ing.stlab.observableentities.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "MR" )
public class ObservableEntityMergeAction
	extends ObservableEntityAction
	implements MergeAction<ObservableEntity,ObservableEntityAction,User,Time> {

	public ObservableEntityMergeAction(String uuid) {
		super(uuid);
		setDelegate( new MergeActionImpl<ObservableEntity,ObservableEntityAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected ObservableEntityMergeAction() {
		super();
		setDelegate( new MergeActionImpl<ObservableEntity,ObservableEntityAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected MergeActionImpl<ObservableEntity, ObservableEntityAction, User, Time> getDelegate() {
		return (MergeActionImpl<ObservableEntity, ObservableEntityAction, User, Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public ObservableEntity getSource1() {
		return getDelegate().getSource1();
	}
	protected void setSource1(ObservableEntity source1) {
		getDelegate().setSource1(source1);
	}
	public void assignSource1(ObservableEntity newSource1) {
		getDelegate().assignSource1(newSource1);
	}

	
	@ManyToOne
	@JoinColumn( name = "source2_id" )
	public ObservableEntity getSource2() {
		return getDelegate().getSource2();
	}
	protected void setSource2(ObservableEntity source2) {
		getDelegate().setSource2(source2);
	}
	public void assignSource2(ObservableEntity newSource2) {
		getDelegate().assignSource2(newSource2);
	}

	
	@ManyToOne
	@JoinColumn( name = "target_id" )
	public ObservableEntity getTarget() {
		return getDelegate().getTarget();
	}
	protected void setTarget(ObservableEntity target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(ObservableEntity newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
