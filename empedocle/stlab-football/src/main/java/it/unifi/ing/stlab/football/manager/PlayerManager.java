package it.unifi.ing.stlab.football.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.football.factory.player.PlayerActionFactory;
import it.unifi.ing.stlab.football.factory.player.PlayerFactory;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.player.PlayerIdentifier;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.model.player.actions.PlayerAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import javax.ejb.Stateless;

@Stateless
public class PlayerManager extends ObservableEntityManager<
        Player,
        PlayerAction,
        PlayerFactory,
        PlayerIdentifier> {

    public PlayerManager() {
        this.factory = new PlayerFactory();
        this.actionFactory = new PlayerActionFactory();
    }

    @Override
    protected AbstractActionFactory<Player, PlayerAction, User, Time> getActionFactory() {
        return new PlayerActionFactory();
    }

}
