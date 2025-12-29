package it.unifi.ing.stlab.football.model.player;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "player_identifiers" )
public class PlayerIdentifier extends ObservableEntityIdentifier implements Persistable {

    public PlayerIdentifier() {
        super();
    }

    public PlayerIdentifier(String uuid) {
        super(uuid);
    }
    
}
