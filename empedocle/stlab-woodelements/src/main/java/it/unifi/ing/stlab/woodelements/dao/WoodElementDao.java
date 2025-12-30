package it.unifi.ing.stlab.woodelements.dao;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;

import javax.ejb.Local;
import it.unifi.ing.stlab.users.model.User;

@Local
public interface WoodElementDao extends ObservableEntityDao<WoodElement, WoodElementManager>{
    WoodElementIdentifier findIdentifierByCode(String code);
    WoodElement mergeWoodElements( Long woodElementId, Long otherId, User author );
}
