package it.unifi.ing.stlab.empedocle.api.mapper;

import it.unifi.ing.stlab.empedocle.api.dto.TypeDTO;
import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.commons.util.TimeFormat;

public class TypeMapper {

    public static TypeDTO toDTO(Type type) {
        if (type == null) return null;

        TypeDTO dto = new TypeDTO();
        dto.id = type.getId();
        dto.uuid = type.getUuid();
        dto.name = type.getName();
        dto.description = type.getDescription();
        dto.readOnly = type.getReadOnly();
        dto.anonymous = type.getAnonymous();
        dto.recurrent = type.getRecurrent();

        if (type instanceof TextualType) {
            dto.type = "TEXTUAL";
        } else if (type instanceof TemporalType) {
            dto.type = "TEMPORAL";
            dto.timeFormat = ((TemporalType) type).getType() != null
                    ? ((TemporalType) type).getType().name()
                    : null;
        } else if (type instanceof QuantitativeType) {
            dto.type = "QUANTITATIVE";
            // qui potresti mappare anche le UnitUse in un dto separato
        } else if (type instanceof QueriedType) {
            dto.type = "QUERIED";
            dto.queryDef = ((QueriedType) type).getQueryDef();
        } else if (type instanceof QualitativeType) {
            dto.type = "QUALITATIVE";
        }

        return dto;
    }

    public static Type toEntity(TypeDTO dto) {
        if (dto == null) return null;

        Type entity;
        switch (dto.type) {
            case "TEXTUAL":
                entity = TypeFactory.createTextualType();
                break;
            case "TEMPORAL":
                entity = TypeFactory.createTemporalType();
                if (dto.timeFormat != null) {
                    ((TemporalType) entity).setType(
                            Enum.valueOf(TimeFormat.class, dto.timeFormat)
                    );
                }
                break;
            case "QUANTITATIVE":
                entity = TypeFactory.createQuantitativeType();
                break;
            case "QUERIED":
                entity = TypeFactory.createQueriedType();
                ((QueriedType) entity).setQueryDef(dto.queryDef);
                break;
            case "QUALITATIVE":
                entity = TypeFactory.createEnumeratedType();
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + dto.type);
        }

        entity.setName(dto.name);
        entity.setDescription(dto.description);
        entity.setReadOnly(dto.readOnly);
        entity.setAnonymous(dto.anonymous);
        entity.setRecurrent(dto.recurrent);

        return entity;
    }
}
