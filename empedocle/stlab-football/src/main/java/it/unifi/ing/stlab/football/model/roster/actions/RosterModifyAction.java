package it.unifi.ing.stlab.football.model.roster.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.football.model.roster.actions.RosterAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.*;


@Entity
@DiscriminatorValue( "MD" )
public class RosterModifyAction
        extends RosterAction
        implements ModifyAction<Roster, RosterAction, User, Time> {

    public RosterModifyAction(String uuid) {
        super(uuid);
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }
    public RosterModifyAction() {
        super();
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }

    @Transient
    public ModifyActionImpl<Roster,RosterAction,User,Time> getDelegate() {
        return (ModifyActionImpl<Roster,RosterAction,User,Time>)super.getDelegate();
    }

    @ManyToOne
    @JoinColumn( name = "source_id" )
    public Roster getSource() {
        return (Roster) getDelegate().getSource();
    }
    public void setSource(Roster source) {
        getDelegate().setSource(source);
    }
    public void assignSource(Roster newSource) {
        getDelegate().assignSource(newSource);
    }


    @OneToOne(mappedBy = "origin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Roster getTarget() {
        return (Roster) getDelegate().getTarget();
    }
    public void setTarget(Roster target) {
        getDelegate().setTarget(target);
    }
    public void assignTarget(Roster newTarget) {
        getDelegate().assignTarget(newTarget);
    }

}
