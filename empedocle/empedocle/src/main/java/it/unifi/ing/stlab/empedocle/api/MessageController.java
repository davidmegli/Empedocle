package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.MessageDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.MessageMapper;
import it.unifi.ing.stlab.empedocle.dao.messages.MessageDao;
import it.unifi.ing.stlab.empedocle.model.messages.Message;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.empedocle.factory.MessageFactory;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

// OpenAPI
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageController {

    @EJB
    private MessageDao messageDao;

    //@EJB(beanName = "ObservableEntityDaoBean")
    //private ObservableEntityDao observableEntityDao;
    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Path("/{id}")
    @Operation(summary = "Get message by ID", description = "Returns the message identified by the given ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Message found",
                    content = @Content(schema = @Schema(implementation = MessageDTO.class))),
            @APIResponse(responseCode = "404", description = "Message not found")
    })
    public Response getById(
            @Parameter(description = "ID of the message", required = true)
            @PathParam("id") Long id) {

        Message message = messageDao.findById(id);
        if (message == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(MessageMapper.toDto(message)).build();
    }

    @POST
    @Operation(summary = "Create a new message", description = "Creates a new message and associates it with an existing observable entity.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Message created",
                    content = @Content(schema = @Schema(implementation = MessageDTO.class))),
            @APIResponse(responseCode = "400", description = "Invalid observable entity ID")
    })
    public Response create(
            @Parameter(description = "DTO representing the message to create", required = true)
            MessageDTO dto) {

        //ObservableEntity observable = observableEntityDao.findById(dto.observableEntityId);
        ObservableEntity observable = entityManager.find(ObservableEntity.class, dto.observableEntityId);

        if (observable == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ObservableEntity with ID " + dto.observableEntityId + " not found")
                    .build();
        }

        Message message = MessageFactory.createMessage();
        MessageMapper.updateEntity(message, dto, observable);
        messageDao.save(message);

        return Response.status(Response.Status.CREATED)
                .entity(MessageMapper.toDto(message))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a message", description = "Updates the message identified by the given ID with new data.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Message updated",
                    content = @Content(schema = @Schema(implementation = MessageDTO.class))),
            @APIResponse(responseCode = "404", description = "Message not found")
    })
    public Response update(
            @Parameter(description = "ID of the message to update", required = true)
            @PathParam("id") Long id,
            @Parameter(description = "DTO with updated message data", required = true)
            MessageDTO dto) {

        Message message = messageDao.findById(id);
        if (message == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ObservableEntity observable = message.getObservableEntity();
        MessageMapper.updateEntity(message, dto, observable);
        messageDao.update(message);

        return Response.ok(MessageMapper.toDto(message)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary="Delete message", description= "Deleted the message identified by the given ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Message deleted successfully"),
            @APIResponse(responseCode = "404", description = "Message not found")
    })
    public Response delete(@Parameter(description = "ID of the message to delete", required = true)
                           @PathParam("id") Long id) {
        Message message= messageDao.findById(id);
        if (message == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        messageDao.delete(id);
        return Response.noContent().build();
    }
}
