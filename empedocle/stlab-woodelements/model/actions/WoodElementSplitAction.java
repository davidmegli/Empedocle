package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;
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
@DiscriminatorValue( "SP" )
public class WoodElementSplitAction
	extends WoodElementAction
	implements SplitAction<WoodElement,WoodElementAction,User,Time> {

	public WoodElementSplitAction(String uuid) {
		super(uuid);
		setDelegate( new SplitActionImpl<WoodElement,WoodElementAction,User,Time>());
		getDelegate().setDelegator( this );
	}
	protected WoodElementSplitAction() {
		super();
		setDelegate( new SplitActionImpl<WoodElement,WoodElementAction,User,Time>());
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected SplitActionImpl<WoodElement,WoodElementAction,User,Time> getDelegate() {
		return (SplitActionImpl<WoodElement,WoodElementAction,User,Time>)super.getDelegate();
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
	@JoinColumn( name = "target1_id" )
	public WoodElement getTarget1() {
		return getDelegate().getTarget1();
	}
	protected void setTarget1(WoodElement target1) {
		getDelegate().setTarget1(target1);
	}
	public void assignTarget1(WoodElement newTarget1) {
		getDelegate().assignTarget1(newTarget1);
	}

	
	@ManyToOne
	@JoinColumn( name = "target2_id" )
	public WoodElement getTarget2() {
		return getDelegate().getTarget2();
	}
	protected void setTarget2(WoodElement target2) {
		getDelegate().setTarget2(target2);
	}
	public void assignTarget2(WoodElement newTarget2) {
		getDelegate().assignTarget2(newTarget2);
	}

}
