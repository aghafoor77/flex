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

@Path("/v1/slaughterhouse")
@Api(value = "Slaughterhouse by RRB", description = "API used to provide CRUD operations for Slaughterhouse service ")
public class SlaughterhouseResource {


	private SlaughterhouseService slaughterhouseService;

	public SlaughterhouseResource(SlaughterhouseService slaughterhouseService){
		this.slaughterhouseService = slaughterhouseService;
	}

	@GET
	@Path("/{sid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [Slaughterhouse]!", notes = "Returns instance of Slaughterhouse stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("sid") final String sid) {
		Slaughterhouse slaughterhouse = this.slaughterhouseService.get(sid);
		if (java.util.Objects.isNull(slaughterhouse)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(slaughterhouse).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored Slaughterhouse!", notes = "Get list of all stored Slaughterhouse!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<Slaughterhouse> list = this.slaughterhouseService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SlaughterhouseList slaughterhouseList = new SlaughterhouseList();
		for (Slaughterhouse slaughterhouse : list) {
			slaughterhouseList.add(slaughterhouse);
		}
		return Response.status(Response.Status.OK).entity(slaughterhouseList).build();
	}

	@DELETE
	@Path("/{sid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("sid") final String sid) {
		int result = this.slaughterhouseService.delete(sid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create Slaughterhouse!", notes = "Creating an instance of Slaughterhouse!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid Slaughterhouse slaughterhouse) {
		int i = this.slaughterhouseService.post(slaughterhouse);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{sid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update Slaughterhouse!", notes = "Updating an instance of Slaughterhouse !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("sid") final String sid, @Valid Slaughterhouse slaughterhouse) {
		slaughterhouse.setSid(sid);
		int i = this.slaughterhouseService.put(slaughterhouse);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
