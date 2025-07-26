package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.AgendaDTO;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;

public class AgendaMapper {

    public static AgendaDTO toDto(Agenda entity) {
        if (entity == null) return null;

        AgendaDTO dto = new AgendaDTO();
        dto.id = entity.getId();
        dto.uuid = entity.getUuid();
        dto.code = entity.getCode();
        dto.description = entity.getDescription();
        dto.measurementSessionTypeId = entity.getMeasurementSessionType() != null
                ? entity.getMeasurementSessionType().getId()
                : null;

        return dto;
    }

    public static void updateEntity(Agenda entity, AgendaDTO dto, MeasurementSessionType mst) {
        entity.setCode(dto.code);
        entity.setDescription(dto.description);
        entity.setMeasurementSessionType(mst);
    }
}
