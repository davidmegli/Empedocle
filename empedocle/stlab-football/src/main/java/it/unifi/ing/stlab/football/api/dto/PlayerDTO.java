package it.unifi.ing.stlab.football.api.dto;

import it.unifi.ing.stlab.football.model.player.Player.PlayerRole;
import it.unifi.ing.stlab.football.model.roster.Roster;

import java.util.Date;

public class PlayerDTO {
    public Long id;
    public String name;
    public String surname;
    public Date birthDate;
    public PlayerRole role;
    public String identifierCode;
    public Long rosterId;


    public PlayerDTO() {
    }

    public PlayerDTO(Long id, String name, String surname, Date birthDate, PlayerRole role, String identifierCode, Long rosterId) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.identifierCode = identifierCode;
        this.rosterId = rosterId;
    }
}