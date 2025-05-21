package it.unifi.ing.stlab.observableentities.dao;

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
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

@Stateless
public class ObservableEntityDaoBean implements ObservableEntityDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int count(QueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}
 
	@Override
	@SuppressWarnings("unchecked")
	public List<ObservableEntity> find(QueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<ObservableEntity>) query.getResultList();	
	}
	
	@Override
	public ObservableEntity findById(Long id) {
		return entityManager.find(ObservableEntity.class, id);
	}
	
	@Override
	public ObservableEntity fetchById(Long id) {
		if ( id == null ) 
			throw new IllegalArgumentException( "id is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from ObservableEntity p" +
			" left join fetch p.before b " + 
			" left join fetch p.after a " + 
			" where p.id = :pid", ObservableEntity.class )
			.setParameter( "pid", id )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (ObservableEntity)results.get( 0 );
	}	
	
	@Override
	public ObservableEntity findByIdentifier(String identifier) {
		if ( identifier == null ) 
			throw new IllegalArgumentException( "identifier is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from ObservableEntity p" +
			" where p.identifier.code = :identifier" +
			" and p.destination is null" )
			.setParameter( "identifier", identifier )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (ObservableEntity)results.get( 0 );
	}
	
	@Override
	public ObservableEntity findLastVersionById( Long id ) {
		if( id == null )
			throw new IllegalArgumentException( "id is null" );
		
		List<ObservableEntity> results = entityManager.createQuery(
					" select p " +
					" from ObservableEntity p " +
					" join p.before b " +
					" where b.id = :pid " +
					" and p.destination is null", ObservableEntity.class )
				.setParameter( "pid", id )
				.setMaxResults( 1 )
				.getResultList();
		
		if ( results.size() == 0 )
			return null;
					
		return results.get( 0 );
	}
	
	@Override
	public ObservableEntityIdentifier findIdentifierByCode(String code) {
		if ( code == null ) 
			throw new IllegalArgumentException( "code is null" );
		
		List<ObservableEntityIdentifier> results =
				entityManager.createQuery( " select pi " +
									" from ObservableEntityIdentifier pi " +
									" where pi.code = :code ", 
									ObservableEntityIdentifier.class )
							.setParameter( "code", code )
//							.setFlushMode(FlushModeType.COMMIT)
							.setMaxResults( 1 )
							.getResultList();
		
		if ( results.size() == 0 )
			return null;
			
		return results.get( 0 );
	}

	@Override
	public List<ObservableEntity> findByName(String name, String surname) {
		return entityManager.createQuery( " select p "+
									" from ObservableEntity p " +
									" where p.name = :name " +
									" and p.surname = :surname " +
									" and p.destination is null ", ObservableEntity.class )
					.setParameter( "name", name )
					.setParameter( "surname", surname )
					.getResultList();
	}
	
	@Override
	public List<ObservableEntity> findForMerge(String name, String surname, Long excludeId) {
		return entityManager.createQuery( " select p "+
									" from ObservableEntity p " +
									" where p.name = :name " +
									" and p.surname = :surname " +
									" and p.destination is null " +
								//	" and p.identifier is null " +  // to allow merging even between different observableEntity records of the same observableEntity in Book
									" and p.id <> :notPid", ObservableEntity.class )
					.setParameter( "name", name )
					.setParameter( "surname", surname )
					.setParameter( "notPid", excludeId )
					.getResultList();
	}

	/**
	 * Manual Merge of observableEntities
	 */
	@Override
	public ObservableEntity mergeObservableEntitys( Long observableEntityId, Long otherId, User author ) {
		ObservableEntity observableEntity = findById( observableEntityId );
		ObservableEntity other = findById( otherId );

		ObservableEntity master;
		ObservableEntity slave;

		ObservableEntityIdentifier observableEntityIdentifier = observableEntity.getIdentifier();
		ObservableEntityIdentifier otherIdentifier = other.getIdentifier();
		if ( observableEntityIdentifier != null && otherIdentifier != null ) {
			// merge between Book observableEntity records
			// master is the most recent record
			if ( observableEntity.getOrigin().getTime().compareTo( other.getOrigin().getTime() ) >= 0 ) {
				master = observableEntity;
				slave = other;
			} else {
				master = other;
				slave = observableEntity;
			}
		} else {
			if ( observableEntityIdentifier != null || otherIdentifier == null ) {
				// there are two possible cases:
				// - observableEntity is the Book record and is the master
				// - or both records are without an identifier and
				// the current record (i.e., observableEntity) is taken as the master
				master = observableEntity;
				slave = other;
			} else {
				// other is the Book record and is the master
				master = other;
				slave = observableEntity;
			}
		}

		ObservableEntityManager manager = new ObservableEntityManager();
		Time time = new Time( new Date() );
		ObservableEntity result = manager.merge( author, time, master, slave );
		
		entityManager.persist( result );
		
		return result;
	}
	
	@Override
	public void save( ObservableEntity target ) {
		entityManager.persist( target );
		flush();
	}
	
	@Override
	public void update( ObservableEntity target ) {
		entityManager.merge( target );
		flush();
	}
	
	@Override
	public void deleteById( Long id, User author ) {
		
		if( id != null ) {
			ObservableEntityManager manager = new ObservableEntityManager();
			ObservableEntity result = manager.delete( author, new Time( new Date() ), findById( id ));
			entityManager.persist( result );
		}
	}	
	
	private void flush() {
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
	}

	@Override
	public ObservableEntity findByTaxCode( String taxCode ) {
		if ( taxCode == null ) 
			throw new IllegalArgumentException( "tax code is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from ObservableEntity p" +
			" where p.taxCode = :taxCode" +
			" and p.destination is null" )
			.setParameter( "taxCode", taxCode )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (ObservableEntity)results.get( 0 );
	}

	@Override
	public List<ObservableEntity> findBySuggestion( String suggestion, int limit ) {
		
		TypedQuery<ObservableEntity> query = entityManager.createQuery(
				" select p from ObservableEntity p " 
					+ " where CONCAT( p.surname, ' ', p.name, ' - ', p.taxCode ) like :suggestion " 
					+ " and p.destination is null", ObservableEntity.class );
		
		query.setParameter( "suggestion", '%' + suggestion + '%');
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();	
	}
	
	@Override
	public ObservableEntity findByUuid( String uuid ) {
		if (uuid == null || uuid.trim().isEmpty())
			throw new IllegalArgumentException("Parametro uuid null");

		List<ObservableEntity> results = entityManager.createQuery( 
				"select p " 
					+ " from ObservableEntity p " 
					+ " where p.uuid = :uuid", ObservableEntity.class )
			.setParameter("uuid", uuid )
			.setMaxResults( 1 )
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return results.get( 0 );
	}
}
