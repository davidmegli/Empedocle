package it.unifi.ing.stlab.observableentities.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "CR" )
public abstract class ObservableEntityCreateAction
	<T extends ObservableEntity<T, A, ?, ?>, A extends ObservableEntityAction<T, A,User,Time>>
	extends ObservableEntityAction<T,A,User,Time>
	implements CreateAction<T,A,User,Time> {

	public ObservableEntityCreateAction(String uuid) {
		super(uuid);
		setDelegate( new CreateActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( (A) this );
	}
	protected ObservableEntityCreateAction() {
		super();
		setDelegate( new CreateActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator((A) this );
	}

	@Transient
	public CreateActionImpl<T, A, User, Time> getDelegate() {
		return (CreateActionImpl<T, A, User, Time>)super.getDelegate();
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
