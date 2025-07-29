package it.unifi.ing.stlab.empedocle.api.dto;

import java.util.Set;

public class StaffDTO {
    public Long id;
    public String uuid;
    public String number;
    public Long userId;
    public String username;
    public String phenomenonUuid;
    public String phenomenonName;
    public Long defaultAgendaId;
    public Set<Long> agendaIds;
    public Set<Long> favoriteAgendaIds;

    public StaffDTO() {
    }

    public StaffDTO(Long id, String uuid, String number, Long userId, String username, String phenomenonUuid, String phenomenonName, Long defaultAgendaId, Set<Long> agendaIds, Set<Long> favoriteAgendaIds) {
        this.id = id;
        this.uuid = uuid;
        this.number = number;
        this.userId = userId;
        this.username = username;
        this.phenomenonUuid = phenomenonUuid;
        this.phenomenonName = phenomenonName;
    }
}
