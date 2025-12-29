package it.unifi.ing.stlab.football.factory.roster;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.football.model.roster.actions.RosterAction;
import it.unifi.ing.stlab.football.model.roster.actions.RosterCreateAction;
import it.unifi.ing.stlab.football.model.roster.actions.RosterDeleteAction;
import it.unifi.ing.stlab.football.model.roster.actions.RosterModifyAction;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityActionFactory;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

import javax.ejb.Stateless;

@Stateless
public class RosterActionFactory
		extends ObservableEntityActionFactory<
		Roster,
		RosterAction>{


	public RosterAction createAction() {
		return new RosterCreateAction( UUID.randomUUID().toString() );
	}


	public RosterAction modifyAction() {
		RosterModifyAction rostermodifyaction = new RosterModifyAction( UUID.randomUUID().toString() );
		return rostermodifyaction;
	}

	public RosterAction deleteAction() {
		return new RosterDeleteAction( UUID.randomUUID().toString() );
	}

}
