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

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;
import java.util.stream.Collectors;

@Path("/survey-schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SurveyScheduleResource {

    @Inject
    private SurveyScheduleDao surveyScheduleDao;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        SurveySchedule entity = findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(SurveyScheduleMapper.toDto(entity)).build();
    }

    @POST
    public Response create(SurveyScheduleDTO dto) {
        SurveySchedule entity = new SurveySchedule(UUID.randomUUID().toString());

        Agenda agenda = AgendaFactory.createAgenda();
        //agenda.setId(dto.agendaId);

        WoodElementManager manager = new WoodElementManager();
        ObservableEntity observableEntity = manager.getFactory().create();
        observableEntity.setId(dto.observableEntityId);

        Set<Service> services = buildServicesFromIds(dto.serviceIds);

        SurveyScheduleMapper.updateEntity(entity, dto, agenda, observableEntity, services);
        surveyScheduleDao.update(entity); // oppure save(entity)

        return Response.status(Response.Status.CREATED)
                .entity(SurveyScheduleMapper.toDto(entity))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, SurveyScheduleDTO dto) {
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
            //s.setId(id);
            return s;
        }).collect(Collectors.toSet());
    }
}
