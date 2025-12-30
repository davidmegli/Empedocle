package it.unifi.ing.stlab.football.dao.participation;

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
import it.unifi.ing.stlab.football.manager.ParticipationManager;
import it.unifi.ing.stlab.football.model.participation.Participation;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.football.model.participation.ParticipationIdentifier;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;

import javax.ejb.Local;

@Stateless
@Local({ParticipationDao.class})
public class ParticipationDaoBean extends ObservableEntityDaoBean<Participation, ParticipationManager> implements ParticipationDao {

    @Override
    protected Class<Participation> getEntityClass() {
        return Participation.class;
    }
    @Override
    public ParticipationManager getManager(){
        return new ParticipationManager();
    }


    public ParticipationIdentifier findIdentifierByCode(String code) {
        if ( code == null )
            throw new IllegalArgumentException( "code is null" );

        List<ParticipationIdentifier> results =
                entityManager.createQuery( " select pi " +
                                        " from ParticipationIdentifier pi " +
                                        " where pi.code = :code ",
                                ParticipationIdentifier.class )
                        .setParameter( "code", code )
//							.setFlushMode(FlushModeType.COMMIT)
                        .setMaxResults( 1 )
                        .getResultList();

        if ( results.size() == 0 )
            return null;

        return results.get( 0 );
    }
}
