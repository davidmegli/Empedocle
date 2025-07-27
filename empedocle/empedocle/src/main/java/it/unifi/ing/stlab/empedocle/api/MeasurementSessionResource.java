package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.MeasurementSessionDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.MeasurementSessionMapper;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionTypeFactory;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionQueryBuilder;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

// OpenAPI
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Path("/measurement-sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeasurementSessionResource {

    @Inject
    private MeasurementSessionDao measurementSessionDao;

    @Inject
    private MeasurementSessionTypeDao measurementSessionTypeDao;

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a measurement session by ID", description = "Returns the measurement session identified by the given ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Measurement session found",
                    content = @Content(schema = @Schema(implementation = MeasurementSessionDTO.class))),
            @APIResponse(responseCode = "404", description = "Measurement session not found")
    })
    public Response getById(
            @Parameter(description = "ID of the measurement session", required = true)
            @PathParam("id") Long id) {

        MeasurementSession entity = measurementSessionDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(MeasurementSessionMapper.toDto(entity)).build();
    }

    @POST
    @Operation(summary = "Create a new measurement session", description = "Creates and persists a new measurement session based on the provided DTO.")
    @APIResponse(responseCode = "201", description = "Measurement session created",
            content = @Content(schema = @Schema(implementation = MeasurementSessionDTO.class)))
    public Response create(
            @Parameter(description = "DTO with the data to create the measurement session", required = true)
            MeasurementSessionDTO dto) {

        MeasurementSession entity = new MeasurementSession(UUID.randomUUID().toString());

        SurveySchedule schedule = SurveyScheduleFactory.createSurveySchedule();
        MeasurementSessionType type = MeasurementSessionTypeFactory.createMeasurementSessionType();

        MeasurementSessionMapper.updateEntity(entity, dto, schedule, type, null);
        measurementSessionDao.save(entity);

        return Response.status(Response.Status.CREATED).entity(MeasurementSessionMapper.toDto(entity)).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a measurement session", description = "Updates the measurement session identified by the given ID with new data.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Measurement session updated",
                    content = @Content(schema = @Schema(implementation = MeasurementSessionDTO.class))),
            @APIResponse(responseCode = "404", description = "Measurement session not found")
    })
    public Response update(
            @Parameter(description = "ID of the measurement session to update", required = true)
            @PathParam("id") Long id,
            @Parameter(description = "DTO with updated values", required = true)
            MeasurementSessionDTO dto) {

        MeasurementSession entity = measurementSessionDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        SurveySchedule schedule = entity.getSurveySchedule();
        MeasurementSessionType type = entity.getType();

        MeasurementSessionMapper.updateEntity(entity, dto, schedule, type, null);
        measurementSessionDao.update(entity);

        return Response.ok(MeasurementSessionMapper.toDto(entity)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a measurement session", description = "Deletes the measurement session identified by the given ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Measurement session deleted successfully"),
            @APIResponse(responseCode = "404", description = "Measurement session not found")
    })
    public Response delete(
            @Parameter(description = "ID of the measurement session to delete", required = true)
            @PathParam("id") Long id) {
        MeasurementSession ms = measurementSessionDao.findById(id);
        if (ms == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        measurementSessionDao.deleteById(id);
        return Response.noContent().build();
    }
}
