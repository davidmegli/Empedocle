package it.unifi.ing.stlab.empedocle.dao;

import it.unifi.ing.stlab.empedocle.model.SurveySchedule;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface SurveyScheduleDao {
	
	List<SurveySchedule> findByObservableEntities(Set<ObservableEntity> observableEntities);
	SurveySchedule findById(Long id);
	void update(SurveySchedule surveySchedule);
	SurveySchedule save(SurveySchedule surveySchedule);
	void delete(Long id);
}
