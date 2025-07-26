package it.unifi.ing.stlab.woodelements.api.dto;

public class MergeActionDTO {
    public Long targetId;
    public Long source1Id;
    public Long source2Id;
    public MergeActionDTO() {
    }
    public MergeActionDTO(Long targetId, Long source1Id, Long source2Id) {
        this.targetId = targetId;
        this.source1Id = source1Id;
        this.source2Id = source2Id;
    }
}