package it.unifi.ing.stlab.football.api;

import it.unifi.ing.stlab.football.api.dto.PlayerDTO;
import it.unifi.ing.stlab.football.api.mapper.PlayerMapper;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.manager.PlayerManager;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.dao.player.PlayerDao;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import java.util.Date;
import it.unifi.ing.stlab.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import javax.ejb.EJB;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Tag(name = "Players", description = "Operations for managing players")
@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerController {

    @EJB
    private PlayerDao dao;

    //TODO: authentication by user not implemented
    private User getAuthor() {
        User author = dao.findUser(1L);
        if (author == null)
            throw new NotAuthorizedException("User not authenticated");
        return author;
    }
    @GET
    @Path("/{id}")
    @Operation(summary = "Get a player by ID", description = "Returns a single player by its external ID")
    @APIResponse(responseCode = "200", description = "Player found")
    @APIResponse(responseCode = "404", description = "Player not found")
    public PlayerDTO get(@PathParam("id") Long id) {
        Player element = dao.findById(id);
        if (element == null) throw new NotFoundException();
        return PlayerMapper.toDto(element);
    }

    @POST
    @Secured
    @Operation(summary = "Create a new player", description = "Creates and persists a new player")
    @APIResponse(responseCode = "201", description = "Player successfully created")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Identifier already exists")
    public Response create(PlayerDTO dto, @Context UriInfo uriInfo) {
        //Check if the identifier is not empty or already used
        String identifierCode = dto.identifierCode;

        if (identifierCode != null && !identifierCode.trim().isEmpty()) {
            if (dao.findByIdentifier(identifierCode) != null) {
                // The identifier code already exists
                return Response.status(Response.Status.CONFLICT)
                        .entity("Error: identifier code already exists.")
                        .build();
            }
        } else {
            // identifier code is null
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: You have to insert identifier code.")
                    .build();
        }
        //If the identifier code is new proceed with the creation
        User author = getAuthor(); //dummy author

        Player element = dao.create(author);
        PlayerMapper.updateEntity(element, dto);
        dao.save(element);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(element.getId().toString());
        return Response.created(builder.build())
                .entity(PlayerMapper.toDto(element))
                .build();
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Operation(summary = "Update a player", description = "Updates the properties of an existing player")
    @APIResponse(responseCode = "200", description = "Player successfully updated")
    @APIResponse(responseCode = "404", description = "Player not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response update(@PathParam("id") Long id, PlayerDTO dto) {
        try {
            User author = getAuthor(); //dummy author

            Player element = dao.modifyById(id, author);
            if (element == null) throw new NotFoundException();

            PlayerMapper.updateEntity(element, dto);

            dao.update(element);

            return Response.ok(PlayerMapper.toDto(element)).build();
        } catch (javax.ejb.EJBException e) {

            Throwable cause = e.getCause();

            if (cause instanceof IllegalArgumentException) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(cause.getMessage())
                        .build();
            } else {
                throw e;
            }
        }
    }


    @DELETE
    @Secured
    @Path("/{id}")
    @Operation(summary = "Delete a player", description = "Deletes a player by its external ID")
    @APIResponse(responseCode = "204", description = "Player successfully deleted")
    @APIResponse(responseCode = "404", description = "Player not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response delete(@PathParam("id") Long id) {
        try {
            //Dummy author
            User author = getAuthor();

            Player element = dao.deleteById(id, author);
            if (element == null) throw new NotFoundException();

            return Response.noContent().build();
        } catch (javax.ejb.EJBException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(cause.getMessage())
                        .build();
            } else {
                throw e;
            }
        }
    }
}
