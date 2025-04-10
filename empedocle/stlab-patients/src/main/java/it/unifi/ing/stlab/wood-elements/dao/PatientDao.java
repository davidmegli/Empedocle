package it.unifi.ing.stlab.wood-elements.dao;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.wood-elements.model.WoodElement;
import it.unifi.ing.stlab.wood-elements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.users.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface WoodElementDao {

	int count(QueryBuilder builder);
	
	List<WoodElement> find(QueryBuilder builder, int offset, int limit);
	WoodElement findById(Long id);
	WoodElement findByUuid(String value);
	WoodElement findByIdentifier(String identifier);
	
	//XXX lasciato per garantire la compatibilità con il vecchio approccio,
	// in cui gli appointment non venivano spostati nell'ultima versione
    WoodElement findLastVersionById(Long id);
	
	List<WoodElement> findByName(String name, String surname);
	WoodElement findByTaxCode(String taxCode);
	List<WoodElement> findBySuggestion(String suggestion, int limit);
	List<WoodElement> findForMerge(String name, String surname, Long idExclude);
	
	WoodElementIdentifier findIdentifierByCode(String code);
	
	WoodElement fetchById(Long id);
	
	WoodElement mergeWoodElements(Long wood_elementId, Long otherId, User author);
	
	void save(WoodElement target);
	void update(WoodElement target);
	void deleteById(Long wood_elementId, User author);

}
