package it.unifi.ing.stlab.woodelements.api.dto;

public class WoodElementDTO {
    public Long id;
    public String type;
    public String specie;
    public int age;
    public String placeOfOrigin;
    public boolean empty;
    public String externalElementId;
    public String note;

    public WoodElementDTO() {
    }

    public WoodElementDTO(String id, String type, String specie, int age, String placeOfOrigin, boolean empty, String externalElementId, String note) {
        this.id = id;
        this.type = type;
        this.specie = specie;
        this.age = age;
        this.placeOfOrigin = placeOfOrigin;
        this.empty = empty;
        this.externalElementId = externalElementId;
        this.note = note;
    }
}