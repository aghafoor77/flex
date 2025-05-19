package com.ri.se.assignanimalstatus.utils;

import com.ri.se.assignanimalstatus.persistance.*;
import com.ri.se.assignanimalstatus.dto.*;
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

@Path("/v1/assignanimalstatus")
@Api(value = "AssignAnimalStatus by RRB", description = "API used to provide CRUD operations for AssignAnimalStatus service ")
public class AssignAnimalStatusResource {


	private AssignAnimalStatusService assignanimalstatusService;

	private AssignAnimalStatusEntityConverter assignanimalstatusEntityConverter = new AssignAnimalStatusEntityConverter();

		public AssignAnimalStatusResource(AssignAnimalStatusService assignanimalstatusService){
		this.assignanimalstatusService = assignanimalstatusService;
	}

	@GET
	@Path("/{aasid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AssignAnimalStatus]!", notes = "Returns instance of AssignAnimalStatus stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("aasid") final String aasid) {
		AssignAnimalStatus assignanimalstatus = this.assignanimalstatusService.get(aasid);
		if (java.util.Objects.isNull(assignanimalstatus)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(assignanimalstatusEntityConverter.getDTOAssignAnimalStatus( assignanimalstatus)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}
	@GET
	@Path("/aaid/{aaid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AssignAnimalStatus]!", notes = "Returns instance of AssignAnimalStatus stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByaaid(@PathParam("aaid") final String aaid) {
		List<AssignAnimalStatus> assignanimalstatus = this.assignanimalstatusService.getByAaid(aaid);
		if (java.util.Objects.isNull(assignanimalstatus)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(assignanimalstatusEntityConverter.getDTOAssignAnimalStatusList( assignanimalstatus)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}
	


	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored AssignAnimalStatus!", notes = "Get list of all stored AssignAnimalStatus!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<AssignAnimalStatus> list = this.assignanimalstatusService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAssignAnimalStatusList assignanimalstatusListDTO = new DTOAssignAnimalStatusList();
		for (AssignAnimalStatus assignanimalstatus : list) {
			try{
				assignanimalstatusListDTO.add(assignanimalstatusEntityConverter.getDTOAssignAnimalStatus(assignanimalstatus));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(assignanimalstatusListDTO).build();
	}

	@DELETE
	@Path("/{aasid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("aasid") final String aasid) {
		int result = this.assignanimalstatusService.delete(aasid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create AssignAnimalStatus!", notes = "Creating an instance of AssignAnimalStatus!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOAssignAnimalStatus dtoassignanimalstatus) {
			AssignAnimalStatus assignanimalstatus = null ;
			try{
				assignanimalstatus = assignanimalstatusEntityConverter.getAssignAnimalStatus(dtoassignanimalstatus);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.assignanimalstatusService.post(assignanimalstatus);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(assignanimalstatus).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{aasid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update AssignAnimalStatus!", notes = "Updating an instance of AssignAnimalStatus !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("aasid") final String aasid, @Valid DTOAssignAnimalStatus dtoassignanimalstatus) {
		dtoassignanimalstatus.setAasid(aasid);
			AssignAnimalStatus assignanimalstatus = null ;
			try{
				assignanimalstatus = assignanimalstatusEntityConverter.getAssignAnimalStatus(dtoassignanimalstatus);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.assignanimalstatusService.put(assignanimalstatus);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
