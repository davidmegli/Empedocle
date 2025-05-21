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

public class ObservableEntityManager
	extends AbstractTracedEntityManager<ObservableEntity,ObservableEntityAction,User,Time>{

	@Override
	protected AbstractActionFactory<ObservableEntity, ObservableEntityAction, User, Time> getActionFactory() {
		return new ObservableEntityActionFactory();
	}

	public ObservableEntity createObservableEntity( User author, Time time ) {
		return init( ObservableEntityFactory.createObservableEntity(), author, time );
	}
	
	public ObservableEntity merge( User author, Time time, ObservableEntity master, ObservableEntity slave ) {
		return ((ObservableEntityMergeAction) getActionFactory()
				.mergeAction(author, time, master, slave, master.copy()))
				.getTarget();
	}	

}
