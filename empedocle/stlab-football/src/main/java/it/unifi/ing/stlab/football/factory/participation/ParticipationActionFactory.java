package it.unifi.ing.stlab.football.factory.participation;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.model.participation.Participation;
import it.unifi.ing.stlab.football.model.participation.actions.ParticipationAction;
import it.unifi.ing.stlab.football.model.participation.actions.ParticipationCreateAction;
import it.unifi.ing.stlab.football.model.participation.actions.ParticipationDeleteAction;
import it.unifi.ing.stlab.football.model.participation.actions.ParticipationModifyAction;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityActionFactory;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

import javax.ejb.Stateless;

@Stateless
public class ParticipationActionFactory
        extends ObservableEntityActionFactory<
        Participation,
        ParticipationAction>{


    public ParticipationAction createAction() {
        return new ParticipationCreateAction( UUID.randomUUID().toString() );
    }


    public ParticipationAction modifyAction() {
        ParticipationModifyAction participationmodifyaction = new ParticipationModifyAction( UUID.randomUUID().toString() );
        return participationmodifyaction;
    }

    public ParticipationAction deleteAction() {
        return new ParticipationDeleteAction( UUID.randomUUID().toString() );
    }

}
