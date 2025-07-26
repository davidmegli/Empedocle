package it.unifi.ing.stlab.woodelements.api.dto;

public class ModifyActionDTO {
    public String sourceId;
    public String targetId;

    public ModifyActionDTO() {
    }

    public ModifyActionDTO(String sourceId, String targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }
}