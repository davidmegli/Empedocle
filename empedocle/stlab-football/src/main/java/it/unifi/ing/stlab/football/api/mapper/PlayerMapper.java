package it.unifi.ing.stlab.football.api.mapper;

import it.unifi.ing.stlab.football.api.dto.PlayerDTO;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.football.model.player.Player.PlayerRole;

public class PlayerMapper {

    public static PlayerDTO toDto(Player p) {
        if (p == null) return null;

        PlayerDTO dto = new PlayerDTO();
        dto.id = p.getId();
        dto.name = p.getName();
        dto.surname = p.getSurname();
        dto.role = p.getRole();
        dto.birthDate = p.getBirthDate();
        if (p.getIdentifier() != null) {
            dto.identifierCode = p.getIdentifier().getCode();
        }
        if (p.getRoster() != null){
            dto.rosterId = p.getRoster().getId();
        }
        return dto;
    }

    public static void updateEntity(Player p, PlayerDTO dto, Roster roster) {
        p.setName(dto.name);
        p.setSurname(dto.surname);
        p.setBirthDate(dto.birthDate);
        p.setRole(dto.role);
        p.getIdentifier().setCode(dto.identifierCode);
        p.setRoster(roster);
    }
}
