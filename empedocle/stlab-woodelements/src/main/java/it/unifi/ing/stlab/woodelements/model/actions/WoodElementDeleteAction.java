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

@Entity
@DiscriminatorValue( "DL" )
public class ObservableEntityDeleteAction
	extends ObservableEntityAction
	implements DeleteAction<ObservableEntity,ObservableEntityAction,User,Time> {

	public ObservableEntityDeleteAction(String uuid) {
		super(uuid);
		setDelegate( new DeleteActionImpl<ObservableEntity,ObservableEntityAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected ObservableEntityDeleteAction() {
		super();
		setDelegate( new DeleteActionImpl<ObservableEntity,ObservableEntityAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected DeleteActionImpl<ObservableEntity, ObservableEntityAction, User, Time> getDelegate() {
		return (DeleteActionImpl<ObservableEntity,ObservableEntityAction,User,Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public ObservableEntity getSource() {
		return getDelegate().getSource();
	}
	protected void setSource(ObservableEntity source) {
		getDelegate().setSource(source);
	}
	public void assignSource(ObservableEntity newSource) {
		getDelegate().assignSource(newSource);
	}
}
