package it.unifi.ing.stlab.empedocle.dao.health;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

@Stateless
@TransactionAttribute
@Local(SurveyScheduleDao.class)
public class SurveyScheduleDaoBean implements SurveyScheduleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings( "unchecked" )
	@Override
	public List<SurveySchedule> findByObservableEntities( Set<ObservableEntity> observableEntities ) {
		return ( List<SurveySchedule> ) entityManager
				.createQuery(
					"select a" + 
					" from SurveySchedule a" +
					" where a.observableEntity in :observableEntities " )
				.setParameter( "observableEntities", observableEntities ).getResultList();
	}
	@Override
	public SurveySchedule findById(Long id){
		return entityManager.find(SurveySchedule.class, id);
	}
	
	@Override
	public void update( SurveySchedule target ) {
		entityManager.merge( target );
	}
	@Override
	public SurveySchedule save(SurveySchedule surveySchedule){
		return entityManager.merge(surveySchedule);
	}
	@Override
	public void delete( Long id ) {
		entityManager.remove( findById( id ) );
	}
}
