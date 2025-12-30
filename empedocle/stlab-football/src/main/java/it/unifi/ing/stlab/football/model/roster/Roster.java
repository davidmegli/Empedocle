package it.unifi.ing.stlab.football.model.roster;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.factory.roster.RosterFactory;
import it.unifi.ing.stlab.football.model.roster.actions.RosterAction;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.List;

@Entity
public class Roster extends ObservableEntity<Roster, RosterAction, RosterIdentifier, RosterFactory> {

    @Enumerated(EnumType.STRING)
    private String name;

    public Roster( String uuid ) {
        super(uuid);

    }
    public Roster() {
        super();
    }
    @Column( name = "name" )
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Methods
    @Override
    @Transient
    public boolean isEmpty() {
        return 	( name == null || name.isEmpty() ) ;
    }

    @Override
    public boolean sameAs(Roster entity) {
        if (entity == null) return false;

        return     ( ( name == null && entity.getName() == null ) ||
                    ( name != null && name.equals( entity.getName() ) ) );
    }



    @Override
    public Roster copy() {
        RosterFactory factory = new RosterFactory();
        Roster result = factory.create();

        result.setName( this.getName() );

        return result;
    }

    public void update(){};
}

