package it.unifi.ing.stlab.woodelements.api;

import it.unifi.ing.stlab.woodelements.api.dto.WoodElementDTO;
import it.unifi.ing.stlab.woodelements.api.mapper.WoodElementMapper;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDaoBean;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.factory.WoodElementActionFactory;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
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

@Tag(name = "Wood Elements", description = "Operations for managing wood elements")
@Path("/woodelements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WoodElementResource {

    @EJB
    private ObservableEntityDao<WoodElement, WoodElementManager> dao;

    @EJB
    private WoodElementFactory factory;

    @EJB
    private WoodElementActionFactory actionFactory;

    @EJB
    private WoodElementManager manager;
    //TODO: autenticazione non impelmentata, author fittizio in db
    private User getAuthor() {
        User author = dao.findUser(1L);
        if (author == null)
            throw new NotAuthorizedException("User not authenticated");
        return author;
    }
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
    @Secured
    @Operation(summary = "Create a new wood element", description = "Creates and persists a new wood element")
    @APIResponse(responseCode = "201", description = "Wood element successfully created")
    @APIResponse(responseCode = "401", description = "User not authorized")
    public Response create(WoodElementDTO dto, @Context UriInfo uriInfo) {
        //dummy author
        User author = getAuthor();

        WoodElement element = dao.create(author);
        WoodElementMapper.updateEntity(element, dto);
        dao.save(element);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(dto.id.toString());
        return Response.created(builder.build())
                .entity(WoodElementMapper.toDto(element))
                .build();
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Operation(summary = "Update a wood element", description = "Updates the properties of an existing wood element")
    @APIResponse(responseCode = "200", description = "Wood element successfully updated")
    @APIResponse(responseCode = "404", description = "Wood element not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    public Response update(@PathParam("id") Long id, WoodElementDTO dto) {
        //dummy author
        User author = getAuthor();

        WoodElement element = dao.modifyById(id,author);
        if (element == null) throw new NotFoundException();

        WoodElementMapper.updateEntity(element, dto);

        dao.save(element);

        return Response.ok(WoodElementMapper.toDto(element)).build();
    }

    @DELETE
    @Secured
    @Path("/{id}")
    @Operation(summary = "Delete a wood element", description = "Deletes a wood element by its external ID")
    @APIResponse(responseCode = "204", description = "Wood element successfully deleted")
    @APIResponse(responseCode = "404", description = "Wood element not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    public Response delete(@PathParam("id") Long id) {
        //Dummy author
        User author = getAuthor();

        WoodElement element = dao.deleteById(id, author);
        if (element == null) throw new NotFoundException();

        return Response.noContent().build();
    }
}
