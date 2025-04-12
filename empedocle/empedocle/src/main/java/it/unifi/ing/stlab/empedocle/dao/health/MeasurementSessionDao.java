package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.*;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Local
public interface MeasurementSessionDao {

	int count(MeasurementSessionQueryBuilder builder);
	Long countByType(MeasurementSessionType type);
	int countWoodElementHistory(Long woodElementId, Long measurementSessionFromId,
                            Set<MeasurementSessionStatus> statuses, Set<Agenda> agendas);
	Long countUserMeasurementSessionsByStatus(String userid, MeasurementSessionStatus status, Date start, Date end);
	
	boolean hasWoodElementHistory(Long woodElementId);
	boolean hasWoodElementHistory(Long woodElementId, Set<MeasurementSessionStatus> statuses, Set<Agenda> agendas);
	
	MeasurementSession findById(Long id);
	MeasurementSession findBySurveyScheduleCodes(String bookingCode, String acceptanceCode);
	List<MeasurementSession> find(MeasurementSessionQueryBuilder builder, int offset, int limit);
	List<MeasurementSession> findWoodElementLastExams(Long woodElementId, Long lastExamId, int numExams);
	List<MeasurementSession> findWoodElementHistory(Long woodElementId,
                                         Long measurementSessionFromId, Set<MeasurementSessionStatus> statuses,
                                         Set<Agenda> agendas, int offset, int limit);
	
	MeasurementSessionDetails fetchById(Long measurementSessionId, Long qualificationId, MeasurementSessionTypeContext context);
	MeasurementSessionDetails fetchByMeasurementSessionViewer(Long measurementSessionId, Long qualificationId, Long viewerId);

	boolean hasPermission(Long measurementSessionId, Long qualificationId, MeasurementSessionOperation operation);
	
	void update(MeasurementSession e);
	void save(MeasurementSession e);
	void deleteById(Long id) ;
	
	Fact resume(Fact f, WoodElement p);
}
