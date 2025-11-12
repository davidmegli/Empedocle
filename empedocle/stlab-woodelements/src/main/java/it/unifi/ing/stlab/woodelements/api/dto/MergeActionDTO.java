package it.unifi.ing.stlab.woodelements.api.dto;

public class MergeActionDTO {
    public Long source1Id;
    public Long source2Id;
    public MergeActionDTO() {
    }
    public MergeActionDTO(Long source1Id, Long source2Id) {
        this.source1Id = source1Id;
        this.source2Id = source2Id;
    }
}