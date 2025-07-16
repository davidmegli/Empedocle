package it.unifi.ing.stlab.woodelements.factory;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementCreateAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementDeleteAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementModifyAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementSplitAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;


public class WoodElementActionFactory
		extends ObservableEntityActionFactory<
		WoodElement,
		WoodElementAction>{

	@Override
	protected WoodElementAction createAction() {
		return new WoodElementCreateAction( UUID.randomUUID().toString() );
	}

	@Override
	protected WoodElementAction modifyAction() {
		return new WoodElementModifyAction( UUID.randomUUID().toString() );
	}

	@Override
	protected WoodElementAction mergeAction() {
		return new WoodElementMergeAction( UUID.randomUUID().toString() );
	}

	@Override
	protected WoodElementAction splitAction() {
		return new WoodElementSplitAction( UUID.randomUUID().toString() );
	}

	@Override
	protected WoodElementAction deleteAction() {
		return new WoodElementDeleteAction( UUID.randomUUID().toString() );
	}

}
