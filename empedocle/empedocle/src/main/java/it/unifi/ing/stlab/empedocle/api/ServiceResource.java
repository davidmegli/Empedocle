package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.ServiceDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.ServiceMapper;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;

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

import java.util.UUID;

@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceResource {

    @EJB
    private ServiceDao serviceDao;

    @EJB
    private AgendaDao agendaDao;

    @GET
    @Path("/{id}")
    @Operation(summary = "Get service by ID", description = "Returns a service entity identified by the given ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Service found",
                    content = @Content(schema = @Schema(implementation = ServiceDTO.class))),
            @APIResponse(responseCode = "404", description = "Service not found")
    })
    public Response getById(
            @Parameter(description = "ID of the service to retrieve", required = true)
            @PathParam("id") Long id) {

        Service entity = serviceDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ServiceMapper.toDto(entity)).build();
    }

    @POST
    @Operation(summary = "Create a new service", description = "Creates a new service and returns the created object.")
    @APIResponse(responseCode = "201", description = "Service created",
            content = @Content(schema = @Schema(implementation = ServiceDTO.class)))
    public Response create(
            @Parameter(description = "DTO representing the service to be created", required = true)
            ServiceDTO dto) {

        Agenda agenda = agendaDao.findById(dto.agendaId);
        if (agenda == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Agenda with ID " + dto.agendaId + " not found")
                    .build();
        }

        Service entity = ServiceFactory.createService();
        ServiceMapper.updateEntity(entity, dto, agenda);

        return Response.status(Response.Status.CREATED)
                .entity(ServiceMapper.toDto(entity))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a service", description = "Updates an existing service with new data.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Service updated",
                    content = @Content(schema = @Schema(implementation = ServiceDTO.class))),
            @APIResponse(responseCode = "404", description = "Service not found")
    })
    public Response update(
            @Parameter(description = "ID of the service to update", required = true)
            @PathParam("id") Long id,
            @Parameter(description = "DTO with updated service data", required = true)
            ServiceDTO dto) {

        Service entity = serviceDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Agenda agenda = entity.getAgenda();
        ServiceMapper.updateEntity(entity, dto, agenda);

        return Response.ok(ServiceMapper.toDto(entity)).build();
    }
}
