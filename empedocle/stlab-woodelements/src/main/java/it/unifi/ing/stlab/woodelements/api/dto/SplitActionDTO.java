package it.unifi.ing.stlab.woodelements.api.dto;

import java.util.List;

public class SplitActionDTO {
    public Long sourceId;
    public Long target1Id;
    public Long target2Id;

    public SplitActionDTO() {
    }

    public SplitActionDTO(Long sourceId, Long target1Id, Long target2Id) {
        this.sourceId = sourceId;
        this.target1Id = target1Id;
        this.target2Id = target2Id;
    }
}