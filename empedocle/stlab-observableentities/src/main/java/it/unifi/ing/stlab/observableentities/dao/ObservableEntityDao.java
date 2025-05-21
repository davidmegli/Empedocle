package it.unifi.ing.stlab.observableentities.dao;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;
import it.unifi.ing.stlab.users.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ObservableEntityDao {

	int count(QueryBuilder builder);
	
	List<ObservableEntity> find(QueryBuilder builder, int offset, int limit);
	ObservableEntity findById(Long id);
	ObservableEntity findByUuid(String value);
	ObservableEntity findByIdentifier(String identifier);
	
	//XXX lasciato per garantire la compatibilità con il vecchio approccio,
	// in cui gli survey_schedule non venivano spostati nell'ultima versione
    ObservableEntity findLastVersionById(Long id);
	
	List<ObservableEntity> findByName(String name, String surname);
	ObservableEntity findByTaxCode(String taxCode);
	List<ObservableEntity> findBySuggestion(String suggestion, int limit);
	List<ObservableEntity> findForMerge(String name, String surname, Long idExclude);
	
	ObservableEntityIdentifier findIdentifierByCode(String code);
	
	ObservableEntity fetchById(Long id);
	
	ObservableEntity mergeObservableEntities(Long observableEntityId, Long otherId, User author);
	
	void save(ObservableEntity target);
	void update(ObservableEntity target);
	void deleteById(Long observableEntityId, User author);

}
