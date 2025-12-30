package it.unifi.ing.stlab.football.api.mapper;

import it.unifi.ing.stlab.football.api.dto.MatchDTO;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.roster.Roster;

public class MatchMapper {

    public static MatchDTO toDto(Match m) {
        if (m == null) return null;

        MatchDTO dto = new MatchDTO();
        dto.id = m.getId();
        dto.date = m.getDate();
        if (m.getHomeTeam() != null){
            dto.homeTeamId = m.getHomeTeam().getId();
        }
        if (m.getAwayTeam() != null){
            dto.awayTeamId = m.getAwayTeam().getId();
        }
        dto.place = m.getPlace();
        if (m.getIdentifier() != null) {
            dto.identifierCode = m.getIdentifier().getCode();
        }
        return dto;
    }

    public static void updateEntity(Match m, MatchDTO dto, Roster homeTeam, Roster awayTeam) {
        m.setDate(dto.date);
        m.setHomeTeam(homeTeam);
        m.setAwayTeam(awayTeam);
        m.setPlace(dto.place);
        m.getIdentifier().setCode(dto.identifierCode);
    }
}
