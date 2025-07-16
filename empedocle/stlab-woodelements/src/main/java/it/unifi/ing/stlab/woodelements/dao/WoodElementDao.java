package it.unifi.ing.stlab.woodelements.dao;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

import javax.ejb.Local;

@Local
public interface WoodElementDao extends ObservableEntityDao<WoodElement>{
	@Override
	protected Class<WoodElement> getEntityClass() {
		return WoodElement.class;
	}
}
