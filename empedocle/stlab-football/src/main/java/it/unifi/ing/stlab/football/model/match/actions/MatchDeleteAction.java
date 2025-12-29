package it.unifi.ing.stlab.football.model.match.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.actions.MatchAction;
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
public class MatchDeleteAction
        extends MatchAction
        implements DeleteAction<Match, MatchAction, User, Time> {

    public MatchDeleteAction(String uuid) {
        super(uuid);
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected MatchDeleteAction() {
        super();
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    @Transient
    public DeleteActionImpl<Match, MatchAction, User, Time> getDelegate() {
        return (DeleteActionImpl<Match, MatchAction, User, Time>)super.getDelegate();
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

}
