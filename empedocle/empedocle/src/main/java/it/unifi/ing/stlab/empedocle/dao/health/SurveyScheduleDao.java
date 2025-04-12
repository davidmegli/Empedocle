package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface SurveyScheduleDao {
	
	List<SurveySchedule> findByWoodElements(Set<WoodElement> woodElements);
	void update(SurveySchedule a);
}
