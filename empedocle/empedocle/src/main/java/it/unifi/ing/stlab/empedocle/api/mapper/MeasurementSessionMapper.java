package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.MeasurementSessionDTO;
import it.unifi.ing.stlab.empedocle.model.*;
import it.unifi.ing.stlab.users.model.User;

public class MeasurementSessionMapper {

    public static MeasurementSessionDTO toDto(MeasurementSession entity) {
        if (entity == null) return null;

        MeasurementSessionDTO dto = new MeasurementSessionDTO();
        dto.id = entity.getId();
        dto.uuid = entity.getUuid();
        dto.status = entity.getStatus() != null ? entity.getStatus().name() : null;
        dto.wasDone = entity.getWasDone();
        dto.lastUpdate = entity.getLastUpdate();
        dto.surveyScheduleId = entity.getSurveySchedule() != null ? entity.getSurveySchedule().getId() : null;
        dto.typeId = entity.getType() != null ? entity.getType().getId() : null;
        dto.authorId = entity.getAuthor() != null ? entity.getAuthor().getId() : null;

        return dto;
    }

    public static void updateEntity(MeasurementSession entity, MeasurementSessionDTO dto,
                                    SurveySchedule schedule, MeasurementSessionType type, User author) {
        entity.setWasDone(dto.wasDone);
        entity.setLastUpdate(dto.lastUpdate);
        entity.setStatus(dto.status != null ? MeasurementSessionStatus.valueOf(dto.status) : null);
        entity.setSurveySchedule(schedule);
        entity.setType(type);
        entity.setAuthor(author);
    }
}
