package it.unifi.ing.stlab.football.model.player.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.player.actions.PlayerAction;
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
public class PlayerCreateAction
        extends PlayerAction
        implements CreateAction<Player, PlayerAction, User, Time> {

    public PlayerCreateAction(String uuid) {
        super(uuid);
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected PlayerCreateAction() {
        super();
        setDelegate(new CreateActionImpl<>());
        getDelegate().setDelegator(this);
    }

    // Delegated methods
    @ManyToOne
    @JoinColumn(name = "target_id")
    public Player getTarget() {
        return (Player) getDelegate().getTarget();
    }

    public void setTarget(Player target) {
        getDelegate().setTarget(target);
    }

    public void assignTarget(Player newTarget) {
        getDelegate().assignTarget(newTarget);
    }

    @Transient
    public CreateActionImpl<Player, PlayerAction, User, Time> getDelegate() {
        return (CreateActionImpl<Player, PlayerAction, User, Time>) super.getDelegate();
    }
}
