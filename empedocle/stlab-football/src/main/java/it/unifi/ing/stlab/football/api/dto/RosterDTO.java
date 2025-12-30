package it.unifi.ing.stlab.football.api.dto;
import java.util.Set;

public class RosterDTO {
    public String name;
    public Long id;
    public String identifierCode;

    public RosterDTO() {
    }

    public RosterDTO(Long id, String name, String identifierCode) {
        this.id = id;
        this.name = name;
        this.identifierCode = identifierCode;
    }
}