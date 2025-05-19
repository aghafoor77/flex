package com.rise.vdr.api.utils;

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

import com.rise.vdr.api.persistance.SCTransaction;
import com.rise.vdr.api.persistance.SCTransactionList;
import com.rise.vdr.api.persistance.SCTransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/sctransaction")
@Api(value = "SCTransaction by RRB", description = "API used to provide CRUD operations for SCTransaction service ")
public class SCTransactionResource {


	private SCTransactionService sctransactionService;

	public SCTransactionResource(SCTransactionService sctransactionService){
		this.sctransactionService = sctransactionService;
	}

	@GET
	@Path("/{eid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCTransaction]!", notes = "Returns instance of SCTransaction stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("eid") final String eid) {
		SCTransaction sctransaction = this.sctransactionService.getTrans(eid);
		if (java.util.Objects.isNull(sctransaction)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(sctransaction).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SCTransaction!", notes = "Get list of all stored SCTransaction!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<SCTransaction> list = this.sctransactionService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SCTransactionList sctransactionList = new SCTransactionList();
		for (SCTransaction sctransaction : list) {
			sctransactionList.add(sctransaction);
		}
		return Response.status(Response.Status.OK).entity(sctransactionList).build();
	}

	@DELETE
	@Path("/{eid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("eid") final String eid) {
		int result = this.sctransactionService.delete(eid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create SCTransaction!", notes = "Creating an instance of SCTransaction!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid SCTransaction sctransaction) {
		int i = this.sctransactionService.post(sctransaction);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{eid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update SCTransaction!", notes = "Updating an instance of SCTransaction !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("eid") final String eid, @Valid SCTransaction sctransaction) {
		sctransaction.setEid(eid);
		int i = this.sctransactionService.put(sctransaction);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
