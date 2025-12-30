package it.unifi.ing.stlab.football.api;

import it.unifi.ing.stlab.football.api.dto.ParticipationDTO;
import it.unifi.ing.stlab.football.api.mapper.ParticipationMapper;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.manager.ParticipationManager;
import it.unifi.ing.stlab.football.model.participation.Participation;
import it.unifi.ing.stlab.football.dao.participation.ParticipationDao;
import it.unifi.ing.stlab.football.dao.player.PlayerDao;
import it.unifi.ing.stlab.football.dao.match.MatchDao;
import it.unifi.ing.stlab.football.model.player.Player;
import it.unifi.ing.stlab.football.model.match.Match;

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

@Tag(name = "Participations", description = "Operations for managing participations")
@Path("/participations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParticipationController {

    @EJB
    private ParticipationDao dao;

    @EJB
    private PlayerDao playerDao;
    @EJB
    private MatchDao matchDao;

    //TODO: authentication by user not implemented
    private User getAuthor() {
        User author = dao.findUser(1L);
        if (author == null)
            throw new NotAuthorizedException("User not authenticated");
        return author;
    }
    @GET
    @Path("/{id}")
    @Operation(summary = "Get a participation by ID", description = "Returns a single participation by its ID")
    @APIResponse(responseCode = "200", description = "Participation found")
    @APIResponse(responseCode = "404", description = "Participation not found")
    public ParticipationDTO get(@PathParam("id") Long id) {
        Participation element = dao.findById(id);
        if (element == null) throw new NotFoundException();
        return ParticipationMapper.toDto(element);
    }

    @POST
    @Secured
    @Operation(summary = "Create a new participation", description = "Creates and persists a new participation")
    @APIResponse(responseCode = "201", description = "Participation successfully created")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Identifier already exists")
    public Response create(ParticipationDTO dto, @Context UriInfo uriInfo) {
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


        Player player = playerDao.findById(dto.playerId);
        if (player == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Player with ID " + dto.playerId + " not found")
                    .build();
        }

        Match match = matchDao.findById(dto.matchId);
        if (match == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Match with ID " + dto.matchId + " not found")
                    .build();
        }
        Participation element = dao.create(author);
        ParticipationMapper.updateEntity(element, dto, player, match );
        dao.save(element);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(element.getId().toString());
        return Response.created(builder.build())
                .entity(ParticipationMapper.toDto(element))
                .build();
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Operation(summary = "Update a participation", description = "Updates the properties of an existing participation")
    @APIResponse(responseCode = "200", description = "Participation successfully updated")
    @APIResponse(responseCode = "404", description = "Participation element not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response update(@PathParam("id") Long id, ParticipationDTO dto) {
        try {
            User author = getAuthor(); //dummy author

            Participation element = dao.modifyById(id, author);
            if (element == null) throw new NotFoundException();

            Player player = playerDao.findById(dto.playerId);
            if (player == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Player with ID " + dto.playerId + " not found")
                        .build();
            }

            Match match = matchDao.findById(dto.matchId);
            if (match == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Match with ID " + dto.matchId + " not found")
                        .build();
            }

            ParticipationMapper.updateEntity(element, dto, player, match);

            dao.update(element);

            return Response.ok(ParticipationMapper.toDto(element)).build();
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
    @Operation(summary = "Delete a participation", description = "Deletes a participation by its external ID")
    @APIResponse(responseCode = "204", description = "Participation successfully deleted")
    @APIResponse(responseCode = "404", description = "Participation not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response delete(@PathParam("id") Long id) {
        try {
            //Dummy author
            User author = getAuthor();

            Participation element = dao.deleteById(id, author);
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