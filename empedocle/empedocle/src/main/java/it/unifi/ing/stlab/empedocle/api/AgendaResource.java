package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.AgendaDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.AgendaMapper;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/agendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendaResource {

    @Inject
    private AgendaDao agendaDao;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Agenda agenda = agendaDao.findById(id);
        if (agenda == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(AgendaMapper.toDto(agenda)).build();
    }

    @POST
    public Response create(AgendaDTO dto) {
        Agenda agenda = new Agenda(java.util.UUID.randomUUID().toString());

        MeasurementSessionType mst = new MeasurementSessionType();
        mst.setId(dto.measurementSessionTypeId);

        AgendaMapper.updateEntity(agenda, dto, mst);

        agendaDao.save(agenda);

        return Response.status(Response.Status.CREATED)
                .entity(AgendaMapper.toDto(agenda))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AgendaDTO dto) {
        Agenda agenda = agendaDao.findById(id);
        if (agenda == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        MeasurementSessionType mst = new MeasurementSessionType();
        mst.setId(dto.measurementSessionTypeId);

        AgendaMapper.updateEntity(agenda, dto, mst);

        agendaDao.update(agenda);

        return Response.ok(AgendaMapper.toDto(agenda)).build();
    }
}
