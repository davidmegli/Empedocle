package it.unifi.ing.stlab.football.api.dto;

import java.util.Date;
import it.unifi.ing.stlab.football.model.participation.Participation.PlayerDisciplinaryStatus;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.match.Match;

public class ParticipationDTO {
    public Long id;
    public String identifierCode;
    public int minutesPlayed;
    public int goals;
    public int assists;
    public Date start;
    public PlayerDisciplinaryStatus disciplinaryStatus;
    public int playerNumber;
    public Long playerId;
    public Long matchId;

    public ParticipationDTO() {
    }
    public ParticipationDTO(Long id, String identifierCode, int minutesPlayed, int goals, int assists, Date start, PlayerDisciplinaryStatus disciplinaryStatus,
                            int playerNumber, Long playerId, Long matchId) {
        this.id = id;
        this.identifierCode = identifierCode;
        this.minutesPlayed = minutesPlayed;
        this.goals = goals;
        this.assists = assists;
        this.start = start;
        this.disciplinaryStatus = disciplinaryStatus;
        this.playerNumber = playerNumber;
        this.playerId = playerId;
        this.matchId = matchId;
    }
}