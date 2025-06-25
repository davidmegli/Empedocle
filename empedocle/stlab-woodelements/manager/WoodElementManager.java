package it.unifi.ing.stlab.woodelements.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.manager.AbstractTracedEntityManager;
import it.unifi.ing.stlab.woodelements.factory.WoodElementActionFactory;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

public class WoodElementManager
	extends AbstractTracedEntityManager<WoodElement,WoodElementAction,User,Time>{

	@Override
	protected AbstractActionFactory<WoodElement, WoodElementAction, User, Time> getActionFactory() {
		return new WoodElementActionFactory();
	}

	public WoodElement createWoodElement( User author, Time time ) {
		return init( WoodElementFactory.createWoodElement(), author, time );
	}
	
	public WoodElement merge( User author, Time time, WoodElement master, WoodElement slave ) {
		return ((WoodElementMergeAction) getActionFactory()
				.mergeAction(author, time, master, slave, master.copy()))
				.getTarget();
	}	

}
