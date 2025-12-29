package it.unifi.ing.stlab.football.model.player.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.player.actions.PlayerAction;
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
public class PlayerDeleteAction
        extends PlayerAction
        implements DeleteAction<Player, PlayerAction, User, Time> {

    public PlayerDeleteAction(String uuid) {
        super(uuid);
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected PlayerDeleteAction() {
        super();
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    @Transient
    public DeleteActionImpl<Player, PlayerAction, User, Time> getDelegate() {
        return (DeleteActionImpl<Player, PlayerAction, User, Time>)super.getDelegate();
    }

    @ManyToOne
    @JoinColumn( name = "source_id" )
    public Player getSource() {
        return (Player) getDelegate().getSource();
    }
    public void setSource(Player source) {
        getDelegate().setSource(source);
    }
    public void assignSource(Player newSource) {
        getDelegate().assignSource(newSource);
    }

}
