package com.rise.se.slaughterhouse.utils;

import com.rise.se.slaughterhouse.persistance.*;
import java.util.Date;
import java.util.List;
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

@Path("/v1/rcarrierslaughterhousedata")
@Api(value = "RCarrierSlaughterhouseData by RRB", description = "API used to provide CRUD operations for RCarrierSlaughterhouseData service ")
public class RCarrierSlaughterhouseDataResource {


	private RCarrierSlaughterhouseDataService rcarrierslaughterhousedataService;

	public RCarrierSlaughterhouseDataResource(RCarrierSlaughterhouseDataService rcarrierslaughterhousedataService){
		this.rcarrierslaughterhousedataService = rcarrierslaughterhousedataService;
	}

	@GET
	@Path("/{carrierId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [RCarrierSlaughterhouseData]!", notes = "Returns instance of RCarrierSlaughterhouseData stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("carrierId") final String carrierId) {
		RCarrierSlaughterhouseData rcarrierslaughterhousedata = this.rcarrierslaughterhousedataService.get(carrierId);
		if (java.util.Objects.isNull(rcarrierslaughterhousedata)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(rcarrierslaughterhousedata).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored RCarrierSlaughterhouseData!", notes = "Get list of all stored RCarrierSlaughterhouseData!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<RCarrierSlaughterhouseData> list = this.rcarrierslaughterhousedataService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		RCarrierSlaughterhouseDataList rcarrierslaughterhousedataList = new RCarrierSlaughterhouseDataList();
		for (RCarrierSlaughterhouseData rcarrierslaughterhousedata : list) {
			rcarrierslaughterhousedataList.add(rcarrierslaughterhousedata);
		}
		return Response.status(Response.Status.OK).entity(rcarrierslaughterhousedataList).build();
	}

	@DELETE
	@Path("/{carrierId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("carrierId") final String carrierId) {
		int result = this.rcarrierslaughterhousedataService.delete(carrierId);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create RCarrierSlaughterhouseData!", notes = "Creating an instance of RCarrierSlaughterhouseData!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid RCarrierSlaughterhouseData rcarrierslaughterhousedata) {
		int i = this.rcarrierslaughterhousedataService.post(rcarrierslaughterhousedata);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{carrierId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update RCarrierSlaughterhouseData!", notes = "Updating an instance of RCarrierSlaughterhouseData !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("carrierId") final String carrierId, @Valid RCarrierSlaughterhouseData rcarrierslaughterhousedata) {
		rcarrierslaughterhousedata.setCarrierId(carrierId);
		int i = this.rcarrierslaughterhousedataService.put(rcarrierslaughterhousedata);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
