package it.unifi.ing.stlab.football.dao.participation;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.model.participation.Participation;
import it.unifi.ing.stlab.football.manager.ParticipationManager;
import it.unifi.ing.stlab.football.model.participation.ParticipationIdentifier;

import javax.ejb.Local;

@Local
public interface ParticipationDao extends ObservableEntityDao<Participation,ParticipationManager>{
    ParticipationIdentifier findIdentifierByCode(String code);
}
