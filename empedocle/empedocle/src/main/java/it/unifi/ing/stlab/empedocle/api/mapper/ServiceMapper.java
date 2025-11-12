package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.ServiceDTO;
import it.unifi.ing.stlab.empedocle.model.Service;
import it.unifi.ing.stlab.empedocle.model.Agenda;

public class ServiceMapper {

    public static ServiceDTO toDto(Service entity) {
        if (entity == null) return null;

        ServiceDTO dto = new ServiceDTO();
        dto.id = entity.getId();
        dto.uuid = entity.getUuid();
        dto.code = entity.getCode();
        dto.description = entity.getDescription();
        dto.internalCode = entity.getInternalCode();
        dto.agendaId = entity.getAgenda() != null ? entity.getAgenda().getId() : null;

        return dto;
    }

    public static void updateEntity(Service entity, ServiceDTO dto, Agenda agenda) {
        entity.setCode(dto.code);
        entity.setDescription(dto.description);
        entity.setInternalCode(dto.internalCode);
        entity.setAgenda(agenda);
    }
}
