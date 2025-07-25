package it.stlab.empedocle.api.woodelements;

import it.stlab.woodelements.WoodElement;
import it.stlab.woodelements.WoodElementManager;
import it.stlab.woodelements.WoodElementDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.Instant;
import java.util.List;

@Path("/woodelements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WoodElementResource {

    @Inject
    WoodElementManager manager;

    @Inject
    WoodElementDao dao;

    @POST
    public Response create(CreateWoodElementRequest req,
                           @QueryParam("user") String user) {
        WoodElement entity = WoodElementMapper.toEntity(req);
        WoodElement created = manager.createObservableEntity(entity, Instant.now(), user);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{uuid}")
    public Response get(@PathParam("uuid") String uuid) {
        WoodElement e = dao.findByUuid(uuid);
        if (e == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(e).build();
    }

    @PUT
    @Path("/{uuid}")
    public Response update(@PathParam("uuid") String uuid, CreateWoodElementRequest req) {
        WoodElement existing = dao.findByUuid(uuid);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();
        WoodElement modified = WoodElementMapper.toEntity(req);
        WoodElement updated = manager.modify(Instant.now(), existing, modified);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response delete(@PathParam("uuid") String uuid,
                           @QueryParam("user") String user) {
        WoodElement existing = dao.findByUuid(uuid);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();
        manager.delete(Instant.now(), existing, user);
        return Response.noContent().build();
    }

    @POST
    @Path("/{uuid}/split")
    public Response split(@PathParam("uuid") String uuid,
                          List<CreateWoodElementRequest> reqs,
                          @QueryParam("user") String user) {
        if (reqs == null || reqs.size() != 2)
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Provide exactly two targets").build();
        WoodElement source = dao.findByUuid(uuid);
        if (source == null) return Response.status(Response.Status.NOT_FOUND).build();
        WoodElement t1 = WoodElementMapper.toEntity(reqs.get(0));
        WoodElement t2 = WoodElementMapper.toEntity(reqs.get(1));
        WoodElement result = manager.split(Instant.now(), source, t1, t2);
        return Response.ok(result).build();
    }

    @POST
    @Path("/merge")
    public Response merge(WoodElementMergeRequest req,
                          @QueryParam("user") String user) {
        WoodElement s1 = dao.findByUuid(req.source1());
        WoodElement s2 = dao.findByUuid(req.source2());
        if (s1 == null || s2 == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        WoodElement result = manager.merge(Instant.now(), s1, s2);
        return Response.ok(result).build();
    }
}
