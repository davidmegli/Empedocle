package it.unifi.ing.stlab.woodelements.factory;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementCreateAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementDeleteAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementModifyAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementSplitAction;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityActionFactory;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;


public class WoodElementActionFactory
		extends ObservableEntityActionFactory<
		WoodElement,
		WoodElementAction>{


	public WoodElementAction createAction() {
		return new WoodElementCreateAction( UUID.randomUUID().toString() );
	}


	public WoodElementAction modifyAction() {
		WoodElementModifyAction woodelementmodifyaction = new WoodElementModifyAction( UUID.randomUUID().toString() );
		return woodelementmodifyaction;
	}


	public WoodElementAction mergeAction() {
		return new WoodElementMergeAction( UUID.randomUUID().toString() );
	}


	public WoodElementAction splitAction() {
		return new WoodElementSplitAction( UUID.randomUUID().toString() );
	}


	public WoodElementAction deleteAction() {
		return new WoodElementDeleteAction( UUID.randomUUID().toString() );
	}

}
