package it.unifi.ing.stlab.football.model.match.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.actions.MatchAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.*;


@Entity
@DiscriminatorValue( "MD" )
public class MatchModifyAction
        extends MatchAction
        implements ModifyAction<Match, MatchAction, User, Time> {

    public MatchModifyAction(String uuid) {
        super(uuid);
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }
    public MatchModifyAction() {
        super();
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }

    @Transient
    public ModifyActionImpl<Match,MatchAction,User,Time> getDelegate() {
        return (ModifyActionImpl<Match,MatchAction,User,Time>)super.getDelegate();
    }

    @ManyToOne
    @JoinColumn( name = "source_id" )
    public Match getSource() {
        return (Match) getDelegate().getSource();
    }
    public void setSource(Match source) {
        getDelegate().setSource(source);
    }
    public void assignSource(Match newSource) {
        getDelegate().assignSource(newSource);
    }


    @OneToOne(mappedBy = "origin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Match getTarget() {
        return (Match) getDelegate().getTarget();
    }
    public void setTarget(Match target) {
        getDelegate().setTarget(target);
    }
    public void assignTarget(Match newTarget) {
        getDelegate().assignTarget(newTarget);
    }

}
