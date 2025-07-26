package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.api.dto.StaffDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.StaffMapper;
import it.unifi.ing.stlab.empedocle.model.Staff;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/staff")
@Produces(MediaType.APPLICATION_JSON)
public class StaffResource {

    @EJB
    private StaffDao staffDao;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Staff staff = staffDao.fetchById(id);
        if (staff == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        StaffDTO dto = StaffMapper.toDto(staff);
        return Response.ok(dto).build();
    }
}
