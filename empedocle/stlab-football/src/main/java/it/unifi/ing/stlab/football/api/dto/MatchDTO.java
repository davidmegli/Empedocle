package it.unifi.ing.stlab.football.api.dto;

import it.unifi.ing.stlab.football.model.roster.Roster;

import java.util.Date;

public class MatchDTO {
    public Long id;
    public Date date;
    public Roster homeTeam;
    public Roster awayTeam;
    public String place;
    public String identifierCode;

    public MatchDTO() {
    }

    public MatchDTO(Long id, Date date, Roster homeTeam, Roster AwayTeam, String place, String identifierCode) {
        this.id = id;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.place = place;
        this.identifierCode = identifierCode;
    }
}