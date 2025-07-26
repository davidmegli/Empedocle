package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.AgendaDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.AgendaMapper;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionTypeFactory;

// Jakarta REST (JAX-RS)
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// Jakarta Dependency Injection
import jakarta.inject.Inject;


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
        Agenda agenda = AgendaFactory.createAgenda();
        MeasurementSessionType mst = MeasurementSessionTypeFactory.createMeasurementSessionType();
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
        MeasurementSessionType mst = agenda.getMeasurementSessionType();
        AgendaMapper.updateEntity(agenda, dto, mst);
        agendaDao.update(agenda);
        return Response.ok(AgendaMapper.toDto(agenda)).build();
    }
}
