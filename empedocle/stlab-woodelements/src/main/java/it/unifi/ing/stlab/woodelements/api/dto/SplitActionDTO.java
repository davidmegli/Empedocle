package it.unifi.ing.stlab.woodelements.api.dto;

import java.util.List;

public class SplitActionDTO {
    public String sourceId;
    public String target1Id;
    public String target2Id;

    public SplitActionDTO() {
    }

    public SplitActionDTO(String sourceId, String target1Id, String target2Id) {
        this.sourceId = sourceId;
        this.target1Id = target1Id;
        this.target2Id = target2Id;
    }
}