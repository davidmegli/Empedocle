package it.unifi.ing.stlab.football.api.dto;

public class ParticipationDTO {
    public int minutesPlayed;
    public int goals;
    public int assists;
    public ParticipationDTO() {
    }
    public ParticipationDTO(int minutesPlayed, int goals, int assists) {
        this.minutesPlayed = minutesPlayed;
        this.goals = goals;
        this.assists = assists;
    }
}