package it.unifi.ing.stlab.football.model.player.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.player.actions.PlayerAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.*;


@Entity
@DiscriminatorValue( "MD" )
public class PlayerModifyAction
        extends PlayerAction
        implements ModifyAction<Player, PlayerAction, User, Time> {

    public PlayerModifyAction(String uuid) {
        super(uuid);
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }
    public PlayerModifyAction() {
        super();
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }

    @Transient
    public ModifyActionImpl<Player,PlayerAction,User,Time> getDelegate() {
        return (ModifyActionImpl<Player,PlayerAction,User,Time>)super.getDelegate();
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


    @OneToOne(mappedBy = "origin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Player getTarget() {
        return (Player) getDelegate().getTarget();
    }
    public void setTarget(Player target) {
        getDelegate().setTarget(target);
    }
    public void assignTarget(Player newTarget) {
        getDelegate().assignTarget(newTarget);
    }

}
