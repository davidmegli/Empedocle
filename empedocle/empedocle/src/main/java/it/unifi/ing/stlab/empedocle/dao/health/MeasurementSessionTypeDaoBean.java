package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.view.model.Viewer;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@TransactionAttribute
public class MeasurementSessionTypeDaoBean implements MeasurementSessionTypeDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int count(MeasurementSessionTypeQueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MeasurementSessionType> find(MeasurementSessionTypeQueryBuilder builder, int offset, int limit) {
		Query query = builder.getListQuery( entityManager ).setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<MeasurementSessionType>) query.getResultList();
	}

	@Override
	public MeasurementSessionType findById(Long id) {
		List<?> results = entityManager.createQuery( 
				"select distinct et " +
				" from MeasurementSessionType et " +
				"  left join fetch et.type " +
				"  left join fetch et.viewers " +
				"  left join fetch et.authorizations " +
				" where et.id = :id")
			.setParameter("id", id )
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return (MeasurementSessionType)results.get( 0 );
	}
	
	@Override
	public void delete(Long id) {
		MeasurementSessionType toRemove = findById(id);
		entityManager.remove(toRemove);
	}
	
	@Override
	public boolean isUsed( Long id ) {
		return entityManager.createQuery( 
				"select e " +
				" from MeasurementSession e " +
				" where e.type.id = :typeId" )
			.setParameter( "typeId", id )
			.setMaxResults( 1 )
			.getResultList()
			.size() > 0 || 
			entityManager.createQuery( 
				"select a " +
				" from Agenda a " +
				" where a.measurementSessionType.id = :typeId" )
			.setParameter( "typeId", id )
			.setMaxResults( 1 )
			.getResultList()
			.size() > 0;
	}
	
	@Override
	public MeasurementSessionType findByMeasurementSessionId(Long id) {
	//XXX passare dai services per avere l'agenda non serve più	
		
//		List<?> result = entityManager.createQuery(
//			"select et " +
//			" from MeasurementSession e " +
//			"  join e.survey_schedule.services s " +
//			"  join s.agenda.measurementSessionType et " +
//			" where e.id = :id" )
//			.setParameter("id", id )
//			.setMaxResults( 1 )
//			.getResultList();
		
		
		List<?> result = entityManager.createQuery(
				"select et " +
				" from MeasurementSession e " +
				"  join e.survey_schedule.agenda.measurementSessionType et " +
				" where e.id = :id" )
				.setParameter("id", id )
				.setMaxResults( 1 )
				.getResultList();		
		
		if ( result.size() > 0 ) {
			return (MeasurementSessionType)result.get( 0 );
		} else {
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Viewer> findAssociatedViewer( Long measurementSessionTypeId, Long qualificationId, MeasurementSessionTypeContext context ){
		return (List<Viewer>)entityManager.createQuery(" select v.viewer " +
														" from MeasurementSessionType et join et.viewers v " +
														" where et.id = :id and v.context = :context " +
														" and v.qualification.id = :qualificationId ")
											.setParameter("id", measurementSessionTypeId)
											.setParameter("context", context)
											.setParameter("qualificationId", qualificationId)
											.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MeasurementSessionType> findAll() {
		return entityManager
					.createQuery( "from MeasurementSessionType et" )
					.getResultList();
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	public List<MeasurementSessionType> findWithAgendas() {
		return entityManager.createQuery( 
				"select distinct a.measurementSessionType " 
						+ " from Agenda a "
						+ " order by a.measurementSessionType.name" )
				.getResultList();
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	public List<MeasurementSessionType> findWithAgendas( Long excludeId ) {
		return entityManager.createQuery( 
				"select distinct a.measurementSessionType " 
						+ " from Agenda a "
						+ " where a.measurementSessionType.id != :excludeId" 
						+ " order by a.measurementSessionType.name" )
				.setParameter( "excludeId", excludeId ).getResultList();
	}	

	@Override
	public void update( MeasurementSessionType measurementSessionType ) {
		entityManager.merge( measurementSessionType );
	}

	@Override
	public void save( MeasurementSessionType measurementSessionType ) {
		entityManager.persist( measurementSessionType );
	}
}
