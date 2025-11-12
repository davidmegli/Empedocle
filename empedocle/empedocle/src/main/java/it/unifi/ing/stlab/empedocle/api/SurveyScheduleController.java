package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.SurveyScheduleDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.SurveyScheduleMapper;
import it.unifi.ing.stlab.empedocle.dao.health.SurveyScheduleDao;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.*;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/survey-schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SurveyScheduleController {

    @EJB
    private SurveyScheduleDao surveyScheduleDao;
    @EJB
    private ObservableEntityDao observableEntityDao;
    @EJB
    private AgendaDao agendaDao;
    @EJB
    private ServiceDao serviceDao;

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Retrieve a SurveySchedule by ID",
            description = "Fetches a specific SurveySchedule entity by its unique identifier."
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "SurveySchedule successfully retrieved",
                    content = @Content(schema = @Schema(implementation = SurveyScheduleDTO.class))
            ),
            @APIResponse(responseCode = "404", description = "SurveySchedule not found")
    })
    public Response getById(
            @Parameter(description = "The ID of the SurveySchedule", required = true)
            @PathParam("id") Long id) {

        SurveySchedule entity = surveyScheduleDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(SurveyScheduleMapper.toDto(entity)).build();
    }

    @POST
    @Operation(
            summary = "Create a new SurveySchedule",
            description = "Creates a new SurveySchedule and persists it in the system."
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "201",
                    description = "SurveySchedule successfully created",
                    content = @Content(schema = @Schema(implementation = SurveyScheduleDTO.class))
            ),
            @APIResponse(responseCode = "400", description = "Invalid input data")
    })
    public Response create(
            @Parameter(description = "DTO representing the SurveySchedule to be created", required = true)
            SurveyScheduleDTO dto) {
        Agenda agenda = agendaDao.findById(dto.agendaId);
        if (agenda == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Agenda with ID " + dto.agendaId + " not found")
                    .build();
        }
        ObservableEntity observableEntity = observableEntityDao.findById(dto.observableEntityId);
        if (observableEntity == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Observable Entity with ID " + dto.observableEntityId + " not found")
                    .build();
        }

        SurveySchedule entity = SurveyScheduleFactory.createSurveySchedule();
        Set<Service> services = findServicesFromIds(dto.serviceIds);

        SurveyScheduleMapper.updateEntity(entity, dto, agenda, observableEntity, services);
        SurveySchedule saved = surveyScheduleDao.save(entity);

        return Response.status(Response.Status.CREATED)
                .entity(SurveyScheduleMapper.toDto(saved))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update an existing SurveySchedule",
            description = "Updates an existing SurveySchedule entity with new values."
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "SurveySchedule successfully updated",
                    content = @Content(schema = @Schema(implementation = SurveyScheduleDTO.class))
            ),
            @APIResponse(responseCode = "404", description = "SurveySchedule not found")
    })
    public Response update(
            @Parameter(description = "The ID of the SurveySchedule to update", required = true)
            @PathParam("id") Long id,
            @Parameter(description = "Updated SurveySchedule data", required = true)
            SurveyScheduleDTO dto) {

        SurveySchedule entity = surveyScheduleDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Agenda agenda = entity.getAgenda();
        ObservableEntity observableEntity = entity.getObservableEntity();
        Set<Service> services = findServicesFromIds(dto.serviceIds);

        SurveyScheduleMapper.updateEntity(entity, dto, agenda, observableEntity, services);
        surveyScheduleDao.update(entity);

        return Response.ok(SurveyScheduleMapper.toDto(entity)).build();
    }
    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete an existing SurveySchedule",
            description = "Deletes a SurveySchedule entity by its ID."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "SurveySchedule successfully deleted"),
            @APIResponse(responseCode = "404", description = "SurveySchedule not found")
    })
    public Response delete(
            @Parameter(description = "The ID of the SurveySchedule to delete", required = true)
            @PathParam("id") Long id) {

        // Trova l'entità esistente
        SurveySchedule entity = surveyScheduleDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Rimuove l'entità dal database
        surveyScheduleDao.delete(id);

        // Restituisce 204 No Content
        return Response.noContent().build();
    }

    //
    // Helpers
    //
    private Set<Service> buildServicesFromIds(Set<Long> ids) {
        if (ids == null) return Collections.emptySet();
        return ids.stream().map(id -> {
            Service s = ServiceFactory.createService();
            return s;
        }).collect(Collectors.toSet());
    }
    private Set<Service> findServicesFromIds(Set<Long> ids) {
        if (ids == null) return Collections.emptySet();
        return ids.stream().map(id -> {
            Service s = serviceDao.findById(id);
            return s;
        }).collect(Collectors.toSet());
    }
}
