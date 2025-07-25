package it.unifi.ing.stlab.observableentities.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
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
public class ObservableEntityModifyAction
	<T extends ObservableEntity<T, A, ?, ?>, A extends ObservableEntityAction<T, A,User,Time>>
	extends ObservableEntityAction<T,A,User,Time>
	implements ModifyAction<T,A,User,Time> {

	public ObservableEntityModifyAction(String uuid) {
		super(uuid);
		setDelegate( new ModifyActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( (A)this );
	}
	public ObservableEntityModifyAction() {
		super();
		setDelegate( new ModifyActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( (A)this );
	}

	public ModifyActionImpl<T,A,User,Time> getDelegate() {
		return (ModifyActionImpl<T,A,User,Time>)super.getDelegate();
	}

	public T getSource() {
		return getDelegate().getSource();
	}
	public void setSource(T source) {
		getDelegate().setSource(source);
	}
	public void assignSource(T newSource) {
		getDelegate().assignSource(newSource);
	}


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
