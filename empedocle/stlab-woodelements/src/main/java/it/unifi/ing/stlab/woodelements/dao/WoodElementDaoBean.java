package it.unifi.ing.stlab.woodelements.dao;

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
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;

import javax.ejb.Local;

@Stateless
@Local(ObservableEntityDao.class)
public class WoodElementDaoBean extends ObservableEntityDaoBean<WoodElement, WoodElementManager> {

	@Override
	protected Class<WoodElement> getEntityClass() {
		return WoodElement.class;
	}


	public WoodElementIdentifier findIdentifierByCode(String code) {
		if ( code == null ) 
			throw new IllegalArgumentException( "code is null" );
		
		List<WoodElementIdentifier> results =
				entityManager.createQuery( " select pi " +
									" from WoodElementIdentifier pi " +
									" where pi.code = :code ", 
									WoodElementIdentifier.class )
							.setParameter( "code", code )
//							.setFlushMode(FlushModeType.COMMIT)
							.setMaxResults( 1 )
							.getResultList();
		
		if ( results.size() == 0 )
			return null;
			
		return results.get( 0 );
	}

	/**
	 * Manual Merge of woodElements
	 */
	public WoodElement mergeWoodElements( Long woodElementId, Long otherId, User author ) {
		WoodElement woodElement = findById( woodElementId );
		WoodElement other = findById( otherId );

		WoodElement master;
		WoodElement slave;

		WoodElementIdentifier woodElementIdentifier = woodElement.getIdentifier();
		WoodElementIdentifier otherIdentifier = other.getIdentifier();
		if ( woodElementIdentifier != null && otherIdentifier != null ) {
			// merge between Book woodElement records
			// master is the most recent record
			if ( woodElement.getOrigin().getTime().compareTo( other.getOrigin().getTime() ) >= 0 ) {
				master = woodElement;
				slave = other;
			} else {
				master = other;
				slave = woodElement;
			}
		} else {
			if ( woodElementIdentifier != null || otherIdentifier == null ) {
				// there are two possible cases:
				// - woodElement is the Book record and is the master
				// - or both records are without an identifier and
				// the current record (i.e., woodElement) is taken as the master
				master = woodElement;
				slave = other;
			} else {
				// other is the Book record and is the master
				master = other;
				slave = woodElement;
			}
		}

		WoodElementManager manager = new WoodElementManager();
		Time time = new Time( new Date() );
		WoodElement result = manager.merge( author, time, master, slave );
		
		entityManager.persist( result );
		
		return result;
	}

	@Override
	public WoodElementManager getManager(){
		return new WoodElementManager();
	}
}
