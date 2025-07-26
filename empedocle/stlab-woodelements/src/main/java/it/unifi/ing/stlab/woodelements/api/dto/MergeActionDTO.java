package it.unifi.ing.stlab.woodelements.api.dto;

public class MergeActionDTO {
    public String targetId;
    public String source1Id;
    public String source2Id;
    public MergeActionDTO() {
    }
    public MergeActionDTO(String targetId, String source1Id, String source2Id) {
        this.targetId = targetId;
        this.source1Id = source1Id;
        this.source2Id = source2Id;
    }
}