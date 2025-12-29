package it.unifi.ing.stlab.football.api.dto;

public class RosterDTO {
    public String name;
    public Long id;
    public String identifierCode;

    public RosterDTO() {
    }

    public RosterDTO(String name, String identifierCode) {
        this.name = name;
        this.identifierCode = identifierCode;
    }
}