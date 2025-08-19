package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.FactDTO;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;

import java.util.stream.Collectors;

public class FactMapper {

    public static FactDTO toDTO(FactImpl fact) {
        if (fact == null) return null;

        FactDTO dto = new FactDTO();
        dto.id = fact.getId();
        dto.uuid = fact.getUuid();
        dto.status = fact.getStatus();
        dto.typeId = fact.getType() != null ? fact.getType().getId() : null;
        dto.contextId = fact.getContext() != null ? fact.getContext().getId() : null;

        // Children
        dto.childrenIds = fact.listChildren().stream()
                .map(FactLinkImpl::getId) // assumendo che FactLinkImpl abbia getId()
                .collect(Collectors.toList());

        // Before & After
        dto.beforeIds = fact.listBefore().stream()
                .map(FactImpl::getId)
                .collect(Collectors.toList());
        dto.afterIds = fact.listAfter().stream()
                .map(FactImpl::getId)
                .collect(Collectors.toList());

        return dto;
    }

    // Per semplicità, DTO -> Entity solo assegnazione di ID e status
    public static void updateEntity(FactImpl fact, FactDTO dto) {
        if (fact == null || dto == null) return;

        fact.setStatus(dto.status);
        // Tipo e context vanno gestiti tramite DAO nel service/resource
    }
}

