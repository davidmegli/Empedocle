package it.unifi.ing.stlab.woodelements.api;

import it.unifi.ing.stlab.woodelements.api.dto.WoodElementDTO;
import it.unifi.ing.stlab.woodelements.api.mapper.WoodElementMapper;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDaoBean;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.users.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import javax.ejb.EJB;
import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Tag(name = "Wood Elements", description = "Operations for managing wood elements")
@Path("/woodelements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WoodElementResource {

    @EJB
    private ObservableEntityDao<WoodElement, WoodElementManager> dao;

    @EJB
    private WoodElementFactory factory;

    @Inject // CDI bean
    private User user;

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a wood element by ID", description = "Returns a single wood element by its external ID")
    @APIResponse(responseCode = "200", description = "Wood element found")
    @APIResponse(responseCode = "404", description = "Wood element not found")
    public WoodElementDTO get(@PathParam("id") Long id) {
        WoodElement element = dao.findById(id);
        if (element == null) throw new NotFoundException();
        return WoodElementMapper.toDto(element);
    }

    @POST
    @Operation(summary = "Create a new wood element", description = "Creates and persists a new wood element")
    @APIResponse(responseCode = "201", description = "Wood element successfully created")
    public Response create(WoodElementDTO dto, @Context UriInfo uriInfo) {
        User author = user;
        if (author == null) throw new NotAuthorizedException("User not authenticated");

        WoodElement element = factory.create();
        WoodElementMapper.updateEntity(element, dto);
        dao.save(element);  // Can also pass the author if necessary: dao.save(element, author);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(dto.id.toString());
        return Response.created(builder.build())
                .entity(WoodElementMapper.toDto(element))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a wood element", description = "Updates the properties of an existing wood element")
    @APIResponse(responseCode = "200", description = "Wood element successfully updated")
    @APIResponse(responseCode = "404", description = "Wood element not found")
    public Response update(@PathParam("id") Long id, WoodElementDTO dto) {
        //User author = loggedUser.getUser();
        User author = user;
        if (author == null) throw new NotAuthorizedException("User not authenticated");

        WoodElement element = dao.findById(id);
        if (element == null) throw new NotFoundException();

        WoodElementMapper.updateEntity(element, dto);
        dao.update(element);  // author could also be passed

        return Response.ok(WoodElementMapper.toDto(element)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a wood element", description = "Deletes a wood element by its external ID")
    @APIResponse(responseCode = "204", description = "Wood element successfully deleted")
    @APIResponse(responseCode = "404", description = "Wood element not found")
    public Response delete(@PathParam("id") Long id) {
        User author = user;
        if (author == null) throw new NotAuthorizedException("User not authenticated");

        WoodElement element = dao.findById(id);
        if (element == null) throw new NotFoundException();

        dao.deleteById(id, author);

        return Response.noContent().build();
    }

    @GET
    @Path("/test")
    public Response test() {
        User author = user;
        String message = (user != null)
                ? "Test OK - Logged in as " + user.getName()
                : "Test OK - No user";

        return Response.ok(message).build();
    }
}
