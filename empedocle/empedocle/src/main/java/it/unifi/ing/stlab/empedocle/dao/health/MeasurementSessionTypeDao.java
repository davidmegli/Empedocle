package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MeasurementSessionTypeDao {

	int count(MeasurementSessionTypeQueryBuilder builder);
	List<MeasurementSessionType> find(MeasurementSessionTypeQueryBuilder builder, int offset, int limit);
	MeasurementSessionType findById(Long id);

	void delete(Long id);
	boolean isUsed(Long id);
	
	MeasurementSessionType findByMeasurementSessionId(Long id);
	List<Viewer> findAssociatedViewer(Long measurementSessionTypeId, Long qualificationId, MeasurementSessionTypeContext context);
	
	List<MeasurementSessionType> findAll();
	List<MeasurementSessionType> findWithAgendas();
	List<MeasurementSessionType> findWithAgendas(Long excludeId);
	
	void update(MeasurementSessionType current);
	void save(MeasurementSessionType current);
	
}
