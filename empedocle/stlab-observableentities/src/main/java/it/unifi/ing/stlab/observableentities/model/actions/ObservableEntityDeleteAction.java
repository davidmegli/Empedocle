package it.unifi.ing.stlab.observableentities.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
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

public class ObservableEntityDeleteAction
	<T extends ObservableEntity<T, A, ?, ?>, A extends ObservableEntityAction<T, A,User,Time>>
	extends ObservableEntityAction<T,A,User,Time>
	implements DeleteAction<T,A,User,Time> {

	public ObservableEntityDeleteAction(String uuid) {
		super(uuid);
		setDelegate( new DeleteActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( (A)this );
	}
	public ObservableEntityDeleteAction() {
		super();
		setDelegate( new DeleteActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( (A)this );
	}

	public DeleteActionImpl<T, A, User, Time> getDelegate() {
		return (DeleteActionImpl<T,A,User,Time>)super.getDelegate();
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
}
