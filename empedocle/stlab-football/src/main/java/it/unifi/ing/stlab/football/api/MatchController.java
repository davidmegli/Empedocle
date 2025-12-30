package it.unifi.ing.stlab.football.api;

import it.unifi.ing.stlab.football.api.dto.MatchDTO;
import it.unifi.ing.stlab.football.api.mapper.MatchMapper;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.manager.MatchManager;
import it.unifi.ing.stlab.football.model.match.Match;
import it.unifi.ing.stlab.football.model.roster.Roster;
import it.unifi.ing.stlab.football.dao.match.MatchDao;
import it.unifi.ing.stlab.football.dao.roster.RosterDao;
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

@Tag(name = "Matches", description = "Operations for managing matches")
@Path("/matches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatchController {

    @EJB
    private MatchDao dao;
    @EJB
    private RosterDao rosterDao;

    //TODO: authentication by user not implemented
    private User getAuthor() {
        User author = dao.findUser(1L);
        if (author == null)
            throw new NotAuthorizedException("User not authenticated");
        return author;
    }
    @GET
    @Path("/{id}")
    @Operation(summary = "Get a match by ID", description = "Returns a single match by its external ID")
    @APIResponse(responseCode = "200", description = "Match found")
    @APIResponse(responseCode = "404", description = "Match not found")
    public MatchDTO get(@PathParam("id") Long id) {
        Match element = dao.findById(id);
        if (element == null) throw new NotFoundException();
        return MatchMapper.toDto(element);
    }

    @POST
    @Secured
    @Operation(summary = "Create a new match", description = "Creates and persists a new match")
    @APIResponse(responseCode = "201", description = "Match successfully created")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Identifier already exists")
    public Response create(MatchDTO dto, @Context UriInfo uriInfo) {
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

        Roster homeTeam = rosterDao.findById(dto.homeTeamId);
        if (homeTeam == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Roster with ID " + dto.homeTeamId + " not found")
                    .build();
        }
        Roster awayTeam = rosterDao.findById(dto.awayTeamId);
        if (awayTeam == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Roster with ID " + dto.awayTeamId + " not found")
                    .build();
        }

        Match element = dao.create(author);
        MatchMapper.updateEntity(element, dto, homeTeam, awayTeam);
        dao.save(element);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(element.getId().toString());
        return Response.created(builder.build())
                .entity(MatchMapper.toDto(element))
                .build();
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Operation(summary = "Update a match", description = "Updates the properties of an existing match")
    @APIResponse(responseCode = "200", description = "Match successfully updated")
    @APIResponse(responseCode = "404", description = "Match not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response update(@PathParam("id") Long id, MatchDTO dto) {
        try {
            User author = getAuthor(); //dummy author

            Match element = dao.modifyById(id, author);
            if (element == null) throw new NotFoundException();

            Roster homeTeam = rosterDao.findById(dto.homeTeamId);
            if (homeTeam == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Roster with ID " + dto.homeTeamId + " not found")
                        .build();
            }
            Roster awayTeam = rosterDao.findById(dto.awayTeamId);
            if (awayTeam == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Roster with ID " + dto.awayTeamId + " not found")
                        .build();
            }

            MatchMapper.updateEntity(element, dto, homeTeam, awayTeam);

            dao.update(element);

            return Response.ok(MatchMapper.toDto(element)).build();
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
    @Operation(summary = "Delete a match", description = "Deletes a match by its external ID")
    @APIResponse(responseCode = "204", description = "Match successfully deleted")
    @APIResponse(responseCode = "404", description = "Match not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response delete(@PathParam("id") Long id) {
        try {
            //Dummy author
            User author = getAuthor();

            Match element = dao.deleteById(id, author);
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
