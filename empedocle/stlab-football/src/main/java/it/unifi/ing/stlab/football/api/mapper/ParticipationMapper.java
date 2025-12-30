package it.unifi.ing.stlab.football.api.mapper;
import it.unifi.ing.stlab.football.api.dto.ParticipationDTO;
import it.unifi.ing.stlab.football.model.participation.Participation;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.match.Match;

public class ParticipationMapper {
    public static ParticipationDTO toDto(Participation participation) {
        if (participation == null) {
            return null;
        }
        return new ParticipationDTO(
                participation.getId(),
                participation.getIdentifier().getCode(),
                participation.getMinutesPlayed(),
                participation.getGoals(),
                participation.getAssists(),
                participation.getStart(),
                participation.getDisciplinaryStatus(),
                participation.getPlayerNumber(),
                participation.getPlayer() != null ? participation.getPlayer().getId() : null,
                participation.getMatch() != null ? participation.getMatch().getId(): null
        );
    }

    public static Participation updateEntity(Participation participation, ParticipationDTO participationDTO, Player player, Match match) {
        if (participationDTO == null) {
            return null;
        }
        participation.setMinutesPlayed(participationDTO.minutesPlayed);
        participation.setGoals(participationDTO.goals);
        participation.setAssists(participationDTO.assists);
        participation.setStart(participationDTO.start);
        participation.setDisciplinaryStatus(participationDTO.disciplinaryStatus);
        participation.setPlayerNumber(participationDTO.playerNumber);
        participation.setPlayer(player);
        participation.setMatch(match);
        participation.getIdentifier().setCode(participationDTO.identifierCode);
        return participation;
    }
}