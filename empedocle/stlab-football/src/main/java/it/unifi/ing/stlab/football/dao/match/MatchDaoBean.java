package it.unifi.ing.stlab.football.dao.match;

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

import it.unifi.ing.stlab.football.manager.MatchManager;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.MatchIdentifier;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;

import javax.ejb.Local;

@Stateless
@Local({MatchDao.class,ObservableEntityDao.class})
public class MatchDaoBean extends ObservableEntityDaoBean<Match, MatchManager> implements MatchDao {

    @Override
    protected Class<Match> getEntityClass() {
        return Match.class;
    }
    @Override
    public MatchManager getManager(){
        return new MatchManager();
    }


    public MatchIdentifier findIdentifierByCode(String code) {
        if ( code == null )
            throw new IllegalArgumentException( "code is null" );

        List<MatchIdentifier> results =
                entityManager.createQuery( " select mi " +
                                        " from MatchIdentifier mi " +
                                        " where mi.code = :code ",
                                MatchIdentifier.class )
                        .setParameter( "code", code )
//							.setFlushMode(FlushModeType.COMMIT)
                        .setMaxResults( 1 )
                        .getResultList();

        if ( results.size() == 0 )
            return null;

        return results.get( 0 );
    }

}
