package it.unifi.ing.stlab.observableentities.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.manager.AbstractTracedEntityManager;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityActionFactory;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

public abstract class ObservableEntityManager
	<		T extends ObservableEntity<T, A, ?, ?>,
			A extends ObservableEntityAction<T,A, User, Time>,
			F extends ObservableEntityFactory<T,I>,
			I extends ObservableEntityIdentifier>
	extends AbstractTracedEntityManager<T,A,User,Time>{

	protected F factory;
	protected AbstractActionFactory<T, A, User, Time> actionFactory;

//	public WoodElementManager() {
//		this.woodElementFactory = new WoodElementFactory();
//		this.actionFactory = new WoodElementActionFactory();
//	}

	@Override
	protected AbstractActionFactory<T, A, User, Time> getActionFactory() {
		return actionFactory;
	}

	public F getFactory() {
		return factory;
	}

	public void setFactory(F factory) {
		this.factory = factory;
	}

	public T createObservableEntity( User author, Time time ) {
		return init( factory.create(), author, time );
	}

	public T merge( User author, Time time, T master, T slave ) {
		T copy = (master != null) ? master.copy() : (slave != null ? slave.copy() : null);
		return (T) ((ObservableEntityMergeAction) getActionFactory()
				.mergeAction(author, time, master, slave, copy))
				.getTarget();
	}


}
