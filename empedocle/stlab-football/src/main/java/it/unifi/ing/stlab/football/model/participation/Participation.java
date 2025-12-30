package it.unifi.ing.stlab.football.model.participation;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.factory.participation.ParticipationFactory;
import it.unifi.ing.stlab.football.model.participation.actions.ParticipationAction;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Participation extends ObservableEntity<Participation, ParticipationAction, ParticipationIdentifier, ParticipationFactory> {

    private int minutesPlayed;
    private int goals;
    private int assists;
    private Player player;
    private Match match;

    public Participation( String uuid ) {
        super(uuid);

    }
    public Participation() {
        super();
    }

    @Column( name = "minutes_played" )
    public int getMinutesPlayed() {
        return minutesPlayed;
    }
    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }
    @Column( name = "goals" )
    public int getGoals() {
        return goals;
    }
    public void setGoals(int goals) {
        this.goals = goals;
    }
    @Column( name = "assists" )
    public int getAssists() {
        return assists;
    }
    public void setAssists(int assists) {
        this.assists = assists;
    }
    @ManyToOne
    @JoinColumn(name = "player_id")
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @ManyToOne
    @JoinColumn(name = "match_id")
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    @Transient
    public boolean isEmpty() {
        return 	minutesPlayed == 0 &&
                goals == 0 &&
                assists == 0 &&
                player == null &&
                match == null;
    }

    @Override
    public boolean sameAs(Participation entity) {
        if (entity == null) return false;

        return
                ( minutesPlayed == entity.getMinutesPlayed() ) &&
                ( goals == entity.getGoals() ) &&
                ( assists == entity.getAssists() ) &&
                        ( player == null ? entity.getPlayer() == null : player.equals(entity.getPlayer()) ) &&
                        ( match == null ? entity.getMatch() == null : match.equals(entity.getMatch()) ) ;
    }



    @Override
    public Participation copy() {
        ParticipationFactory factory = new ParticipationFactory();
        Participation result = factory.create();

        result.setMinutesPlayed( this.getMinutesPlayed() );
        result.setGoals( this.getGoals() );
        result.setAssists( this.getAssists() );
        result.setPlayer( this.getPlayer() );
        result.setMatch( this.getMatch() );

        return result;
    }

    public void update(){};
}

