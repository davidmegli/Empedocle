package it.unifi.ing.stlab.football.model.roster;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;


@Entity
@Table( name = "roster_identifiers" )
public class RosterIdentifier extends ObservableEntityIdentifier implements Persistable {

    public RosterIdentifier() {
        super();
    }

    public RosterIdentifier(String uuid) {
        super(uuid);
    }



}
