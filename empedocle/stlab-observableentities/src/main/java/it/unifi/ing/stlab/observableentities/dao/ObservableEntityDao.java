package it.unifi.ing.stlab.observableentities.dao;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;
import it.unifi.ing.stlab.users.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ObservableEntityDao <T extends ObservableEntity>{

	int count(QueryBuilder builder);
	
	List<T> find(QueryBuilder builder, int offset, int limit);
	T findById(Long id);
	T findByUuid(String uuid);
	T findByIdentifier(String identifier);
	
	//XXX lasciato per garantire la compatibilità con il vecchio approccio,
	// in cui gli survey_schedule non venivano spostati nell'ultima versione
    T findLastVersionById(Long id);

	T fetchById(Long id);

	void save(T target);
	void update(T target);
	void deleteById(Long id, User author);

}
