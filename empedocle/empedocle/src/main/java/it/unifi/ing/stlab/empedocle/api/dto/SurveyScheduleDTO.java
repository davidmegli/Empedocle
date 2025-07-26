package it.unifi.ing.stlab.empedocle.api.dto;

import java.util.Date;
import java.util.Set;

public class SurveyScheduleDTO {

    public Long id;
    public String uuid;
    public Date date;
    public String number;
    public String bookingCode;
    public String acceptanceCode;
    public String status;

    public Long agendaId;
    public Long observableEntityId;
    public Set<Long> serviceIds;

    public SurveyScheduleDTO() {
    }

    public SurveyScheduleDTO(Long id, String uuid, Date date, String number, String bookingCode, String acceptanceCode, String status, Long agendaId, Long observableEntityId, Set<Long> serviceIds) {
        this.id = id;
        this.uuid = uuid;
        this.date = date;
        this.number = number;
        this.bookingCode = bookingCode;
        this.acceptanceCode = acceptanceCode;
        this.status = status;
        this.agendaId = agendaId;
    }
}