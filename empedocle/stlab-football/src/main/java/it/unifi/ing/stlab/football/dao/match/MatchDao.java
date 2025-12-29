package it.unifi.ing.stlab.football.dao.match;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.manager.MatchManager;

import javax.ejb.Local;

@Local
public interface MatchDao extends ObservableEntityDao<Match, MatchManager>{
    MatchIdentifier findIdentifierByCode(String code);
}
