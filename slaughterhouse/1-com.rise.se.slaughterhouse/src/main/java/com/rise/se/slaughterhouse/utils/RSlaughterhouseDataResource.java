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

@Path("/v1/rslaughterhousedata")
@Api(value = "RSlaughterhouseData by RRB", description = "API used to provide CRUD operations for RSlaughterhouseData service ")
public class RSlaughterhouseDataResource {


	private RSlaughterhouseDataService rslaughterhousedataService;

	public RSlaughterhouseDataResource(RSlaughterhouseDataService rslaughterhousedataService){
		this.rslaughterhousedataService = rslaughterhousedataService;
	}

	@GET
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [RSlaughterhouseData]!", notes = "Returns instance of RSlaughterhouseData stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("tripNo") final String tripNo) {
		RSlaughterhouseData rslaughterhousedata = this.rslaughterhousedataService.get(tripNo);
		if (java.util.Objects.isNull(rslaughterhousedata)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(rslaughterhousedata).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored RSlaughterhouseData!", notes = "Get list of all stored RSlaughterhouseData!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<RSlaughterhouseData> list = this.rslaughterhousedataService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		RSlaughterhouseDataList rslaughterhousedataList = new RSlaughterhouseDataList();
		for (RSlaughterhouseData rslaughterhousedata : list) {
			rslaughterhousedataList.add(rslaughterhousedata);
		}
		return Response.status(Response.Status.OK).entity(rslaughterhousedataList).build();
	}

	@DELETE
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("tripNo") final String tripNo) {
		int result = this.rslaughterhousedataService.delete(tripNo);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create RSlaughterhouseData!", notes = "Creating an instance of RSlaughterhouseData!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid RSlaughterhouseData rslaughterhousedata) {
		int i = this.rslaughterhousedataService.post(rslaughterhousedata);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update RSlaughterhouseData!", notes = "Updating an instance of RSlaughterhouseData !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("tripNo") final String tripNo, @Valid RSlaughterhouseData rslaughterhousedata) {
		rslaughterhousedata.setTripNo(tripNo);
		int i = this.rslaughterhousedataService.put(rslaughterhousedata);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
