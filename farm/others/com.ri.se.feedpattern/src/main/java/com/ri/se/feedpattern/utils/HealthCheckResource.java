package com.ri.se.feedpattern.utils;



import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ri.se.assignfeed.dto.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1")
@Api(value = "HealthCheckResource by RRB", description = "API used to check the health of deployed service !")
public class HealthCheckResource {


	private AbstractHealthCheck  dbService;
	public HealthCheckResource(AbstractHealthCheck dbService){
		this.dbService = dbService;
	}

	

	@GET
	@Path("/health")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns OK report if service is running !", notes = "Returns OK report if service is running !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response health() {
		
			return Response.status(Response.Status.OK).entity(new  Message("OK health report !")).build();

	}

	@GET
	@Path("/deephealth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns OK report if service is running !", notes = "Returns OK report if service is running !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response deephealth() {
		
		String status = dbService.performHealthCheck();
		if(!Objects.isNull(status)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new  Message(status)).build();
	
		}
		return Response.status(Response.Status.OK).entity(new  Message("OK deep health report !")).build();
		
	}
}

