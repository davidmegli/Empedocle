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
	<T extends ObservableEntity, A extends ObservableEntityAction<T, A>>
	extends ObservableEntityAction
	implements MergeAction<T,A,User,Time> {

	public ObservableEntityMergeAction(String uuid) {
		super(uuid);
		setDelegate( new MergeActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected ObservableEntityMergeAction() {
		super();
		setDelegate( new MergeActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected MergeActionImpl<T, A, User, Time> getDelegate() {
		return (MergeActionImpl<T, A, User, Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public T getSource1() {
		return getDelegate().getSource1();
	}
	protected void setSource1(T source1) {
		getDelegate().setSource1(source1);
	}
	public void assignSource1(T newSource1) {
		getDelegate().assignSource1(newSource1);
	}

	
	@ManyToOne
	@JoinColumn( name = "source2_id" )
	public T getSource2() {
		return getDelegate().getSource2();
	}
	protected void setSource2(T source2) {
		getDelegate().setSource2(source2);
	}
	public void assignSource2(T newSource2) {
		getDelegate().assignSource2(newSource2);
	}

	
	@ManyToOne
	@JoinColumn( name = "target_id" )
	public T getTarget() {
		return getDelegate().getTarget();
	}
	protected void setTarget(T target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(T newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
