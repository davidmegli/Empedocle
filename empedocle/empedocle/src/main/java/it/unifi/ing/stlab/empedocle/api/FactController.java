package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/facts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FactController{

    @EJB
    private FactDao factDao;

    @GET
    @Path("/{id}")
    public Response getFact(@PathParam("id") Long id) {
        Fact fact = factDao.findById(id);
        if (fact == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(fact).build();
    }

    @POST
    public Response createFact(FactImpl fact) {
        factDao.save(fact);
        return Response.status(Response.Status.CREATED).entity(fact).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateFact(@PathParam("id") Long id, Fact updatedFact) {
        Fact existing = factDao.findById(id);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();
        // Copia i campi aggiornabili
        existing.assignType(updatedFact.getType());
        existing.setContext(updatedFact.getContext());
        existing.setStatus(updatedFact.getStatus());
        factDao.save(existing);
        return Response.ok(existing).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFact(@PathParam("id") Long id) {
        Fact fact = factDao.findById(id);
        if (fact == null) return Response.status(Response.Status.NOT_FOUND).build();
        fact.delete();
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/children")
    public Response addChildren(@PathParam("id") Long factId, TypeLink typeLink) {
        Fact fact = factDao.findById(factId);
        if (fact == null) return Response.status(Response.Status.NOT_FOUND).build();
        User user = null; // o recuperalo dal DB
        Time time = new Time(new Date( System.currentTimeMillis()));
        factDao.addChildren(typeLink, fact, user, time);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{factId}/children/{linkId}")
    public Response removeChild(@PathParam("factId") Long factId,
                                @PathParam("linkId") Long linkId) {

        FactImpl fact = (FactImpl)factDao.fetchById(factId);
        if (fact == null) return Response.status(Response.Status.NOT_FOUND).build();

        FactLinkImpl link = fact.listChildren().stream()
                .filter(l -> l.getId().equals(linkId))
                .findFirst()
                .orElse(null);

        if (link == null) return Response.status(Response.Status.NOT_FOUND).build();

        factDao.removeChildren(link);
        return Response.noContent().build();
    }

}
