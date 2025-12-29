package it.unifi.ing.stlab.football.api.mapper;
import it.unifi.ing.stlab.football.api.dto.ParticipationDTO;
import it.unifi.ing.stlab.football.model.participation.Participation;

public class ParticipationMapper {
    public static ParticipationDTO toDTO(Participation participation) {
        if (participation == null) {
            return null;
        }
        return new ParticipationDTO(
                participation.getMinutesPlayed(),
                participation.getGoals(),
                participation.getAssists()
        );
    }

    public static Participation fromDTO(ParticipationDTO participationDTO) {
        if (participationDTO == null) {
            return null;
        }
        Participation participation = new Participation();
        participation.setMinutesPlayed(participationDTO.minutesPlayed);
        participation.setGoals(participationDTO.goals);
        participation.setAssists(participationDTO.assists);
        return participation;
    }
}