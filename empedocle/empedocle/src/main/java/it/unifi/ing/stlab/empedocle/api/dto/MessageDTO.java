package it.unifi.ing.stlab.empedocle.api.dto;

import java.util.Date;

public class MessageDTO {

    public Long id;
    public String uuid;
    public Date date;
    public String sender;
    public String subject;
    public String body;
    public String level;
    public Boolean isRead;
    public Long observableEntityId;

    public MessageDTO() {
    }
    public MessageDTO(Long id, String uuid, Date date, String sender, String subject, String body, String level, Boolean isRead, Long observableEntityId) {
        this.id = id;
        this.uuid = uuid;
        this.date = date;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.level = level;
        this.isRead = isRead;
    }
}

