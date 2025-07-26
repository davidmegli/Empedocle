package it.unifi.ing.stlab.woodelements.api.mapper;

import it.unifi.ing.stlab.woodelements.api.dto.WoodElementDTO;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

public class WoodElementMapper {

    public static WoodElementDTO toDto(WoodElement element) {
        WoodElementDTO dto = new WoodElementDTO();
        dto.id = element.getExternalElementId();
        dto.type = element.getType().name();
        dto.specie = element.getSpecie();
        dto.age = element.getAge();
        dto.placeOfOrigin = element.getPlaceOfOrigin();
        dto.empty = element.isEmpty();
        dto.externalElementId = element.getExternalElementId();
        dto.note = element.getNote();
        return dto;
    }

    public static void updateEntity(WoodElement element, WoodElementDTO dto) {
        element.setType(WoodElementType.valueOf(dto.type));
        element.setSpecie(dto.specie);
        element.setAge(dto.age);
        element.setPlaceOfOrigin(dto.placeOfOrigin);
        element.setEmpty(dto.empty);
        element.setExternalElementId(dto.externalElementId);
        element.setNote(dto.note);
    }

    public static WoodElement toEntity(WoodElementDTO dto) {
        WoodElement element = new WoodElement();
        updateEntity(element, dto);
        return element;
    }
}