package it.unifi.ing.stlab.football.api.mapper;
import it.unifi.ing.stlab.football.api.dto.RosterDTO;
import it.unifi.ing.stlab.football.model.roster.Roster;

public class RosterMapper {
    public static RosterDTO toDto(Roster roster) {
        if (roster == null) {
            return null;
        }
        return new RosterDTO(roster.getId(), roster.getName(), roster.getIdentifier().getCode());
    }

    public static Roster updateEntity(Roster roster, RosterDTO rosterDTO) {
        if (rosterDTO == null) {
            return null;
        }
        roster.setName(rosterDTO.name);
        roster.getIdentifier().setCode(rosterDTO.identifierCode);
        return roster;
    }
}