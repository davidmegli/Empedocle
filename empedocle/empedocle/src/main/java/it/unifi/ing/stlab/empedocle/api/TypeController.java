package it.unifi.ing.stlab.empedocle.api;

import it.unifi.ing.stlab.empedocle.api.dto.TypeDTO;
import it.unifi.ing.stlab.empedocle.api.mapper.TypeMapper;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.commons.util.TimeFormat;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TypeController {

    @EJB
    private TypeDao typeDao; // supponendo tu abbia un dao per Type

    @GET
    @Path("/{id}")
    public TypeDTO findById(@PathParam("id") Long id) {
        Type type = typeDao.findById(id);
        if (type == null) {
            throw new NotFoundException("Type not found with id " + id);
        }
        return TypeMapper.toDTO(type);
    }

    @POST
    @Transactional
    public Response create(TypeDTO dto) {
        Type entity = TypeMapper.toEntity(dto);
        typeDao.save(entity);
        return Response.status(Response.Status.CREATED)
                .entity(TypeMapper.toDTO(entity))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, TypeDTO dto) {
        Type existing = typeDao.findById(id);
        if (existing == null) {
            throw new NotFoundException("Type not found with id " + id);
        }

        // aggiorno i campi
        existing.setName(dto.name);
        existing.setDescription(dto.description);
        existing.setReadOnly(dto.readOnly);
        existing.setAnonymous(dto.anonymous);
        existing.setRecurrent(dto.recurrent);

        if (existing instanceof TemporalType && dto.timeFormat != null) {
            ((TemporalType) existing).setType(Enum.valueOf(TimeFormat.class, dto.timeFormat));
        }
        if (existing instanceof QueriedType) {
            ((QueriedType) existing).setQueryDef(dto.queryDef);
        }

        typeDao.update(existing);
        return Response.ok(TypeMapper.toDTO(existing)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Type existing = typeDao.findById(id);
        if (existing == null) {
            throw new NotFoundException("Type not found with id " + id);
        }
        typeDao.delete(id);
        return Response.noContent().build();
    }
}
