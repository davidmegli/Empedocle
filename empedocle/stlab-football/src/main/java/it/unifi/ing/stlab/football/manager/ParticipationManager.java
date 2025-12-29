package it.unifi.ing.stlab.football.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.factory.participation.ParticipationActionFactory;
import it.unifi.ing.stlab.football.factory.participation.ParticipationFactory;
import it.unifi.ing.stlab.football.model.participation.Participation;
import it.unifi.ing.stlab.football.model.participation.ParticipationIdentifier;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.model.participation.actions.ParticipationAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import javax.ejb.Stateless;

@Stateless
public class ParticipationManager extends ObservableEntityManager<
        Participation,
        ParticipationAction,
        ParticipationFactory,
        ParticipationIdentifier> {

    public ParticipationManager() {
        this.factory = new ParticipationFactory();
        this.actionFactory = new ParticipationActionFactory();
    }

    @Override
    protected AbstractActionFactory<Participation, ParticipationAction, User, Time> getActionFactory() {
        return new ParticipationActionFactory();
    }
    @Override
    public Participation merge( User author, Time time, Participation master, Participation slave ) {
        Participation copy = (master != null) ? master.copy() : (slave != null ? slave.copy() : null);
        return ((ParticipationAction) this.actionFactory
                .mergeAction(author, time, master, slave, copy))
                .getTarget();
    }

}
