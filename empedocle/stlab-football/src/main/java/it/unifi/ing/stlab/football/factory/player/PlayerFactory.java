package it.unifi.ing.stlab.football.factory.player;

import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.player.PlayerIdentifier;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;

import javax.ejb.Stateless;

import java.util.UUID;

@Stateless
public class PlayerFactory extends ObservableEntityFactory<Player, PlayerIdentifier> {

    @Override
    protected Player createConcreteEntity() {
        return new Player();
    }

    @Override
    protected PlayerIdentifier createConcreteIdentifier() {
        return new PlayerIdentifier();
    }

    @Override
    public Player create() {
        Player player = super.create();
        return player;
    }

    @Override
    public PlayerIdentifier createIdentifier() {
        PlayerIdentifier identifier = super.createIdentifier();
        return identifier;
    }
}

