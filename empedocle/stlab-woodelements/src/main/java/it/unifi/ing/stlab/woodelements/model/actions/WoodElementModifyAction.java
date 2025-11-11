package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.*;


//TODO: riguardare

@Entity
@DiscriminatorValue( "MD" )
public class WoodElementModifyAction
	extends WoodElementAction
	implements ModifyAction<WoodElement, WoodElementAction, User, Time> {

	public WoodElementModifyAction(String uuid) {
		super(uuid);
		setDelegate(new ModifyActionImpl<>());
		getDelegate().setDelegator(this);
	}
	public WoodElementModifyAction() {
		super();
		setDelegate(new ModifyActionImpl<>());
		getDelegate().setDelegator(this);
	}

	@Transient
	public ModifyActionImpl<WoodElement,WoodElementAction,User,Time> getDelegate() {
		return (ModifyActionImpl<WoodElement,WoodElementAction,User,Time>)super.getDelegate();
	}

	@ManyToOne
	@JoinColumn( name = "source_id" )
	public WoodElement getSource() {
		return (WoodElement) getDelegate().getSource();
	}
	public void setSource(WoodElement source) {
		getDelegate().setSource(source);
	}
	public void assignSource(WoodElement newSource) {
		getDelegate().assignSource(newSource);
	}


	@OneToOne(mappedBy = "origin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public WoodElement getTarget() {
		return (WoodElement) getDelegate().getTarget();
	}
	public void setTarget(WoodElement target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(WoodElement newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
