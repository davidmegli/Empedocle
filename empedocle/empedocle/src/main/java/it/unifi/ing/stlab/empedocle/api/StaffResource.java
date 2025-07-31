package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.empedocle.api.dto.StaffDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.StaffMapper;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.empedocle.factory.StaffFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/staff")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaffResource {

    @EJB private StaffDao staffDao;
    @EJB private AgendaDao agendaDao;
    @EJB private PhenomenonDao phenomenonDao;
    @EJB private UserDao userDao;

    @GET
    @Path("/{id}")
    @Operation(summary = "Find staff by ID", description = "Fetches a staff member including their agendas, roles, and qualifications.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Staff found",
                    content = @Content(schema = @Schema(implementation = StaffDTO.class))),
            @APIResponse(responseCode = "404", description = "Staff not found")
    })
    public Response findById(@PathParam("id") Long id) {
        Staff staff = staffDao.fetchById(id);
        if (staff == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(StaffMapper.toDto(staff)).build();
    }

    @POST
    @Operation(summary = "Create a new staff member")
    @APIResponse(responseCode = "201", description = "Staff created")
    public Response create(StaffDTO dto) {
        Staff staff = StaffFactory.createStaff();

        if (dto.userId != null) {
            User user = userDao.findById(dto.userId);
            staff.setUser(user);
        }

        if (dto.phenomenonUuid != null) {
            Phenomenon phenomenon = phenomenonDao.findByUuid(dto.phenomenonUuid);
            staff.setPhenomenon(phenomenon);
        }

        if (dto.defaultAgendaId != null) {
            Agenda defaultAgenda = agendaDao.findById(dto.defaultAgendaId);
            staff.setDefaultAgenda(defaultAgenda);
        }

        if (dto.agendaIds != null) {
            dto.agendaIds.forEach(id -> {
                Agenda agenda = agendaDao.findById(id);
                staff.addAgenda(agenda);
            });
        }

        if (dto.favoriteAgendaIds != null) {
            dto.favoriteAgendaIds.forEach(id -> {
                Agenda agenda = agendaDao.findById(id);
                staff.addFavoriteAgenda(agenda);
            });
        }

        StaffMapper.updateEntity(staff, dto);
        staffDao.update(staff);

        return Response.status(Response.Status.CREATED)
                .entity(StaffMapper.toDto(staff))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing staff member")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Staff updated"),
            @APIResponse(responseCode = "404", description = "Staff not found")
    })
    public Response update(@PathParam("id") Long id, StaffDTO dto) {
        Staff staff = staffDao.findById(id);
        if (staff == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        StaffMapper.updateEntity(staff, dto);

        if (dto.userId != null) {
            User user = userDao.findById(dto.userId);
            staff.setUser(user);
        }

        if (dto.phenomenonUuid != null) {
            Phenomenon phenomenon = phenomenonDao.findByUuid(dto.phenomenonUuid);
            staff.setPhenomenon(phenomenon);
        }

        if (dto.defaultAgendaId != null) {
            Agenda defaultAgenda = agendaDao.findById(dto.defaultAgendaId);
            staff.setDefaultAgenda(defaultAgenda);
        }

        if (dto.agendaIds != null) {
            dto.agendaIds.forEach(idAgenda -> {
                Agenda agenda = agendaDao.findById(idAgenda);
                staff.addAgenda(agenda);
            });
        }

        if (dto.favoriteAgendaIds != null) {
            dto.favoriteAgendaIds.forEach(idAgenda -> {
                Agenda agenda = agendaDao.findById(idAgenda);
                staff.addFavoriteAgenda(agenda);
            });
        }

        staffDao.update(staff);

        return Response.ok(StaffMapper.toDto(staff)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a staff member")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Staff deleted"),
            @APIResponse(responseCode = "400", description = "Deletion not allowed"),
            @APIResponse(responseCode = "404", description = "Staff not found")
    })
    public Response delete(@PathParam("id") Long id) {
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
