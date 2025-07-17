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
import it.unfi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;


@Stateless
public class WoodElementDaoBean extends ObservableEntityDaoBean<WoodElement, WoodElementManger> implements WoodElementDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int count(QueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}
 
	@Override
	@SuppressWarnings("unchecked")
	public List<WoodElement> find(QueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<WoodElement>) query.getResultList();	
	}
	
	@Override
	public WoodElement findById(Long id) {
		return entityManager.find(WoodElement.class, id);
	}
	
	@Override
	public WoodElement fetchById(Long id) {
		if ( id == null ) 
			throw new IllegalArgumentException( "id is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from WoodElement p" +
			" left join fetch p.before b " + 
			" left join fetch p.after a " + 
			" where p.id = :pid", WoodElement.class )
			.setParameter( "pid", id )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (WoodElement)results.get( 0 );
	}	
	
	@Override
	public WoodElement findByIdentifier(String identifier) {
		if ( identifier == null ) 
			throw new IllegalArgumentException( "identifier is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from WoodElement p" +
			" where p.identifier.code = :identifier" +
			" and p.destination is null" )
			.setParameter( "identifier", identifier )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (WoodElement)results.get( 0 );
	}
	
	@Override
	public WoodElement findLastVersionById( Long id ) {
		if( id == null )
			throw new IllegalArgumentException( "id is null" );
		
		List<WoodElement> results = entityManager.createQuery(
					" select p " +
					" from WoodElement p " +
					" join p.before b " +
					" where b.id = :pid " +
					" and p.destination is null", WoodElement.class )
				.setParameter( "pid", id )
				.setMaxResults( 1 )
				.getResultList();
		
		if ( results.size() == 0 )
			return null;
					
		return results.get( 0 );
	}
	
	@Override
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

	@Override
	public List<WoodElement> findByName(String name, String surname) {
		return entityManager.createQuery( " select p "+
									" from WoodElement p " +
									" where p.name = :name " +
									" and p.surname = :surname " +
									" and p.destination is null ", WoodElement.class )
					.setParameter( "name", name )
					.setParameter( "surname", surname )
					.getResultList();
	}
	
	@Override
	public List<WoodElement> findForMerge(String name, String surname, Long excludeId) {
		return entityManager.createQuery( " select p "+
									" from WoodElement p " +
									" where p.name = :name " +
									" and p.surname = :surname " +
									" and p.destination is null " +
								//	" and p.identifier is null " +  // to allow merging even between different woodElement records of the same woodElement in Book
									" and p.id <> :notPid", WoodElement.class )
					.setParameter( "name", name )
					.setParameter( "surname", surname )
					.setParameter( "notPid", excludeId )
					.getResultList();
	}

	/**
	 * Manual Merge of woodElements
	 */
	@Override
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
	public void save( WoodElement target ) {
		entityManager.persist( target );
		flush();
	}
	
	@Override
	public void update( WoodElement target ) {
		entityManager.merge( target );
		flush();
	}

	@Override
	public WoodElementManager getManager(){
		return new WoodElementManager();
	}

	
	private void flush() {
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
	}

	@Override
	public WoodElement findByTaxCode( String taxCode ) {
		if ( taxCode == null ) 
			throw new IllegalArgumentException( "tax code is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from WoodElement p" +
			" where p.taxCode = :taxCode" +
			" and p.destination is null" )
			.setParameter( "taxCode", taxCode )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (WoodElement)results.get( 0 );
	}

	@Override
	public List<WoodElement> findBySuggestion( String suggestion, int limit ) {
		
		TypedQuery<WoodElement> query = entityManager.createQuery(
				" select p from WoodElement p " 
					+ " where CONCAT( p.surname, ' ', p.name, ' - ', p.taxCode ) like :suggestion " 
					+ " and p.destination is null", WoodElement.class );
		
		query.setParameter( "suggestion", '%' + suggestion + '%');
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();	
	}
	
	@Override
	public WoodElement findByUuid( String uuid ) {
		if (uuid == null || uuid.trim().isEmpty())
			throw new IllegalArgumentException("Parametro uuid null");

		List<WoodElement> results = entityManager.createQuery( 
				"select p " 
					+ " from WoodElement p " 
					+ " where p.uuid = :uuid", WoodElement.class )
			.setParameter("uuid", uuid )
			.setMaxResults( 1 )
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return results.get( 0 );
	}
}
