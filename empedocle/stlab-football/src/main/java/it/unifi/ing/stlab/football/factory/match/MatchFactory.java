package it.unifi.ing.stlab.football.factory.match;

import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.MatchIdentifier;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;

import javax.ejb.Stateless;

import java.util.UUID;

@Stateless
public class MatchFactory extends ObservableEntityFactory<Match, MatchIdentifier> {

    @Override
    protected Match createConcreteEntity() {
        return new Match();
    }

    @Override
    protected MatchIdentifier createConcreteIdentifier() {
        return new MatchIdentifier();
    }

    @Override
    public Match create() {
        Match match = super.create();
        return match;
    }

    @Override
    public MatchIdentifier createIdentifier() {
        MatchIdentifier identifier = super.createIdentifier();
        return identifier;
    }
}

