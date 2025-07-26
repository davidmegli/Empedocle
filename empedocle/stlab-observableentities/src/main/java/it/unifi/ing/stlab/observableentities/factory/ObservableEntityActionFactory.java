package it.unifi.ing.stlab.observableentities.factory;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityCreateAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityDeleteAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntitySplitAction;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

public abstract class ObservableEntityActionFactory
		<T extends ObservableEntity<T, A, ?, ?>, A extends ObservableEntityAction<T,A,User,Time>>
		extends AbstractActionFactory<T,A,User,Time>{

	@Override
	public abstract A createAction();

	@Override
	public abstract A modifyAction();

	@Override
	public abstract A mergeAction();

	@Override
	public abstract A splitAction();

	@Override
	public abstract A deleteAction();

}
