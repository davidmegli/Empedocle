package it.unifi.ing.stlab.football.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.factory.match.MatchActionFactory;
import it.unifi.ing.stlab.football.factory.match.MatchFactory;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.MatchIdentifier;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.model.match.actions.MatchAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import javax.ejb.Stateless;

@Stateless
public class MatchManager extends ObservableEntityManager<
        Match,
        MatchAction,
        MatchFactory,
        MatchIdentifier> {

    public MatchManager() {
        this.factory = new MatchFactory();
        this.actionFactory = new MatchActionFactory();
    }

    @Override
    protected AbstractActionFactory<Match, MatchAction, User, Time> getActionFactory() {
        return new MatchActionFactory();
    }

}
