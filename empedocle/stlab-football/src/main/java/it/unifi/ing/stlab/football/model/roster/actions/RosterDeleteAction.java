package it.unifi.ing.stlab.football.model.roster.actions;s

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.football.model.roster.actions.RosterAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityDeleteAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "DL" )
public class RosterDeleteAction
        extends RosterAction
        implements DeleteAction<Roster, RosterAction, User, Time> {

    public RosterDeleteAction(String uuid) {
        super(uuid);
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected RosterDeleteAction() {
        super();
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    @Transient
    public DeleteActionImpl<Roster, RosterAction, User, Time> getDelegate() {
        return (DeleteActionImpl<Roster, RosterAction, User, Time>)super.getDelegate();
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

}
