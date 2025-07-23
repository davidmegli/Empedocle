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

/*
 * To create a specialized CreateAction for a specific ObservableEntity type, follow these steps:
 *
 * 1. Create a concrete subclass (e.g., MyEntityCreateAction) extending your specific Action base class
 *    (e.g., MyEntityAction) and implementing CreateAction<T, A, User, Time>.
 *
 * 2. In the constructor, initialize and set the delegate:
 *      setDelegate(new CreateActionImpl<>());
 *      getDelegate().setDelegator(this);
 *
 * 3. Override the methods getTarget(), setTarget(T), assignTarget(T), and getDelegate()
 *    delegating them to the CreateActionImpl.
 *
 * Example:
 *
 * @Entity
 * @DiscriminatorValue("CR")
 * public class MyEntityCreateAction extends MyEntityAction
 *       implements CreateAction<MyEntity, MyEntityAction, User, Time> {
 *
 *     public MyEntityCreateAction(String uuid) {
 *         super(uuid);
 *         setDelegate(new CreateActionImpl<>());
 *         getDelegate().setDelegator(this);
 *     }
 *
 *     protected MyEntityCreateAction() {
 *         super();
 *         setDelegate(new CreateActionImpl<>());
 *         getDelegate().setDelegator(this);
 *     }
 *
 *     @ManyToOne
 *     @JoinColumn(name = "target_id")
 *     public MyEntity getTarget() {
 *         return getDelegate().getTarget();
 *     }
 *
 *     public void setTarget(MyEntity target) {
 *         getDelegate().setTarget(target);
 *     }
 *
 *     public void assignTarget(MyEntity newTarget) {
 *         getDelegate().assignTarget(newTarget);
 *     }
 *
 *     @Transient
 *     public CreateActionImpl<MyEntity, MyEntityAction, User, Time> getDelegate() {
 *         return (CreateActionImpl<MyEntity, MyEntityAction, User, Time>) super.getDelegate();
 *     }
 * }
 */

public class ObservableEntityCreateAction
	<T extends ObservableEntity<T, A, ?, ?>, A extends ObservableEntityAction<T, A,User,Time>>
	extends ObservableEntityAction<T,A,User,Time>
	implements CreateAction<T,A,User,Time> {

	public ObservableEntityCreateAction(String uuid) {
		super(uuid);
		setDelegate( new CreateActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( (A) this );
	}
	public ObservableEntityCreateAction() {
		super();
		setDelegate( new CreateActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator((A) this );
	}

	public CreateActionImpl<T, A, User, Time> getDelegate() {
		return (CreateActionImpl<T, A, User, Time>)super.getDelegate();
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
