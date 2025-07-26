package it.unifi.ing.stlab.woodelements.api.resource;

import it.unifi.ing.stlab.woodelements.api.dto.*;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDaoBean;
import it.unifi.ing.stlab.woodelements.factory.WoodElementActionFactory;
import it.unifi.ing.stlab.woodelements.model.actions.*;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Wood Element Actions", description = "Domain-specific actions on wood elements")
@Path("/woodelements/actions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WoodElementActionResource {

    @Inject
    private WoodElementDaoBean dao;

    @Inject
    private WoodElementActionFactory actionFactory;

    @POST
    @Path("/split")
    @Operation(summary = "Split a wood element", description = "Splits a source wood element into two target elements")
    @APIResponse(responseCode = "200", description = "Split action completed successfully")
    public Response split(SplitActionDTO dto) {
        WoodElement source = dao.findById(dto.sourceId);
        WoodElement t1 = dao.findById(dto.target1Id);
        WoodElement t2 = dao.findById(dto.target2Id);

        WoodElementSplitAction action = actionFactory.splitAction();
        action.assignSource(source);
        action.assignTarget(t1);
        action.assignTarget2(t2);

        return Response.ok().build();
    }

    @POST
    @Path("/merge")
    @Operation(summary = "Merge two wood elements", description = "Merges two source wood elements into a single target element")
    @APIResponse(responseCode = "200", description = "Merge action completed successfully")
    public Response merge(MergeActionDTO dto) {
        WoodElement t = dao.findById(dto.targetId);
        WoodElement s1 = dao.findById(dto.source1Id);
        WoodElement s2 = dao.findById(dto.source2Id);

        WoodElementMergeAction action = actionFactory.mergeAction();
        action.assignTarget(t);
        action.assignSource1(s1);
        action.assignSource2(s2);

        return Response.ok().build();
    }

    @POST
    @Path("/modify")
    @Operation(summary = "Modify a wood element", description = "Modifies a source wood element and applies changes to a target")
    @APIResponse(responseCode = "200", description = "Modify action completed successfully")
    public Response modify(ModifyActionDTO dto) {
        WoodElement source = dao.findById(dto.sourceId);
        WoodElement target = dao.findById(dto.targetId);

        WoodElementModifyAction action = actionFactory.modifyAction();
        action.assignSource(source);
        action.assignTarget(target);

        return Response.ok().build();
    }

    @POST
    @Path("/delete")
    @Operation(summary = "Delete a wood element via action", description = "Performs a delete action on a wood element")
    @APIResponse(responseCode = "200", description = "Delete action completed successfully")
    public Response delete(DeleteActionDTO dto) {
        WoodElement source = dao.findById(dto.sourceId);

        WoodElementDeleteAction action = actionFactory.deleteAction();
        action.assignSource(source);

        return Response.ok().build();
    }
}