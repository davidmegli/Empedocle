package it.unifi.ing.stlab.empedocle.api.dto;

public class ServiceDTO {

    public Long id;
    public String uuid;
    public String code;
    public String description;
    public String internalCode;
    public Long agendaId;

    public ServiceDTO() {
    }

    public ServiceDTO(Long id, String uuid, String code, String description, String internalCode, Long agendaId) {
        this.id = id;
        this.uuid = uuid;
        this.code = code;
        this.description = description;
        this.internalCode = internalCode;
        this.agendaId = agendaId;
    }
}
