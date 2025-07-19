package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntitySplitAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "SP" )
public class WoodElementSplitAction
	extends WoodElementAction
	implements SplitAction<WoodElement, WoodElementAction, User, Time> {

	@Transient
	private ObservableEntitySplitAction observableEntitySplitAction;

	public WoodElementSplitAction(String uuid) {
		super(uuid);
		this.observableEntitySplitAction = new ObservableEntitySplitAction(uuid);
	}
	public WoodElementSplitAction() {
		super();
		this.observableEntitySplitAction = new ObservableEntitySplitAction();
	}

	@Transient
	public SplitActionImpl<WoodElement,WoodElementAction,User,Time> getDelegate() {
		return (SplitActionImpl<WoodElement,WoodElementAction,User,Time>)observableEntitySplitAction.getDelegate();
	}

	@ManyToOne
	@JoinColumn( name = "source_id" )
	public WoodElement getSource() {
		return (WoodElement) observableEntitySplitAction.getSource();
	}
	public void setSource(WoodElement source) {
		observableEntitySplitAction.setSource(source);
	}
	public void assignSource(WoodElement newSource) {
		observableEntitySplitAction.assignSource(newSource);
	}


	@ManyToOne
	@JoinColumn( name = "target1_id" )
	public WoodElement getTarget1() {
		return (WoodElement) observableEntitySplitAction.getTarget1();
	}
	public void setTarget1(WoodElement target1) {
		observableEntitySplitAction.setTarget1(target1);
	}
	public void assignTarget1(WoodElement newTarget1) {
		observableEntitySplitAction.assignTarget1(newTarget1);
	}


	@ManyToOne
	@JoinColumn( name = "target2_id" )
	public WoodElement getTarget2() {
		return (WoodElement) observableEntitySplitAction.getTarget2();
	}
	public void setTarget2(WoodElement target2) {
		observableEntitySplitAction.setTarget2(target2);
	}
	public void assignTarget2(WoodElement newTarget2) {
		observableEntitySplitAction.assignTarget2(newTarget2);
	}

}
