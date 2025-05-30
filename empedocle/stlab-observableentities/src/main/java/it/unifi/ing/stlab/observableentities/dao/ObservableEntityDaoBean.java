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
	public ObservableEntity findByUuid(String uuid ) {
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



	

}
