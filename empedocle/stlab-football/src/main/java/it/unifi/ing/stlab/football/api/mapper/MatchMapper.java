package it.unifi.ing.stlab.football.api.mapper;

import it.unifi.ing.stlab.football.api.dto.MatchDTO;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.match.Match.MatchRole;

public class MatchMapper {

    public static MatchDTO toDto(Match m) {
        if (m == null) return null;

        MatchDTO dto = new MatchDTO();
        dto.id = m.getId();
        dto.date = m.getDate();
        dto.homeTeam = m.getHomeTeam();
        dto.awayTeam = m.getAwayTeam();
        dto.place = m.getPlace();
        if (m.getIdentifier() != null) {
            dto.identifierCode = m.getIdentifier().getCode();
        }
        return dto;
    }

    public static void updateEntity(Match p, MatchDTO dto) {
        m.setDate(dto.date);
        m.setHomeTeam(dto.homeTeam);
        m.setAwayTeam(dto.awayTeam);
        m.setPlace(dto.place);
        m.getIdentifier().setCode(dto.identifierCode);
    }
}
