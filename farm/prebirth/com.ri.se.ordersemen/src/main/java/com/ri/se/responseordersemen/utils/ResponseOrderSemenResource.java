package com.ri.se.responseordersemen.utils;

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

import com.ri.se.responseordersemen.dto.DTOResponseOrderSemen;
import com.ri.se.responseordersemen.dto.DTOResponseOrderSemenList;
import com.ri.se.responseordersemen.dto.Message;
import com.ri.se.responseordersemen.dto.ResponseOrderSemenEntityConverter;
import com.ri.se.responseordersemen.persistance.ResponseOrderSemen;
import com.ri.se.responseordersemen.persistance.ResponseOrderSemenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/responseordersemen")
@Api(value = "ResponseOrderSemen by RRB", description = "API used to provide CRUD operations for ResponseOrderSemen service ")
public class ResponseOrderSemenResource {

	private ResponseOrderSemenService responseordersemenService;

	private ResponseOrderSemenEntityConverter responseordersemenEntityConverter = new ResponseOrderSemenEntityConverter();

	public ResponseOrderSemenResource(ResponseOrderSemenService responseordersemenService) {
		this.responseordersemenService = responseordersemenService;
	}

	/*
	 * @GET
	 * 
	 * @Path("/health")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value = "Returns OK report if service is running !", notes =
	 * "Returns OK report if service is running !", response = String.class)
	 * 
	 * @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server error !") }) public
	 * Response health() {
	 * 
	 * return Response.status(Response.Status.OK).entity(new
	 * Message("OK health report !")).build();
	 * 
	 * }
	 * 
	 * @GET
	 * 
	 * @Path("/deephealth")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value = "Returns OK report if service is running !", notes =
	 * "Returns OK report if service is running !", response = String.class)
	 * 
	 * @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server error !") }) public
	 * Response deephealth() {
	 * 
	 * String status = responseordersemenService.performHealthCheck();
	 * if(!Objects.isNull(status)) { return
	 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
	 * Message(status)).build();
	 * 
	 * } return Response.status(Response.Status.OK).entity(new
	 * Message("OK deep health report !")).build();
	 * 
	 * }
	 */

	@GET
	@Path("/{osid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [ResponseOrderSemen]!", notes = "Returns instance of ResponseOrderSemen stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("osid") final String osid) {
		ResponseOrderSemen responseordersemen = this.responseordersemenService.get(osid);
		if (java.util.Objects.isNull(responseordersemen)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(responseordersemenEntityConverter.getDTOResponseOrderSemen(responseordersemen)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored ResponseOrderSemen!", notes = "Get list of all stored ResponseOrderSemen!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<ResponseOrderSemen> list = this.responseordersemenService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOResponseOrderSemenList responseordersemenListDTO = new DTOResponseOrderSemenList();
		for (ResponseOrderSemen responseordersemen : list) {
			try {
				responseordersemenListDTO
						.add(responseordersemenEntityConverter.getDTOResponseOrderSemen(responseordersemen));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(responseordersemenListDTO).build();
	}
	
	
	@DELETE
	@Path("/{osid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("osid") final String osid) {
		int result = this.responseordersemenService.delete(osid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create ResponseOrderSemen!", notes = "Creating an instance of ResponseOrderSemen!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOResponseOrderSemen dtoresponseordersemen) {
		ResponseOrderSemen responseordersemen = null;
		try {
			responseordersemen = responseordersemenEntityConverter.getResponseOrderSemen(dtoresponseordersemen);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.responseordersemenService.post(responseordersemen);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(responseordersemen).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{osid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update ResponseOrderSemen!", notes = "Updating an instance of ResponseOrderSemen !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("osid") final String osid, @Valid DTOResponseOrderSemen dtoresponseordersemen) {
		dtoresponseordersemen.setOsid(osid);
		ResponseOrderSemen responseordersemen = null;
		try {
			responseordersemen = responseordersemenEntityConverter.getResponseOrderSemen(dtoresponseordersemen);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.responseordersemenService.put(responseordersemen);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
}
