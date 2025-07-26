package it.unifi.ing.stlab.empedocle.api.dto;

import java.util.Date;

public class MeasurementSessionDTO {

    public Long id;
    public String uuid;
    public String status;
    public Boolean wasDone;
    public Date lastUpdate;

    public Long surveyScheduleId;
    public Long typeId;
    public Long authorId;

    public MeasurementSessionDTO() {
    }

    public MeasurementSessionDTO(Long id, String uuid, String status, Boolean wasDone, Date lastUpdate, Long surveyScheduleId, Long typeId, Long authorId) {
        this.id = id;
        this.uuid = uuid;
        this.status = status;
        this.wasDone = wasDone;
        this.lastUpdate = lastUpdate;
        this.surveyScheduleId = surveyScheduleId;
        this.typeId = typeId;
    }
}
