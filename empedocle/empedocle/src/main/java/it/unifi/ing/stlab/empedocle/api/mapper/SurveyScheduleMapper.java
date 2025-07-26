package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.SurveyScheduleDTO;
import it.unifi.ing.stlab.empedocle.model.health.*;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;


import java.util.Set;
import java.util.stream.Collectors;

public class SurveyScheduleMapper {

    public static SurveyScheduleDTO toDto(SurveySchedule entity) {
        if (entity == null) return null;

        SurveyScheduleDTO dto = new SurveyScheduleDTO();
        dto.id = entity.getId();
        dto.uuid = entity.getUuid();
        dto.date = entity.getDate();
        dto.number = entity.getNumber();
        dto.bookingCode = entity.getBookingCode();
        dto.acceptanceCode = entity.getAcceptanceCode();
        dto.status = entity.getStatus() != null ? entity.getStatus().name() : null;

        dto.agendaId = entity.getAgenda() != null ? entity.getAgenda().getId() : null;
        dto.observableEntityId = entity.getObservableEntity() != null ? entity.getObservableEntity().getId() : null;
        dto.serviceIds = entity.listServices().stream()
                .map(Service::getId)
                .collect(Collectors.toSet());

        return dto;
    }

    public static void updateEntity(SurveySchedule entity, SurveyScheduleDTO dto,
                                    Agenda agenda, ObservableEntity observableEntity, Set<Service> services) {
        entity.setDate(dto.date);
        entity.setNumber(dto.number);
        entity.setBookingCode(dto.bookingCode);
        entity.setAcceptanceCode(dto.acceptanceCode);
        entity.setStatus(dto.status != null ? SurveyScheduleStatus.valueOf(dto.status) : null);
        entity.setAgenda(agenda);
        entity.setObservableEntity(observableEntity);

        entity.clearServices();
        if (services != null) {
            for (Service s : services) {
                entity.addService(s);
            }
        }
    }
}
