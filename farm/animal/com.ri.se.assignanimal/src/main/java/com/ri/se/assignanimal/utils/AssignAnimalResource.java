package com.ri.se.assignanimal.utils;

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

import com.ri.se.assignanimal.dto.AssignAnimalEntityConverter;
import com.ri.se.assignanimal.dto.AssignAnimalExtConverter;
import com.ri.se.assignanimal.dto.DTOAssignAnimal;
import com.ri.se.assignanimal.dto.DTOAssignAnimalExt;
import com.ri.se.assignanimal.dto.DTOAssignAnimalList;
import com.ri.se.assignanimal.dto.Message;
import com.ri.se.assignanimal.persistance.AssignAnimal;
import com.ri.se.assignanimal.persistance.AssignAnimalService;
import com.ri.se.assignanimalstatus.persistance.AssignAnimalStatus;
import com.ri.se.assignanimalstatus.persistance.AssignAnimalStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/assignanimal")
@Api(value = "AssignAnimal by RRB", description = "API used to provide CRUD operations for AssignAnimal service ")
public class AssignAnimalResource {

	private AssignAnimalService assignanimalService;

	private AssignAnimalEntityConverter assignanimalEntityConverter = new AssignAnimalEntityConverter();
	private AssignAnimalStatusService assignAnimalStatusService;
	
	public AssignAnimalResource(AssignAnimalService assignanimalService, AssignAnimalStatusService assignAnimalStatusService) {
		this.assignanimalService = assignanimalService;
		this.assignAnimalStatusService= assignAnimalStatusService;
	}

	@GET
	@Path("/{aaid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AssignAnimal]!", notes = "Returns instance of AssignAnimal stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("aaid") final String aaid) {
		AssignAnimal assignanimal = this.assignanimalService.get(aaid);
		if (java.util.Objects.isNull(assignanimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(assignanimalEntityConverter.getDTOAssignAnimal(assignanimal)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}
	
	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AssignAnimal]!", notes = "Returns instance of AssignAnimal stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAssignedAnimalByAnimalID(@PathParam("animalID") final String animalID) {
		List<AssignAnimal> assignanimal = this.assignanimalService.getByAnimlalID(animalID);
		if (java.util.Objects.isNull(assignanimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(assignanimalEntityConverter.getDTOAssignAnimalList(assignanimal)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}	

	@GET
	@Path("/animal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored AssignAnimal!", notes = "Get list of all stored AssignAnimal!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<AssignAnimal> list = this.assignanimalService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAssignAnimalList assignanimalListDTO = new DTOAssignAnimalList();
		for (AssignAnimal assignanimal : list) {
			try {
				assignanimalListDTO.add(assignanimalEntityConverter.getDTOAssignAnimal(assignanimal));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(assignanimalListDTO).build();
	}
	//
	
	@GET
	@Path("/records/healthcare/{examiner}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored AssignAnimal by examiner!", notes = "Get list of all stored AssignAnimal by examiner!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response betByExaminer(@PathParam("examiner") final String examiner) {
		
		List<AssignAnimal> list = this.assignanimalService.getByExaminer(examiner);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAssignAnimalList assignanimalListDTO = new DTOAssignAnimalList();
		for (AssignAnimal assignanimal : list) {
			try {
				assignanimalListDTO.add(assignanimalEntityConverter.getDTOAssignAnimal(assignanimal));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(assignanimalListDTO).build();
	}
	

	@DELETE
	@Path("/{aaid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("aaid") final String aaid) {
		int result = this.assignanimalService.delete(aaid);
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
	@ApiOperation(value = "Create AssignAnimal!", notes = "Creating an instance of AssignAnimal!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOAssignAnimalExt dtoAssignAnimalExt) {
		AssignAnimal assignanimal = null;
		try {
			assignanimal = new AssignAnimalExtConverter().toAssignAnimal(dtoAssignAnimalExt);
			assignanimal.setAaid("AAID-" + System.currentTimeMillis());
			
			List<String> animals = dtoAssignAnimalExt.getAnimals();
			
			int i=0;
			for(String animal : animals) {
				AssignAnimalStatus assignAnimalStatus = new AssignAnimalStatus();
				assignAnimalStatus.setAaid(assignanimal.getAaid());
				assignAnimalStatus.setAasid("AASID-"+i+"-"+System.currentTimeMillis());
				assignAnimalStatus.setAnimals(animal);
				this.assignAnimalStatusService.post(assignAnimalStatus);
				i++;
			}
			
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.assignanimalService.post(assignanimal);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(assignanimal).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{aaid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update AssignAnimal!", notes = "Updating an instance of AssignAnimal !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("aaid") final String aaid, @Valid DTOAssignAnimal dtoassignanimal) {
		dtoassignanimal.setAaid(aaid);
		AssignAnimal assignanimal = null;
		try {
			assignanimal = assignanimalEntityConverter.getAssignAnimal(dtoassignanimal);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.assignanimalService.put(assignanimal);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
}
