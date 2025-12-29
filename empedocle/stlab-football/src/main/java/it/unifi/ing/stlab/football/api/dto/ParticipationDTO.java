package it.unifi.ing.stlab.football.api.dto;

public class ParticipationDTO {
    public Long id;
    public String identifierCode;
    public int minutesPlayed;
    public int goals;
    public int assists;
    public ParticipationDTO() {
    }
    public ParticipationDTO(String identifierCode, int minutesPlayed, int goals, int assists) {
        this.identifierCode = identifierCode;
        this.minutesPlayed = minutesPlayed;
        this.goals = goals;
        this.assists = assists;
    }
}