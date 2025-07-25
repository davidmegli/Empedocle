package it.stlab.empedocle.api.measurement;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.stlab.empedocle.service.MeasurementSessionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

// TODO: da rivedere. Usare classi esistenti di empedocle invece di MeasurementSessionService
@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeasurementSessionResource {

    @Inject
    MeasurementSessionService service;

    @GET
    public List<MeasurementSession> all() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Response byId(@PathParam("id") Long id) {
        MeasurementSession ms = service.find(id);
        if (ms == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(ms).build();
    }

    @POST
    public Response create(CreateSessionRequest req) {
        MeasurementSession ms = service.create(req.toEntity());
        return Response.status(Response.Status.CREATED).entity(ms).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CreateSessionRequest req) {
        MeasurementSession ms = req.toEntity();
        ms.setId(id);
        MeasurementSession updated = service.update(ms);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
