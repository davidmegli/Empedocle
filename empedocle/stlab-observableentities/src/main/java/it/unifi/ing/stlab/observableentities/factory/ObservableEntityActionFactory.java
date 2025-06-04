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

public abstract class ObservableEntityActionFactory
		<T extends ObservableEntity, A extends ObservableEntityAction<T,A>>
		extends AbstractActionFactory<T,A,User,Time>{

	@Override
	protected A createAction() {
		return new ObservableEntityCreateAction( UUID.randomUUID().toString() );
	}

	@Override
	protected A modifyAction() {
		return new ObservableEntityModifyAction( UUID.randomUUID().toString() );
	}

	@Override
	protected A mergeAction() {
		return new ObservableEntityMergeAction( UUID.randomUUID().toString() );
	}

	@Override
	protected A splitAction() {
		return new ObservableEntitySplitAction( UUID.randomUUID().toString() );
	}

	@Override
	protected A deleteAction() {
		return new ObservableEntityDeleteAction( UUID.randomUUID().toString() );
	}

}
