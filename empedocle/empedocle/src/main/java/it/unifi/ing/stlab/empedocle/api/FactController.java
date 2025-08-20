package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.empedocle.api.dto.FactDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.FactMapper;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;

import java.util.Date;
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

@Path("/facts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FactController {

    @EJB
    private FactDao factDao;
    @EJB
    private TypeDao typeDao;

    @GET
    @Path("/{id}")
    @Operation(summary = "Get fact by ID", description = "Returns a single fact given its ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Fact found",
                    content = @Content(schema = @Schema(implementation = FactDTO.class))),
            @APIResponse(responseCode = "404", description = "Fact not found")
    })
    public Response getFactById(
            @Parameter(description = "ID of the fact", required = true)
            @PathParam("id") Long id) {

        FactImpl fact = (FactImpl) factDao.fetchById(id);
        if (fact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        FactDTO dto = FactMapper.toDTO(fact);
        return Response.ok(dto).build();
    }


    @POST
    @Operation(summary = "Create a new fact", description = "Creates a new fact based on its Type and returns it.")
    @APIResponse(responseCode = "201", description = "Fact created",
            content = @Content(schema = @Schema(implementation = FactDTO.class)))
    public Response create(@Parameter(description = "DTO representing the fact to create", required = true)
                           FactDTO dto) {

        // Retrive to type from db
        Type type = typeDao.findById(dto.typeId);
        if (type == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Type not found for id: " + dto.typeId)
                    .build();
        }

        // Creation of a Fact based on his type
        FactImpl fact;
        Class<?> typeClass = type.getClass();

        if (typeClass.equals(TextualType.class)) {
            fact = FactFactory.createTextual();
        } else if (typeClass.equals(QuantitativeType.class)) {
            fact = FactFactory.createQuantitative();
        } else if (typeClass.equals(QualitativeType.class)) {
            fact = FactFactory.createQualitative();
        } else if (typeClass.equals(TemporalType.class)) {
            fact = FactFactory.createTemporal();
        } else if (typeClass.equals(CompositeType.class)) {
            fact = FactFactory.createComposite();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Unsupported Type class: " + typeClass.getSimpleName())
                    .build();
        }

        fact.assignType(type);
        FactMapper.updateEntity(fact, dto);
        factDao.save(fact);

        return Response.status(Response.Status.CREATED)
                .entity(FactMapper.toDTO(fact))
                .build();
    }
}