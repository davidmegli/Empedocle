package it.unifi.ing.stlab.football.model.roster;

import it.unifi.ing.stlab.football.api.dto.RosterDTO;
import it.unifi.ing.stlab.football.api.mapper.RosterMapper;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.football.manager.RosterManager;
import it.unifi.ing.stlab.football.model.roster.Roster;
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

@Tag(name = "Rosters", description = "Operations for managing rosters")
@Path("/rosters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RosterController {

    @EJB
    //private ObservableEntityDao<WoodElement, WoodElementManager> dao;
    private RosterDao dao;

    //TODO: authentication by user not implemented
    private User getAuthor() {
        User author = dao.findUser(1L);
        if (author == null)
            throw new NotAuthorizedException("User not authenticated");
        return author;
    }
    @GET
    @Path("/{id}")
    @Operation(summary = "Get a roster by ID", description = "Returns a single roster by its ID")
    @APIResponse(responseCode = "200", description = "Roster found")
    @APIResponse(responseCode = "404", description = "Roster not found")
    public RosterDTO get(@PathParam("id") Long id) {
        Roster element = dao.findById(id);
        if (element == null) throw new NotFoundException();
        return RosterMapper.toDto(element);
    }

    @POST
    @Secured
    @Operation(summary = "Create a new roster", description = "Creates and persists a new roster")
    @APIResponse(responseCode = "201", description = "Roster successfully created")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Identifier already exists")
    public Response create(RosterDTO dto, @Context UriInfo uriInfo) {
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

        Roster element = dao.create(author);
        RosterMapper.updateEntity(element, dto);
        dao.save(element);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(element.getId().toString());
        return Response.created(builder.build())
                .entity(RosterMapper.toDto(element))
                .build();
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Operation(summary = "Update a roster", description = "Updates the properties of an existing roster")
    @APIResponse(responseCode = "200", description = "Roster successfully updated")
    @APIResponse(responseCode = "404", description = "Roster element not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response update(@PathParam("id") Long id, RosterDTO dto) {
        try {
            User author = getAuthor(); //dummy author

            Roster element = dao.modifyById(id, author);
            if (element == null) throw new NotFoundException();

            RosterMapper.updateEntity(element, dto);

            dao.update(element);

            return Response.ok(RosterMapper.toDto(element)).build();
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
    @Operation(summary = "Delete a roster", description = "Deletes a roster by its external ID")
    @APIResponse(responseCode = "204", description = "Roster successfully deleted")
    @APIResponse(responseCode = "404", description = "Roster not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response delete(@PathParam("id") Long id) {
        try {
            //Dummy author
            User author = getAuthor();

            Roster element = dao.deleteById(id, author);
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