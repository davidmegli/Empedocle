package it.unifi.ing.stlab.football.dao.roster;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.football.model.roster.RosterIdentifier;
import it.unifi.ing.stlab.football.manager.RosterManager;

import javax.ejb.Local;

@Local
public interface RosterDao extends ObservableEntityDao<Roster,RosterManager>{
	RosterIdentifier findIdentifierByCode(String code);
}
