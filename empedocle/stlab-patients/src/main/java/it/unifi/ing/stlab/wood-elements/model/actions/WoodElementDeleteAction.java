package it.unifi.ing.stlab.wood-elements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.wood-elements.model.WoodElement;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "DL" )
public class WoodElementDeleteAction
	extends WoodElementAction
	implements DeleteAction<WoodElement,WoodElementAction,User,Time> {

	public WoodElementDeleteAction(String uuid) {
		super(uuid);
		setDelegate( new DeleteActionImpl<WoodElement,WoodElementAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected WoodElementDeleteAction() {
		super();
		setDelegate( new DeleteActionImpl<WoodElement,WoodElementAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected DeleteActionImpl<WoodElement, WoodElementAction, User, Time> getDelegate() {
		return (DeleteActionImpl<WoodElement,WoodElementAction,User,Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public WoodElement getSource() {
		return getDelegate().getSource();
	}
	protected void setSource(WoodElement source) {
		getDelegate().setSource(source);
	}
	public void assignSource(WoodElement newSource) {
		getDelegate().assignSource(newSource);
	}
}
