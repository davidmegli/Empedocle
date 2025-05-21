package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface SurveyScheduleDao {
	
	List<SurveySchedule> findByObservableEntities(Set<ObservableEntity> observableEntities);
	void update(SurveySchedule a);
}
