package it.unifi.ing.stlab.football.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.factory.roster.RosterActionFactory;
import it.unifi.ing.stlab.football.factory.roster.RosterFactory;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.football.model.roster.RosterIdentifier;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.model.roster.actions.RosterAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import javax.ejb.Stateless;

@Stateless
public class RosterManager extends ObservableEntityManager<
        Roster,
        RosterAction,
        RosterFactory,
        RosterIdentifier> {

    public RosterManager() {
        this.factory = new RosterFactory();
        this.actionFactory = new RosterActionFactory();
    }

    @Override
    protected AbstractActionFactory<Roster, RosterAction, User, Time> getActionFactory() {
        return new RosterActionFactory();
    }

}
