package it.unifi.ing.stlab.empedocle.api.dto;

import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import java.util.List;

public class FactDTO {
    public Long id;
    public String uuid;
    public Long typeId;
    public Long contextId;
    public FactStatus status;

    public List<Long> childrenIds;
    public List<Long> beforeIds;
    public List<Long> afterIds;

    public FactDTO() {}
}