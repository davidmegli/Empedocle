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

/*
 * To create a specialized class see the comment in ObservableEntityCreateAction
 * */
public class ObservableEntityMergeAction
	<T extends ObservableEntity<T, A, ?, ?>, A extends ObservableEntityAction<T, A,User,Time>>
	extends ObservableEntityAction<T,A,User,Time>
	implements MergeAction<T,A,User,Time> {

	public ObservableEntityMergeAction(String uuid) {
		super(uuid);
		setDelegate( new MergeActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( (A)this );
	}
	public ObservableEntityMergeAction() {
		super();
		setDelegate( new MergeActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator((A) this );
	}
	
	@Transient
	public MergeActionImpl<T, A, User, Time> getDelegate() {
		return (MergeActionImpl<T, A, User, Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public T getSource1() {
		return getDelegate().getSource1();
	}
	public void setSource1(T source1) {
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
	public void setSource2(T source2) {
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
	public void setTarget(T target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(T newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
