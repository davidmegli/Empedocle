package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.StaffDTO;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.factory.StaffFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;

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
            dto.phenomenonUuid = entity.getPhenomenon().getUuid();
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

    public static void updateEntity(Staff entity, StaffDTO dto) {
        if (entity == null || dto == null) return;

        entity.setNumber(dto.number);

        if (dto.userId != null) {
//        TODO: sistemare autenticazione User
//        User user = new User();
//        user.setUserid(dto.username);
            entity.setUser(null);
        }

        if (dto.phenomenonUuid != null) {
            Phenomenon phenomenon = PhenomenonFactory.createPhenomenon();
            phenomenon.setName(dto.phenomenonName);
            entity.setPhenomenon(phenomenon);
        }

        if (dto.defaultAgendaId != null) {
            Agenda defaultAgenda = AgendaFactory.createAgenda();
            entity.setDefaultAgenda(defaultAgenda);
        }

        entity.clearAgendas();
        if (dto.agendaIds != null) {
            dto.agendaIds.forEach(id -> {
                Agenda agenda = AgendaFactory.createAgenda();
                entity.addAgenda(agenda);
            });
        }

        entity.clearFavoriteAgendas();
        if (dto.favoriteAgendaIds != null) {
            dto.favoriteAgendaIds.forEach(id -> {
                Agenda agenda = AgendaFactory.createAgenda();
                entity.addFavoriteAgenda(agenda);
            });
        }
    }

}
