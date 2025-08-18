package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.AgendaDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.AgendaMapper;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/agendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendaResource {

    @EJB
    private AgendaDao agendaDao;

    @EJB
    private MeasurementSessionTypeDao measurementSessionTypeDao;

    @GET
    @Path("/{id}")
    @Operation(summary = "Get agenda by ID", description = "Returns a single agenda given its ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Agenda found",
                    content = @Content(schema = @Schema(implementation = AgendaDTO.class))),
            @APIResponse(responseCode = "404", description = "Agenda not found")
    })
    public Response getById(@Parameter(description = "ID of the agenda", required = true)
                            @PathParam("id") Long id) {
        Agenda agenda = agendaDao.findById(id);
        if (agenda == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(AgendaMapper.toDto(agenda)).build();
    }

    @POST
    @Operation(summary = "Create a new agenda", description = "Creates a new agenda and returns the created resource.")
    @APIResponse(responseCode = "201", description = "Agenda created",
            content = @Content(schema = @Schema(implementation = AgendaDTO.class)))
    public Response create(@Parameter(description = "DTO representing the agenda to create", required = true)
                           AgendaDTO dto) {
        MeasurementSessionType mst = measurementSessionTypeDao.findById(dto.measurementSessionTypeId);
        if (mst == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("MeasurementSessionType not found for id: " + dto.measurementSessionTypeId)
                    .build();
        }

        Agenda agenda = AgendaFactory.createAgenda();
        AgendaMapper.updateEntity(agenda, dto, mst);
        agendaDao.save(agenda);

        return Response.status(Response.Status.CREATED)
                .entity(AgendaMapper.toDto(agenda))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an agenda", description = "Updates an existing agenda given its ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Agenda updated",
                    content = @Content(schema = @Schema(implementation = AgendaDTO.class))),
            @APIResponse(responseCode = "404", description = "Agenda not found")
    })
    public Response update(@Parameter(description = "ID of the agenda to update", required = true)
                           @PathParam("id") Long id,
                           @Parameter(description = "DTO with updated values", required = true)
                           AgendaDTO dto) {
        Agenda agenda = agendaDao.findById(id);
        if (agenda == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        MeasurementSessionType mst = agenda.getMeasurementSessionType();
        if (mst == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("MeasurementSessionType not found for id: " + dto.measurementSessionTypeId)
                    .build();
        }

        AgendaMapper.updateEntity(agenda, dto, mst);
        agendaDao.update(agenda);
        return Response.ok(AgendaMapper.toDto(agenda)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete an agenda", description = "Deletes the agenda identified by the given ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Agenda deleted successfully"),
            @APIResponse(responseCode = "404", description = "Agenda not found")
    })
    public Response delete(@Parameter(description = "ID of the agenda to delete", required = true)
                           @PathParam("id") Long id) {
        Agenda agenda = agendaDao.findById(id);
        if (agenda == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        agendaDao.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/test")
    public Response test() {
        return Response.ok("Test OK").build();
    }
}
