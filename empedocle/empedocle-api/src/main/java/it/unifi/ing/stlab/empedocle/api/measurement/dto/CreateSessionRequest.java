package it.stlab.empedocle.api.measurement.dto;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import java.time.Instant;

public class CreateSessionRequest {
    public String name;
    public Instant timestamp;

    public MeasurementSession toEntity() {
        MeasurementSession ms = new MeasurementSession();
        ms.setName(name);
        ms.setTimestamp(timestamp != null ? timestamp : Instant.now());
        return ms;
    }
}
