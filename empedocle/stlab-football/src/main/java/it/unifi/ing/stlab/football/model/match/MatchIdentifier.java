package it.unifi.ing.stlab.football.model.match;

import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "match_identifiers" )
public class MatchIdentifier extends ObservableEntityIdentifier implements Persistable {

    public MatchIdentifier() {
        super();
    }

    public MatchIdentifier(String uuid) {
        super(uuid);
    }
}
