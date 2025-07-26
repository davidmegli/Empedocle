package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.ServiceDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.ServiceMapper;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.empedocle.model.Agenda;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceResource {

    @Inject
    private ServiceDao serviceDao;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Service entity = serviceDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ServiceMapper.toDto(entity)).build();
    }

    @POST
    public Response create(ServiceDTO dto) {
        Service entity = new Service(java.util.UUID.randomUUID().toString());

        Agenda agenda = new Agenda();
        agenda.setId(dto.agendaId);

        ServiceMapper.updateEntity(entity, dto, agenda);
        // Aggiungi persistenza se hai serviceDao.save(entity);

        return Response.status(Response.Status.CREATED)
                .entity(ServiceMapper.toDto(entity))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ServiceDTO dto) {
        Service entity = serviceDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Agenda agenda = new Agenda();
        agenda.setId(dto.agendaId);

        ServiceMapper.updateEntity(entity, dto, agenda);
        // Aggiungi persistenza se hai serviceDao.update(entity);

        return Response.ok(ServiceMapper.toDto(entity)).build();
    }
}
