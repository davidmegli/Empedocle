package it.stlab.empedocle.api.woodelements.mapper;

import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.stlab.empedocle.api.woodelements.dto.CreateWoodElementRequest;

public class WoodElementMapper {
    public static WoodElement toEntity(CreateWoodElementRequest req) {
        WoodElement e = new WoodElement();
        e.setType(WoodElementType.valueOf(req.type));
        e.setExternalElementId(req.externalElementId);
        e.setAge(req.age);
        e.setSpecie(req.specie);
        e.setPlaceOfOrigin(req.placeOfOrigin);
        e.setNote(req.note);
        return e;
    }
}
