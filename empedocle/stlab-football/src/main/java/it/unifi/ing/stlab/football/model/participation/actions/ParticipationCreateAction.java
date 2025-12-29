package it.unifi.ing.stlab.football.model.participation.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.woodelements.model.participation.Participation;
import it.unifi.ing.stlab.woodelements.model.participation.actions.ParticipationAction;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityCreateAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "CR" )
public class ParticipationCreateAction
        extends ParticipationAction
        implements CreateAction<Participation, ParticipationAction, User, Time> {

    public ParticipationCreateAction(String uuid) {
        super(uuid);
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected ParticipationCreateAction() {
        super();
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }

    // Delegated methods
    @ManyToOne
    @JoinColumn(name = "target_id")
    public Participation getTarget() {
        return (Participation) getDelegate().getTarget();
    }

    public void setTarget(Participation target) {
        getDelegate().setTarget(target);
    }

    public void assignTarget(Participation newTarget) {
        getDelegate().assignTarget(newTarget);
    }

    @Transient
    public CreateActionImpl<Participation, ParticipationAction, User, Time> getDelegate() {
        return (CreateActionImpl<Participation, ParticipationAction, User, Time>) super.getDelegate();
    }
}
