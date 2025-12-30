package it.unifi.ing.stlab.football.model.match;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.factory.match.MatchFactory;
import it.unifi.ing.stlab.football.model.match.actions.MatchAction;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Match extends ObservableEntity<Match, MatchAction, MatchIdentifier, MatchFactory> {
    

    //attributs
    private Date date;
    private Roster homeTeam;
    private Roster awayTeam;
    private String place;


    //constructors
    public Match( String uuid ) {
        super(uuid);

    }
    public Match() {
        super();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name="home_team_id")
    public Roster getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Roster homeTeam) {
        this.homeTeam = homeTeam;
    }

    @ManyToOne
    @JoinColumn(name="away_team_id")
    public Roster getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Roster awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    // Methods
    @Override
    @Transient
    public boolean isEmpty() {
        return 	identifier == null &&
                date == null &&
                homeTeam == null &&
                awayTeam == null &&
                place == null;
    }

    @Override
    public boolean sameAs(Match m) {
        if (m == null) return false;

        return
                ( identifier == null ? m.getIdentifier() == null : identifier.equals(m.getIdentifier()) ) &&
                        (date == null ? m.getDate() == null : date.equals(m.getDate())) &&
                        (homeTeam == null ? m.getHomeTeam() == null : homeTeam.equals(m.getHomeTeam())) &&
                        (awayTeam == null ? m.getAwayTeam() == null : awayTeam.equals(m.getAwayTeam()));
    }



    @Override
    public Match copy() {
        MatchFactory factory = new MatchFactory();
        Match result = factory.create();

        result.setIdentifier(getIdentifier());
        result.setDate(getDate());
        result.setHomeTeam(getHomeTeam());
        result.setAwayTeam(getAwayTeam());
        result.setPlace(getPlace());

        return result;
    }

    public void update(){};
}

