package it.unifi.ing.stlab.observableentities.factory;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityCreateAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityDeleteAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntitySplitAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

public class ObservableEntityActionFactory extends AbstractActionFactory<ObservableEntity,ObservableEntityAction,User,Time>{

	@Override
	protected ObservableEntityAction createAction() {
		return new ObservableEntityCreateAction( UUID.randomUUID().toString() );
	}

	@Override
	protected ObservableEntityAction modifyAction() {
		return new ObservableEntityModifyAction( UUID.randomUUID().toString() );
	}

	@Override
	protected ObservableEntityAction mergeAction() {
		return new ObservableEntityMergeAction( UUID.randomUUID().toString() );
	}

	@Override
	protected ObservableEntityAction splitAction() {
		return new ObservableEntitySplitAction( UUID.randomUUID().toString() );
	}

	@Override
	protected ObservableEntityAction deleteAction() {
		return new ObservableEntityDeleteAction( UUID.randomUUID().toString() );
	}

}
