package it.unifi.ing.stlab.football.api.dto;

import it.unifi.ing.stlab.football.model.roster.Roster;

import java.util.Date;

public class MatchDTO {
    public Long id;
    public Date date;
    public Long homeTeamId;
    public Long awayTeamId;
    public String place;
    public String identifierCode;

    public MatchDTO() {
    }

    public MatchDTO(Long id, Date date, Long homeTeamId, Long awayTeamId, String place, String identifierCode) {
        this.id = id;
        this.date = date;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.place = place;
        this.identifierCode = identifierCode;
    }
}