package it.unifi.ing.stlab.woodelements.api.mapper;

import it.unifi.ing.stlab.woodelements.api.dto.WoodElementDTO;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.WoodElement.WoodElementType;

public class WoodElementMapper {

    public static WoodElementDTO toDto(WoodElement element) {
        if (element == null) return null;

        WoodElementDTO dto = new WoodElementDTO();
        dto.id = element.getId();
        dto.type = element.getType();
        dto.specie = element.getSpecie();
        dto.age = element.getAge();
        dto.placeOfOrigin = element.getPlaceOfOrigin();
        dto.externalElementId = element.getExternalElementId();
        dto.note = element.getNote();
        if (element.getIdentifier() != null) {
            dto.identifierCode = element.getIdentifier().getCode();
        }
        return dto;
    }

    public static void updateEntity(WoodElement element, WoodElementDTO dto) {
        element.setType(dto.type);
        element.setSpecie(dto.specie);
        element.setAge(dto.age);
        element.setPlaceOfOrigin(dto.placeOfOrigin);
        element.setExternalElementId(dto.externalElementId);
        element.setNote(dto.note);
        element.getIdentifier().setCode(dto.identifierCode);
    }
}
