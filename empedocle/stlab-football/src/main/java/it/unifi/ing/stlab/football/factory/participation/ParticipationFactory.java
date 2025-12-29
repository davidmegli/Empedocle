package it.unifi.ing.stlab.football.factory.participation;

import it.unifi.ing.stlab.football.model.participation.Participation
import it.unifi.ing.stlab.football.model.participation.ParticipationIdentifier;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;

import javax.ejb.Stateless;

import java.util.UUID;

@Stateless
public class ParticipationFactory extends ObservableEntityFactory<Participation, ParticipationIdentifier> {

    @Override
    protected Participation createConcreteEntity() {
        return new Participation();
    }

    @Override
    protected ParticipationIdentifier createConcreteIdentifier() {
        return new ParticipationIdentifier();
    }

    @Override
    public Participation create() {
        Participation participation = super.create();
        return participation;
    }

    @Override
    public ParticipationIdentifier createIdentifier() {
        ParticipationIdentifier identifier = super.createIdentifier();
        return identifier;
    }
}

