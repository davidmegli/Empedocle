package it.unifi.ing.stlab.woodelements.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.woodelements.factory.WoodElementActionFactory;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;


public class WoodElementManager extends ObservableEntityManager<
		WoodElement,
		WoodElementAction,
		WoodElementFactory,
		WoodElementIdentifier> {

	public WoodElementManager() {
		this.factory = new WoodElementFactory();
		this.actionFactory = new WoodElementActionFactory();
	}

	@Override
	protected AbstractActionFactory<WoodElement, WoodElementAction, User, Time> getActionFactory() {
		return new WoodElementActionFactory();
	}
	@Override
	public WoodElement merge( User author, Time time, WoodElement master, WoodElement slave ) {
		WoodElement copy = (master != null) ? master.copy() : (slave != null ? slave.copy() : null);
		return ((WoodElementMergeAction) this.actionFactory
				.mergeAction(author, time, master, slave, copy))
				.getTarget();
	}

}
