package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.api.dto.StaffDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.StaffMapper;
import it.unifi.ing.stlab.empedocle.model.Staff;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.ejb.EJB;

@Path("/staff")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaffResource {

    @EJB
    private StaffDao staffDao;

    @GET
    @Path("/{id}")
    @Operation(summary = "Find staff by ID", description = "Fetches a staff member including their agendas, roles, and qualifications.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Staff found",
                    content = @Content(schema = @Schema(implementation = StaffDTO.class))),
            @APIResponse(responseCode = "404", description = "Staff not found")
    })
    public Response findById(
            @Parameter(description = "ID of the staff member", required = true)
            @PathParam("id") Long id) {

        Staff staff = staffDao.fetchById(id);
        if (staff == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(StaffMapper.toDto(staff)).build();
    }

    @POST
    @Operation(summary = "Create a new staff member", description = "Creates and persists a new staff member.")
    @APIResponse(responseCode = "201", description = "Staff created",
            content = @Content(schema = @Schema(implementation = StaffDTO.class)))
    public Response create(
            @Parameter(description = "DTO representing the staff to be created", required = true)
            StaffDTO dto) {

        Staff entity = StaffMapper.toEntity(dto);
        staffDao.save(entity);

        return Response.status(Response.Status.CREATED)
                .entity(StaffMapper.toDto(entity))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing staff member", description = "Updates the staff member identified by the given ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Staff updated",
                    content = @Content(schema = @Schema(implementation = StaffDTO.class))),
            @APIResponse(responseCode = "404", description = "Staff not found")
    })
    public Response update(
            @Parameter(description = "ID of the staff to update", required = true)
            @PathParam("id") Long id,
            @Parameter(description = "DTO with updated staff data", required = true)
            StaffDTO dto) {

        Staff entity = staffDao.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        StaffMapper.updateEntity(entity, dto);
        staffDao.update(entity);

        return Response.ok(StaffMapper.toDto(entity)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a staff member", description = "Deletes the staff member identified by the given ID if no foreign key constraints exist.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Staff deleted"),
            @APIResponse(responseCode = "400", description = "Deletion not allowed due to existing references"),
            @APIResponse(responseCode = "404", description = "Staff not found")
    })
    public Response delete(
            @Parameter(description = "ID of the staff to delete", required = true)
            @PathParam("id") Long id) {

        Staff staff = staffDao.findById(id);
        if (staff == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (staffDao.checkForeignKeyRestrictions(staff)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Staff cannot be deleted due to existing references.")
                    .build();
        }

        staffDao.delete(id);
        return Response.noContent().build();
    }
}
