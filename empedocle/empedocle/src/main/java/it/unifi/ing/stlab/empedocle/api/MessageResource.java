package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.MessageDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.MessageMapper;
import it.unifi.ing.stlab.empedocle.dao.messages.MessageDao;
import it.unifi.ing.stlab.empedocle.model.messages.Message;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    private MessageDao messageDao;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Message message = messageDao.findById(id);
        if (message == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(MessageMapper.toDto(message)).build();
    }

    @POST
    public Response create(MessageDTO dto) {
        Message message = new Message(java.util.UUID.randomUUID().toString());

        ObservableEntity observable = new ObservableEntity();
        observable.setId(dto.observableEntityId);

        MessageMapper.updateEntity(message, dto, observable);

        messageDao.save(message);

        return Response.status(Response.Status.CREATED)
                .entity(MessageMapper.toDto(message))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MessageDTO dto) {
        Message message = messageDao.findById(id);
        if (message == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ObservableEntity observable = new ObservableEntity();
        observable.setId(dto.observableEntityId);

        MessageMapper.updateEntity(message, dto, observable);

        messageDao.update(message);

        return Response.ok(MessageMapper.toDto(message)).build();
    }
}
