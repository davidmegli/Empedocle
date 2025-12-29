package it.unifi.ing.stlab.football.model.match.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.actions.MatchAction;
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
public class MatchCreateAction
        extends MatchAction
        implements CreateAction<Match, MatchAction, User, Time> {

    public MatchCreateAction(String uuid) {
        super(uuid);
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected MatchCreateAction() {
        super();
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }

    // Delegated methods
    @ManyToOne
    @JoinColumn(name = "target_id")
    public Match getTarget() {
        return (Match) getDelegate().getTarget();
    }

    public void setTarget(Match target) {
        getDelegate().setTarget(target);
    }

    public void assignTarget(Match newTarget) {
        getDelegate().assignTarget(newTarget);
    }

    @Transient
    public CreateActionImpl<Match, MatchAction, User, Time> getDelegate() {
        return (CreateActionImpl<Match, MatchAction, User, Time>) super.getDelegate();
    }
}
