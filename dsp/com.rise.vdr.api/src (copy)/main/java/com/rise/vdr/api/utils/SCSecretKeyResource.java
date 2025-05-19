package com.rise.vdr.api.utils;

import com.rise.vdr.api.persistance.*;
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

@Path("/v1/scsecretkey")
@Api(value = "SCSecretKey by RRB", description = "API used to provide CRUD operations for SCSecretKey service ")
public class SCSecretKeyResource {


	private SCSecretKeyService scsecretkeyService;

	public SCSecretKeyResource(SCSecretKeyService scsecretkeyService){
		this.scsecretkeyService = scsecretkeyService;
	}

	@GET
	@Path("/{ssid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCSecretKey]!", notes = "Returns instance of SCSecretKey stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("ssid") final String ssid) {
		SCSecretKey scsecretkey = this.scsecretkeyService.get(ssid);
		if (java.util.Objects.isNull(scsecretkey)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(scsecretkey).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SCSecretKey!", notes = "Get list of all stored SCSecretKey!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<SCSecretKey> list = this.scsecretkeyService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SCSecretKeyList scsecretkeyList = new SCSecretKeyList();
		for (SCSecretKey scsecretkey : list) {
			scsecretkeyList.add(scsecretkey);
		}
		return Response.status(Response.Status.OK).entity(scsecretkeyList).build();
	}

	@DELETE
	@Path("/{ssid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("ssid") final String ssid) {
		int result = this.scsecretkeyService.delete(ssid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create SCSecretKey!", notes = "Creating an instance of SCSecretKey!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid SCSecretKey scsecretkey) {
		int i = this.scsecretkeyService.post(scsecretkey);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{ssid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update SCSecretKey!", notes = "Updating an instance of SCSecretKey !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("ssid") final String ssid, @Valid SCSecretKey scsecretkey) {
		scsecretkey.setSsid(ssid);
		int i = this.scsecretkeyService.put(scsecretkey);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
