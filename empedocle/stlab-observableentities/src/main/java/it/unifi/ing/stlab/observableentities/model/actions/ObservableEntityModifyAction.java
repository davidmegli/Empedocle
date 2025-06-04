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

@Entity
@DiscriminatorValue( "MD" )
public class ObservableEntityModifyAction
	<T extends ObservableEntity, A extends ObservableEntityAction<T, A>>
	extends ObservableEntityAction
	implements ModifyAction<T,A,User,Time> {

	public ObservableEntityModifyAction(String uuid) {
		super(uuid);
		setDelegate( new ModifyActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected ObservableEntityModifyAction() {
		super();
		setDelegate( new ModifyActionImpl<T,A,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected ModifyActionImpl<T,A,User,Time> getDelegate() {
		return (ModifyActionImpl<T,A,User,Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public T getSource() {
		return getDelegate().getSource();
	}
	protected void setSource(T source) {
		getDelegate().setSource(source);
	}
	public void assignSource(T newSource) {
		getDelegate().assignSource(newSource);
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
