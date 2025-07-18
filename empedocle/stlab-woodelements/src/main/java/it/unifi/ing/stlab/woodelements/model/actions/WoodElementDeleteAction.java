package it.unifi.ing.stlab.woodelements.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityDeleteAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
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
	extends WoodElementAction {

	@Transient
	private ObservableEntityDeleteAction observableEntityDeleteAction;

	public WoodElementDeleteAction(String uuid) {
		super(uuid);
		this.observableEntityDeleteAction = new ObservableEntityDeleteAction(uuid);
	}
	protected WoodElementDeleteAction() {
		super();
		this.observableEntityDeleteAction = new ObservableEntityDeleteAction();
	}

	// Getters and setters
	public ObservableEntityDeleteAction<WoodElement, WoodElementAction> getObservableEntityDeleteAction() {
		return observableEntityDeleteAction;
	}
	public void setObservableEntityDeleteAction(ObservableEntityDeleteAction<WoodElement, WoodElementAction> delegate) {
		this.observableEntityDeleteAction = delegate;
	}

	@Transient
	public DeleteActionImpl<WoodElement, WoodElementAction, User, Time> getDelegate() {
		return observableEntityDeleteAction.getDelegate();
	}

	@ManyToOne
	@JoinColumn( name = "source_id" )
	public WoodElement getSource() {
		return (WoodElement) observableEntityDeleteAction.getSource();
	}
	public void setSource(WoodElement source) {
		observableEntityDeleteAction.setSource(source);
	}
	public void assignSource(WoodElement newSource) {
		observableEntityDeleteAction.assignSource(newSource);
	}

}
