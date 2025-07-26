package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.MeasurementSessionDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.MeasurementSessionMapper;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionQueryBuilder;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.users.model.User;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



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
    public Response getById(@PathParam("id") Long id) {
        MeasurementSession entity = measurementSessionDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(MeasurementSessionMapper.toDto(entity)).build();
    }

    @GET
    public Response list(@QueryParam("offset") @DefaultValue("0") int offset,
                         @QueryParam("limit") @DefaultValue("20") int limit) {
        List<MeasurementSession> list = measurementSessionDao.find(MeasurementSessionQueryBuilder, offset, limit);
        List<MeasurementSessionDTO> dtos = list.stream()
                .map(MeasurementSessionMapper::toDto)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @POST
    public Response create(MeasurementSessionDTO dto) {
        MeasurementSession entity = new MeasurementSession(UUID.randomUUID().toString());

        SurveySchedule schedule = SurveyScheduleFactory.createSurveySchedule();
        //schedule.setId(dto.id());
        MeasurementSessionType type = measurementSessionTypeDao.findById(dto.typeId);
//        User author = new User();
//        author.setId(dto.authorId);

        MeasurementSessionMapper.updateEntity(entity, dto, schedule, type, null);
        measurementSessionDao.save(entity);

        return Response.status(Response.Status.CREATED).entity(MeasurementSessionMapper.toDto(entity)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MeasurementSessionDTO dto) {
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
    public Response delete(@PathParam("id") Long id) {
        measurementSessionDao.deleteById(id);
        return Response.noContent().build();
    }
}
