package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.SurveyScheduleDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.SurveyScheduleMapper;
import it.unifi.ing.stlab.empedocle.dao.health.SurveyScheduleDao;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;

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
public class SurveyScheduleResource {

    @EJB
    private SurveyScheduleDao surveyScheduleDao;

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

        SurveySchedule entity = findById(id);
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

        SurveySchedule entity = new SurveySchedule(UUID.randomUUID().toString());

        Agenda agenda = AgendaFactory.createAgenda();

        WoodElementManager manager = new WoodElementManager();
        ObservableEntity observableEntity = manager.getFactory().create();

        Set<Service> services = buildServicesFromIds(dto.serviceIds);

        SurveyScheduleMapper.updateEntity(entity, dto, agenda, observableEntity, services);
        surveyScheduleDao.update(entity);

        return Response.status(Response.Status.CREATED)
                .entity(SurveyScheduleMapper.toDto(entity))
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

        SurveySchedule entity = findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Agenda agenda = entity.getAgenda();
        ObservableEntity observableEntity = entity.getObservableEntity();
        Set<Service> services = buildServicesFromIds(dto.serviceIds);

        SurveyScheduleMapper.updateEntity(entity, dto, agenda, observableEntity, services);
        surveyScheduleDao.update(entity);

        return Response.ok(SurveyScheduleMapper.toDto(entity)).build();
    }

    //
    // Helpers
    //
    private SurveySchedule findById(Long id) {
        Set<SurveySchedule> all = new HashSet<>(surveyScheduleDao.findByObservableEntities(Collections.emptySet()));
        return all.stream().filter(s -> Objects.equals(s.getId(), id)).findFirst().orElse(null);
    }

    private Set<Service> buildServicesFromIds(Set<Long> ids) {
        if (ids == null) return Collections.emptySet();
        return ids.stream().map(id -> {
            Service s = ServiceFactory.createService();
            return s;
        }).collect(Collectors.toSet());
    }
}
