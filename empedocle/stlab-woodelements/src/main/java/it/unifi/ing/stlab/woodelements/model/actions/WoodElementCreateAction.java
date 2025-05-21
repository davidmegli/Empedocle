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
public class ObservableEntityCreateAction
	extends ObservableEntityAction
	implements CreateAction<ObservableEntity,ObservableEntityAction,User,Time> {

	public ObservableEntityCreateAction(String uuid) {
		super(uuid);
		setDelegate( new CreateActionImpl<ObservableEntity,ObservableEntityAction,User,Time>() ); 
		getDelegate().setDelegator( this );
	}
	protected ObservableEntityCreateAction() {
		super();
		setDelegate( new CreateActionImpl<ObservableEntity,ObservableEntityAction,User,Time>() ); 
		getDelegate().setDelegator( this );
	}

	@Transient
	public CreateActionImpl<ObservableEntity, ObservableEntityAction, User, Time> getDelegate() {
		return (CreateActionImpl<ObservableEntity, ObservableEntityAction, User, Time>)super.getDelegate();
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
