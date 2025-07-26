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
        dto.number = entity.number;

        if (entity.user != null) {
            dto.userId = entity.user.getId();
            dto.username = entity.user.getUserid();
        }

        if (entity.phenomenon != null) {
            dto.phenomenonId = entity.phenomenon.getId();
            dto.phenomenonName = entity.phenomenon.getName();
        }

        if (entity.defaultAgenda != null) {
            dto.defaultAgendaId = entity.defaultAgenda.getId();
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
