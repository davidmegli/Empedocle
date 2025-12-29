package it.unifi.ing.stlab.football.dao.player;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.manager.PlayerManager;

import javax.ejb.Local;

@Local
public interface PlayerDao extends ObservableEntityDao<Player, PlayerManager>{
    PlayerIdentifier findIdentifierByCode(String code);
}
