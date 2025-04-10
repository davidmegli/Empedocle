package it.unifi.ing.stlab.wood-elements.factory;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.wood-elements.model.WoodElement;
import it.unifi.ing.stlab.wood-elements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.wood-elements.model.actions.WoodElementCreateAction;
import it.unifi.ing.stlab.wood-elements.model.actions.WoodElementDeleteAction;
import it.unifi.ing.stlab.wood-elements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.wood-elements.model.actions.WoodElementModifyAction;
import it.unifi.ing.stlab.wood-elements.model.actions.WoodElementSplitAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

public class WoodElementActionFactory extends AbstractActionFactory<WoodElement,WoodElementAction,User,Time>{

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
