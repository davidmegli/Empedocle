package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//TODO: extend class

@Entity
@DiscriminatorValue( "MD" )
public class WoodElementModifyAction
	extends WoodElementAction
	implements ModifyAction<WoodElement,WoodElementAction,User,Time> {

	public WoodElementModifyAction(String uuid) {
		super(uuid);
		setDelegate( new ModifyActionImpl<WoodElement,WoodElementAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected WoodElementModifyAction() {
		super();
		setDelegate( new ModifyActionImpl<WoodElement,WoodElementAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected ModifyActionImpl<WoodElement,WoodElementAction,User,Time> getDelegate() {
		return (ModifyActionImpl<WoodElement,WoodElementAction,User,Time>)super.getDelegate();
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

	
	@ManyToOne
	@JoinColumn( name = "target_id" )
	public WoodElement getTarget() {
		return getDelegate().getTarget();
	}
	protected void setTarget(WoodElement target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(WoodElement newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
