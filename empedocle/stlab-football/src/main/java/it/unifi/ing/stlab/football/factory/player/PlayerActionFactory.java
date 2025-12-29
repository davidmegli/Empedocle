package it.unifi.ing.stlab.football.factory.player;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.player.actions.PlayerAction;
import it.unifi.ing.stlab.football.model.player.actions.PlayerCreateAction;
import it.unifi.ing.stlab.football.model.player.actions.PlayerDeleteAction;
import it.unifi.ing.stlab.football.model.player.actions.PlayerModifyAction;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityActionFactory;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

import javax.ejb.Stateless;

@Stateless
public class PlayerActionFactory
        extends ObservableEntityActionFactory<
        Player,
        PlayerAction>{


    public PlayerAction createAction() {
        return new PlayerCreateAction( UUID.randomUUID().toString() );
    }

    public PlayerAction modifyAction() {
        return new PlayerModifyAction( UUID.randomUUID().toString() );
    }

    public PlayerAction deleteAction() {
        return new PlayerDeleteAction( UUID.randomUUID().toString() );
    }

    public PlayerAction mergeAction() {
        return null;
    }
    
    public PlayerAction splitAction() {
        return null;
    }

}
