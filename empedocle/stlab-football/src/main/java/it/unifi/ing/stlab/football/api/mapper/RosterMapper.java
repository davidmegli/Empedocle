package it.unifi.ing.stlab.football.api.mapper;
import it.unifi.ing.stlab.football.api.dto.RosterDTO;
import it.unifi.ing.stlab.football.model.roster.Roster;

public class RosterMapper {
    public static RosterDTO toDTO(Roster roster) {
        if (roster == null) {
            return null;
        }
        return new RosterDTO(roster.getName());
    }

    public static Roster fromDTO(RosterDTO rosterDTO) {
        if (rosterDTO == null) {
            return null;
        }
        Roster roster = new Roster();
        roster.setName(rosterDTO.getName());
        return roster;
    }
}