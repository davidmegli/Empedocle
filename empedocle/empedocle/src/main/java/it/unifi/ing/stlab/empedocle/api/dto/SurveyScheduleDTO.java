package it.unifi.ing.stlab.empedocle.api.dto;

import java.util.Date;
import java.util.Set;

public class SurveyScheduleDTO {

    private Long id;
    private String uuid;
    private Date date;
    private String number;
    private String bookingCode;
    private String acceptanceCode;
    private String status;

    private Long agendaId;
    private Long observableEntityId;
    private Set<Long> serviceIds;

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