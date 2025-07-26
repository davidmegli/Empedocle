package it.unifi.ing.stlab.woodelements.api.dto;

public class ModifyActionDTO {
    public Long sourceId;
    public Long targetId;

    public ModifyActionDTO() {
    }

    public ModifyActionDTO(Long sourceId, Long targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }
}