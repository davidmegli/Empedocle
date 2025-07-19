package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//TODO: riguardare

@Entity
@DiscriminatorValue( "MD" )
public class WoodElementModifyAction
	extends WoodElementAction
	implements ModifyAction<WoodElement, WoodElementAction, User, Time> {

	@Transient
	private ObservableEntityModifyAction observableEntityModifyAction;

	public WoodElementModifyAction(String uuid) {
		super(uuid);
		this.observableEntityModifyAction = new ObservableEntityModifyAction(uuid);
		System.out.println("WOODELEMENTMODIFYACTION: "+ observableEntityModifyAction.getDelegate());
	}
	public WoodElementModifyAction() {
		super();
		this.observableEntityModifyAction = new ObservableEntityModifyAction();
	}

	@Transient
	public ModifyActionImpl<WoodElement,WoodElementAction,User,Time> getDelegate() {
		return (ModifyActionImpl<WoodElement,WoodElementAction,User,Time>)observableEntityModifyAction.getDelegate();
	}

	@ManyToOne
	@JoinColumn( name = "source_id" )
	public WoodElement getSource() {
		return (WoodElement) observableEntityModifyAction.getSource();
	}
	public void setSource(WoodElement source) {
		observableEntityModifyAction.setSource(source);
	}
	public void assignSource(WoodElement newSource) {
		observableEntityModifyAction.assignSource(newSource);
	}


	@ManyToOne
	@JoinColumn( name = "target_id" )
	public WoodElement getTarget() {
		return (WoodElement) observableEntityModifyAction.getTarget();
	}
	public void setTarget(WoodElement target) {
		observableEntityModifyAction.setTarget(target);
	}
	public void assignTarget(WoodElement newTarget) {
		observableEntityModifyAction.assignTarget(newTarget);
	}

}
