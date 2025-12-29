package it.unifi.ing.stlab.football.model.roster.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.woodelements.model.roster.Roster;
import it.unifi.ing.stlab.woodelements.model.roster.actions.RosterAction;
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
public class RosterCreateAction
        extends RosterAction
        implements CreateAction<Roster, RosterAction, User, Time> {

    public RosterCreateAction(String uuid) {
        super(uuid);
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected RosterCreateAction() {
        super();
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }

    // Delegated methods
    @ManyToOne
    @JoinColumn(name = "target_id")
    public Roster getTarget() {
        return (Roster) getDelegate().getTarget();
    }

    public void setTarget(Roster target) {
        getDelegate().setTarget(target);
    }

    public void assignTarget(Roster newTarget) {
        getDelegate().assignTarget(newTarget);
    }

    @Transient
    public CreateActionImpl<Roster, RosterAction, User, Time> getDelegate() {
        return (CreateActionImpl<Roster, RosterAction, User, Time>) super.getDelegate();
    }
}
