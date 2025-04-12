package it.unifi.ing.stlab.empedocle.dao.health;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

@Stateless
@TransactionAttribute
public class SurveyScheduleDaoBean implements SurveyScheduleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings( "unchecked" )
	@Override
	public List<SurveySchedule> findByWoodElements( Set<WoodElement> woodElements ) {
		return ( List<SurveySchedule> ) entityManager
				.createQuery(
					"select a" + 
					" from SurveySchedule a" +
					" where a.wood_element in :wood_elements " )
				.setParameter( "wood_elements", woodElements ).getResultList();
	}
	
	@Override
	public void update( SurveySchedule target ) {
		entityManager.merge( target );
	}
}
