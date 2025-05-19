package com.rise.carrier.slaughterhouse.utils;

import com.rise.carrier.slaughterhouse.persistance.*;
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

@Path("/v1/shcarrier")
@Api(value = "SHCarrier by RRB", description = "API used to provide CRUD operations for SHCarrier service ")
public class SHCarrierResource {


	private SHCarrierService shcarrierService;

	public SHCarrierResource(SHCarrierService shcarrierService){
		this.shcarrierService = shcarrierService;
	}

	@GET
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SHCarrier]!", notes = "Returns instance of SHCarrier stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("tripNo") final String tripNo) {
		SHCarrier shcarrier = this.shcarrierService.get(tripNo);
		if (java.util.Objects.isNull(shcarrier)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(shcarrier).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SHCarrier!", notes = "Get list of all stored SHCarrier!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<SHCarrier> list = this.shcarrierService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SHCarrierList shcarrierList = new SHCarrierList();
		for (SHCarrier shcarrier : list) {
			shcarrierList.add(shcarrier);
		}
		return Response.status(Response.Status.OK).entity(shcarrierList).build();
	}

	@DELETE
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("tripNo") final String tripNo) {
		int result = this.shcarrierService.delete(tripNo);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create SHCarrier!", notes = "Creating an instance of SHCarrier!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid SHCarrier shcarrier) {
		int i = this.shcarrierService.post(shcarrier);
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
	@ApiOperation(value = "Update SHCarrier!", notes = "Updating an instance of SHCarrier !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("tripNo") final String tripNo, @Valid SHCarrier shcarrier) {
		shcarrier.setTripNo(tripNo);
		int i = this.shcarrierService.put(shcarrier);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
