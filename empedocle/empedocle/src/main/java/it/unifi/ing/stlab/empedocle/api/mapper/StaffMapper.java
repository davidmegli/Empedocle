package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.StaffDTO;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.empedocle.model.Agenda;

import java.util.stream.Collectors;

public class StaffMapper {

    public static StaffDTO toDto(Staff entity) {
        if (entity == null) return null;

        StaffDTO dto = new StaffDTO();
        dto.id = entity.getId();
        dto.uuid = entity.getUuid();
        dto.number = entity.getNumber();

        if (entity.getUser() != null) {
            dto.userId = entity.getUser().getId();
            dto.username = entity.getUser().getUserid();
        }

        if (entity.getPhenomenon() != null) {
            dto.phenomenonId = entity.getPhenomenon().getId();
            dto.phenomenonName = entity.getPhenomenon().getName();
        }

        if (entity.getPhenomenon() != null) {
            dto.defaultAgendaId = entity.getDefaultAgenda().getId();
        }

        dto.agendaIds = entity.listAgendas().stream()
                .map(Agenda::getId)
                .collect(Collectors.toSet());

        dto.favoriteAgendaIds = entity.listFavoriteAgendas().stream()
                .map(Agenda::getId)
                .collect(Collectors.toSet());

        return dto;
    }
}
