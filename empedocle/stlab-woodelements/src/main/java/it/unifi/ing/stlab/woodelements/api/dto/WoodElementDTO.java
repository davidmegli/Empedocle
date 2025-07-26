package it.unifi.ing.stlab.woodelements.api.dto;

import it.unifi.ing.stlab.woodelements.model.WoodElement.WoodElementType;

public class WoodElementDTO {
    public Long id;
    public WoodElementType type;
    public String specie;
    public int age;
    public String placeOfOrigin;
    public String externalElementId;
    public String note;

    public WoodElementDTO() {
    }

    public WoodElementDTO(Long id, WoodElementType type, String specie, int age, String placeOfOrigin, String externalElementId, String note) {
        this.id = id;
        this.type = type;
        this.specie = specie;
        this.age = age;
        this.placeOfOrigin = placeOfOrigin;
        this.externalElementId = externalElementId;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WoodElementType getType() {
        return type;
    }

    public void setType(WoodElementType type) {
        this.type = type;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getExternalElementId() {
        return externalElementId;
    }

    public void setExternalElementId(String externalElementId) {
        this.externalElementId = externalElementId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}