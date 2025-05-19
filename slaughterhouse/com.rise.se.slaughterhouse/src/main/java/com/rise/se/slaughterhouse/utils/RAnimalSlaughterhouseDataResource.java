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

@Path("/v1/ranimalslaughterhousedata")
@Api(value = "RAnimalSlaughterhouseData by RRB", description = "API used to provide CRUD operations for RAnimalSlaughterhouseData service ")
public class RAnimalSlaughterhouseDataResource {


	private RAnimalSlaughterhouseDataService ranimalslaughterhousedataService;

	public RAnimalSlaughterhouseDataResource(RAnimalSlaughterhouseDataService ranimalslaughterhousedataService){
		this.ranimalslaughterhousedataService = ranimalslaughterhousedataService;
	}

	@GET
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [RAnimalSlaughterhouseData]!", notes = "Returns instance of RAnimalSlaughterhouseData stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("animalID") final String animalID) {
		RAnimalSlaughterhouseData ranimalslaughterhousedata = this.ranimalslaughterhousedataService.get(animalID);
		if (java.util.Objects.isNull(ranimalslaughterhousedata)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(ranimalslaughterhousedata).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored RAnimalSlaughterhouseData!", notes = "Get list of all stored RAnimalSlaughterhouseData!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<RAnimalSlaughterhouseData> list = this.ranimalslaughterhousedataService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		RAnimalSlaughterhouseDataList ranimalslaughterhousedataList = new RAnimalSlaughterhouseDataList();
		for (RAnimalSlaughterhouseData ranimalslaughterhousedata : list) {
			ranimalslaughterhousedataList.add(ranimalslaughterhousedata);
		}
		return Response.status(Response.Status.OK).entity(ranimalslaughterhousedataList).build();
	}

	@DELETE
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("animalID") final String animalID) {
		int result = this.ranimalslaughterhousedataService.delete(animalID);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create RAnimalSlaughterhouseData!", notes = "Creating an instance of RAnimalSlaughterhouseData!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid RAnimalSlaughterhouseData ranimalslaughterhousedata) {
		int i = this.ranimalslaughterhousedataService.post(ranimalslaughterhousedata);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update RAnimalSlaughterhouseData!", notes = "Updating an instance of RAnimalSlaughterhouseData !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("animalID") final String animalID, @Valid RAnimalSlaughterhouseData ranimalslaughterhousedata) {
		ranimalslaughterhousedata.setAnimalID(animalID);
		int i = this.ranimalslaughterhousedataService.put(ranimalslaughterhousedata);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
