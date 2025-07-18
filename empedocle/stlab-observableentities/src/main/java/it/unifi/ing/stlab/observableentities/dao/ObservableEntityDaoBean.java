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
public abstract class ObservableEntityDaoBean<T extends ObservableEntity<T, ?, ?, ?>, M extends ObservableEntityManager<T, ?,?,?>>
		implements ObservableEntityDao<T, M> {

	@PersistenceContext
	protected EntityManager entityManager;

	// Metodo astratto per ottenere la classe dell'entità concreta
	protected abstract Class<T> getEntityClass();

	// Metodo helper per ottenere il nome dell'entità per le query JPQL
	protected String getEntityName() {
		return getEntityClass().getSimpleName();
	}
	
	@Override
	public int count(QueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<T> find(QueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<T>) query.getResultList();
	}
	
	@Override
	public T findById(Long id) {
		return entityManager.find(getEntityClass(), id);
	}

	@Override
	public T findByUuid(String uuid ) {
		if (uuid == null || uuid.trim().isEmpty())
			throw new IllegalArgumentException("Parametro uuid null");

		List<T> results = entityManager.createQuery(
						"select p "
								+ " from "+getEntityName()+" p "
								+ " where p.uuid = :uuid", getEntityClass() )
				.setParameter("uuid", uuid )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public T findByIdentifier(String identifier) {
		if ( identifier == null )
			throw new IllegalArgumentException( "identifier is null" );

		List<?> results = entityManager.createQuery(
						"select p" +
								" from "+getEntityName()+" p" +
								" where p.identifier.code = :identifier" +
								" and p.destination is null" )
				.setParameter( "identifier", identifier )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.size() == 0 )
			return null;

		return (T)results.get( 0 );
	}

	@Override
	public T findLastVersionById( Long id ) {
		if( id == null )
			throw new IllegalArgumentException( "id is null" );

		List<T> results = entityManager.createQuery(
						" select p " +
								" from "+getEntityName()+" p " +
								" join p.before b " +
								" where b.id = :pid " +
								" and p.destination is null", getEntityClass() )
				.setParameter( "pid", id )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.size() == 0 )
			return null;

		return results.get( 0 );
	}

	@Override
	public T fetchById(Long id) {
		if ( id == null ) 
			throw new IllegalArgumentException( "id is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from "+getEntityName()+" p " +
			" left join fetch p.before b " + 
			" left join fetch p.after a " + 
			" where p.id = :pid", getEntityClass() )
			.setParameter( "pid", id )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (T)results.get( 0 );
	}	

	@Override
	public void save( T target ) {
		entityManager.persist( target );
		flush();
	}
	
	@Override
	public void update( T target ) {
		entityManager.merge( target );
		flush();
	}


	@Override
	public void deleteById(Long id, User author) {
		if (id != null) {
			T entityToDelete = findById(id);
			if (entityToDelete != null) {
				M manager= getManager();
				T result = manager.delete(author, new Time(new Date()), entityToDelete);
				entityManager.persist(result);
				flush();
			}
		}
	}
	
	protected void flush() {
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
	}



	

}
