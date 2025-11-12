package it.unifi.ing.stlab.empedocle.dao;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.*;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Local
public interface MeasurementSessionDao {

	int count(MeasurementSessionQueryBuilder builder);
	Long countByType(MeasurementSessionType type);
	int countObservableEntityHistory(Long observableEntityId, Long measurementSessionFromId,
                            Set<MeasurementSessionStatus> statuses, Set<Agenda> agendas);
	Long countUserMeasurementSessionsByStatus(String userid, MeasurementSessionStatus status, Date start, Date end);
	
	boolean hasObservableEntityHistory(Long observableEntityId);
	boolean hasObservableEntityHistory(Long observableEntityId, Set<MeasurementSessionStatus> statuses, Set<Agenda> agendas);
	
	MeasurementSession findById(Long id);
	MeasurementSession findBySurveyScheduleCodes(String bookingCode, String acceptanceCode);
	List<MeasurementSession> find(MeasurementSessionQueryBuilder builder, int offset, int limit);
	List<MeasurementSession> findObservableEntityLastMeasurementSessions(Long observableEntityId, Long lastMeasurementSessionId, int numMeasurementSessions);
	List<MeasurementSession> findObservableEntityHistory(Long observableEntityId,
                                         Long measurementSessionFromId, Set<MeasurementSessionStatus> statuses,
                                         Set<Agenda> agendas, int offset, int limit);
	
	MeasurementSessionDetails fetchById(Long measurementSessionId, Long qualificationId, MeasurementSessionTypeContext context);
	boolean hasPermission(Long measurementSessionId, Long qualificationId, MeasurementSessionOperation operation);
	
	void update(MeasurementSession e);
	MeasurementSession save(MeasurementSession e);
	void deleteById(Long id) ;
	void delete(Long id);
	
	Fact resume(Fact f, ObservableEntity p);
}
