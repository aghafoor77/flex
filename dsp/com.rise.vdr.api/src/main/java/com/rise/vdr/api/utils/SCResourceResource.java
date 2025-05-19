package com.rise.vdr.api.utils;

import com.rise.vdr.api.persistance.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/scresource")
@Api(value = "SCResource by RRB", description = "API used to provide CRUD operations for SCResource service ")
public class SCResourceResource {

	private IResourceService scresourceService;

	public SCResourceResource(IResourceService scresourceService){
		this.scresourceService = scresourceService;
	}

	@GET
	@Path("/did/{did}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCResource]!", notes = "Returns instance of SCResource stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("did") final String did) {
		SCResource scresource = this.scresourceService.get(did);
		if (java.util.Objects.isNull(scresource)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(scresource).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SCResource!", notes = "Get list of all stored SCResource!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<SCResource> list = this.scresourceService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SCResourceList scresourceList = new SCResourceList();
		for (SCResource scresource : list) {
			scresourceList.add(scresource);
		}
		return Response.status(Response.Status.OK).entity(scresourceList).build();
	}
	@GET
	@Path("/vdr/role/{role}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SCResource!", notes = "Get list of all stored SCResource!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response listResourcesByRole(@PathParam("role") final String role) {
		List<SCResource> list = this.scresourceService.listByRole(role);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SCResourceList scresourceList = new SCResourceList();
		for (SCResource scresource : list) {
			scresourceList.add(scresource);
		}
		return Response.status(Response.Status.OK).entity(scresourceList).build();
	}
//TRANSPORTER
	@DELETE
	@Path("/{did}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("did") final String did) {
		int result = this.scresourceService.delete(did);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create SCResource!", notes = "Creating an instance of SCResource!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid SCResource scresource) {
		if(Objects.isNull(this.scresourceService.get(scresource.getDid()))){
			int i = this.scresourceService.post(scresource);
			if (i > 0) {
				return Response.status(Response.Status.OK).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			this.scresourceService.put(scresource);
			return Response.status(Response.Status.OK).build();
		}
	}

	@PUT
	@Path("/{did}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update SCResource!", notes = "Updating an instance of SCResource !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("did") final String did, @Valid SCResource scresource) {
		scresource.setDid(did);
		int i = this.scresourceService.put(scresource);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
