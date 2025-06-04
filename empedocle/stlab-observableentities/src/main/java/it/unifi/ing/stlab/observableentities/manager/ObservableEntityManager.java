package it.unifi.ing.stlab.observableentities.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.manager.AbstractTracedEntityManager;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityActionFactory;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

public abstract class ObservableEntityManager
	<T extends ObservableEntity, A extends ObservableEntityAction<T,A>>
	extends AbstractTracedEntityManager<T,A,User,Time>{

	@Override
	protected AbstractActionFactory<T, A, User, Time> getActionFactory() {
		return new ObservableEntityActionFactory();
	}

	public T createObservableEntity( User author, Time time ) {
		return init( ObservableEntityFactory.createObservableEntity(), author, time );
	}
	
	public T merge( User author, Time time, T master, T slave ) {
		return ((ObservableEntityMergeAction) getActionFactory()
				.mergeAction(author, time, master, slave, master.copy()))
				.getTarget();
	}	

}
