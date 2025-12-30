package it.unifi.ing.stlab.football.dao.player;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;

import it.unifi.ing.stlab.football.manager.PlayerManager;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.player.PlayerIdentifier;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;

import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.ejb.Local;

@Stateless
@Local({PlayerDao.class})
public class PlayerDaoBean extends ObservableEntityDaoBean<Player, PlayerManager> implements PlayerDao {

    @Override
    protected Class<Player> getEntityClass() {
        return Player.class;
    }
    @Override
    public PlayerManager getManager(){
        return new PlayerManager();
    }


    public PlayerIdentifier findIdentifierByCode(String code) {
        if ( code == null )
            throw new IllegalArgumentException( "code is null" );

        List<PlayerIdentifier> results =
                entityManager.createQuery( " select pi " +
                                        " from PlayerIdentifier pi " +
                                        " where pi.code = :code ",
                                PlayerIdentifier.class )
                        .setParameter( "code", code )
//							.setFlushMode(FlushModeType.COMMIT)
                        .setMaxResults( 1 )
                        .getResultList();

        if ( results.size() == 0 )
            return null;

        return results.get( 0 );
    }

}
