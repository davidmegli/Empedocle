package it.unifi.ing.stlab.football.dao.roster;

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
import it.unifi.ing.stlab.football.manager.RosterManager;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.football.model.roster.RosterIdentifier;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;

import javax.ejb.Local;

@Stateless
@Local({RosterDao.class})
public class RosterDaoBean extends ObservableEntityDaoBean<Roster, RosterManager> implements RosterDao {

	@Override
	protected Class<Roster> getEntityClass() {
		return Roster.class;
	}
	@Override
	public RosterManager getManager(){
		return new RosterManager();
	}


	public RosterIdentifier findIdentifierByCode(String code) {
		if ( code == null )
			throw new IllegalArgumentException( "code is null" );

		List<RosterIdentifier> results =
				entityManager.createQuery( " select pi " +
										" from RosterIdentifier pi " +
										" where pi.code = :code ",
								RosterIdentifier.class )
						.setParameter( "code", code )
//							.setFlushMode(FlushModeType.COMMIT)
						.setMaxResults( 1 )
						.getResultList();

		if ( results.size() == 0 )
			return null;

		return results.get( 0 );
	}
}
