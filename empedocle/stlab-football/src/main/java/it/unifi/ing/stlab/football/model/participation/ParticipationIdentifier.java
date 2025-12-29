package it.unifi.ing.stlab.football.model.participation;

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
@Table( name = "participation_identifiers" )
public class ParticipationIdentifier extends ObservableEntityIdentifier implements Persistable {

    public ParticipationIdentifier() {
        super();
    }

    public ParticipationIdentifier(String uuid) {
        super(uuid);
    }



}
