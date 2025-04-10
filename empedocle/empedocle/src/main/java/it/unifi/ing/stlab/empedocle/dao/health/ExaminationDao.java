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
public interface ExaminationDao {

	int count(ExaminationQueryBuilder builder);
	Long countByType(ExaminationType type);
	int countWoodElementHistory(Long woodElementId, Long examFromId,
                            Set<ExaminationStatus> statuses, Set<Agenda> agendas);
	Long countUserExaminationsByStatus(String userid, ExaminationStatus status, Date start, Date end);
	
	boolean hasWoodElementHistory(Long woodElementId);
	boolean hasWoodElementHistory(Long woodElementId, Set<ExaminationStatus> statuses, Set<Agenda> agendas);
	
	Examination findById(Long id);
	Examination findByAppointmentCodes(String bookingCode, String acceptanceCode);
	List<Examination> find(ExaminationQueryBuilder builder, int offset, int limit);
	List<Examination> findWoodElementLastExams(Long woodElementId, Long lastExamId, int numExams);
	List<Examination> findWoodElementHistory(Long woodElementId,
                                         Long examFromId, Set<ExaminationStatus> statuses,
                                         Set<Agenda> agendas, int offset, int limit);
	
	ExaminationDetails fetchById(Long examinationId, Long qualificationId, ExaminationTypeContext context);
	ExaminationDetails fetchByExaminationViewer(Long examinationId, Long qualificationId, Long viewerId);

	boolean hasPermission(Long examinationId, Long qualificationId, ExaminationOperation operation);
	
	void update(Examination e);
	void save(Examination e);
	void deleteById(Long id) ;
	
	Fact resume(Fact f, WoodElement p);
}
