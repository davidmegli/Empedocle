package it.unifi.ing.stlab.woodelements.api;

import it.unifi.ing.stlab.woodelements.api.dto.WoodElementDTO;
import it.unifi.ing.stlab.woodelements.api.dto.MergeActionDTO;
import it.unifi.ing.stlab.woodelements.api.mapper.WoodElementMapper;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
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
public class WoodElementController {

    @EJB
    //private ObservableEntityDao<WoodElement, WoodElementManager> dao;
    private WoodElementDao dao;

    //TODO: authentication by user not implemented
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
    @APIResponse(responseCode = "409", description = "Identifier already exists")
    public Response create(WoodElementDTO dto, @Context UriInfo uriInfo) {
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

        WoodElement element = dao.create(author);
        WoodElementMapper.updateEntity(element, dto);
        dao.save(element);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(element.getId().toString());
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
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response update(@PathParam("id") Long id, WoodElementDTO dto) {
        try {
            User author = getAuthor(); //dummy author

            WoodElement element = dao.modifyById(id, author);
            if (element == null) throw new NotFoundException();

            WoodElementMapper.updateEntity(element, dto);

            dao.update(element);

            return Response.ok(WoodElementMapper.toDto(element)).build();
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
    @Operation(summary = "Delete a wood element", description = "Deletes a wood element by its external ID")
    @APIResponse(responseCode = "204", description = "Wood element successfully deleted")
    @APIResponse(responseCode = "404", description = "Wood element not found")
    @APIResponse(responseCode = "401", description = "User not authorized")
    @APIResponse(responseCode = "409", description = "Entity not active")
    public Response delete(@PathParam("id") Long id) {
       try {
            //Dummy author
            User author = getAuthor();

            WoodElement element = dao.deleteById(id, author);
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
    @POST
    @Secured
    @Path("/merge")
    @Operation(summary = "Merge two wood elements", description = "Merges two source wood elements into a single target element")
    @APIResponse(responseCode = "200", description = "Merge action completed successfully")
    public Response merge(MergeActionDTO dto, @Context UriInfo uriInfo) {
        //Dummy author
        User author = getAuthor();

        WoodElement t = dao.mergeById(dto.source1Id, dto.source2Id, author);
        if (t == null) throw new NotFoundException();

        dao.update(t);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(t.getId().toString());
        return Response.created(builder.build())
                .entity(WoodElementMapper.toDto(t))
                .build();
    }
}
