package it.unifi.ing.stlab.football.factory.match;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.actions.MatchAction;
import it.unifi.ing.stlab.football.model.match.actions.MatchCreateAction;
import it.unifi.ing.stlab.football.model.match.actions.MatchDeleteAction;
import it.unifi.ing.stlab.football.model.match.actions.MatchModifyAction;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityActionFactory;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

import javax.ejb.Stateless;

@Stateless
public class MatchActionFactory
        extends ObservableEntityActionFactory<
        Match,
        MatchAction>{


    public MatchAction createAction() {
        return new MatchCreateAction( UUID.randomUUID().toString() );
    }


    public MatchAction modifyAction() {
        return new MatchModifyAction( UUID.randomUUID().toString() );
    }

    public MatchAction deleteAction() {
        return new MatchDeleteAction( UUID.randomUUID().toString() );
    }

    public MatchAction mergeAction() {
        return null;
    }

    public MatchAction splitAction() {
        return null;
    }

}
