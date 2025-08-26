package it.unifi.ing.stlab.empedocle.api.dto;

import java.util.Set;

public class TypeDTO {

        public Long id;
        public String uuid;
        public String name;
        public String description;
        public Boolean readOnly;
        public Boolean anonymous;
        public Boolean recurrent;

        // tipo specifico (Textual, Temporal, Queried, Quantitative, Qualitative)
        public String type;

        // attributi aggiuntivi per sottoclassi
        public String timeFormat;         // TemporalType
        public String queryDef;           // QueriedType
        public Set<Long> unitIds;         // QuantitativeType (solo ID delle UnitUse)
}
