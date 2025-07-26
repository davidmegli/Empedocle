package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.MessageDTO;
import it.unifi.ing.stlab.empedocle.model.messages.Message;
import it.unifi.ing.stlab.empedocle.model.messages.MessageLevel;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

public class MessageMapper {

    public static MessageDTO toDto(Message entity) {
        if (entity == null) return null;

        MessageDTO dto = new MessageDTO();
        dto.id = entity.getId();
        dto.uuid = entity.getUuid();
        dto.date = entity.getDate();
        dto.sender = entity.getSender();
        dto.subject = entity.getSubject();
        dto.body = entity.getBody();
        dto.level = entity.getLevel() != null ? entity.getLevel().toString() : null;
        dto.isRead = entity.getIsRead();
        dto.observableEntityId = entity.getObservableEntity() != null ? entity.getObservableEntity().getId() : null;

        return dto;
    }

    public static void updateEntity(Message entity, MessageDTO dto, ObservableEntity observableEntity) {
        entity.setDate(dto.date);
        entity.setSender(dto.sender);
        entity.setSubject(dto.subject);
        entity.setBody(dto.body);
        entity.setIsRead(dto.isRead != null ? dto.isRead : false);
        entity.setObservableEntity(observableEntity);

        if (dto.level != null) {
            entity.setLevel(MessageLevel.valueOf(dto.level.toUpperCase()));
        } else {
            entity.setLevel(null);
        }
    }
}
