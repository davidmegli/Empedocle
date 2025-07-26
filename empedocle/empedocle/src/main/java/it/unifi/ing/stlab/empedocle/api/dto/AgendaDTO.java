package it.unifi.ing.stlab.empedocle.api.dto;

public class AgendaDTO {

    public Long id;
    public String uuid;
    public String code;
    public String description;
    public Long measurementSessionTypeId;
    public AgendaDTO() {
    }

    public AgendaDTO(Long id, String uuid, String code, String description, Long measurementSessionTypeId) {
        this.id = id;
        this.uuid = uuid;
        this.code = code;
        this.description = description;
        this.measurementSessionTypeId = measurementSessionTypeId;
    }
}
