package com.ri.se.animalderegister.utils;

import com.ri.se.animalderegister.persistance.*;
import com.ri.se.animalderegister.dto.*;
import java.util.Date;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/animalderegister")
@Api(value = "AnimalDeregister by RRB", description = "API used to provide CRUD operations for AnimalDeregister service ")
public class AnimalDeregisterResource {


	private AnimalDeregisterService animalderegisterService;

	private AnimalDeregisterEntityConverter animalderegisterEntityConverter = new AnimalDeregisterEntityConverter();

		public AnimalDeregisterResource(AnimalDeregisterService animalderegisterService){
		this.animalderegisterService = animalderegisterService;
	}

	@GET
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AnimalDeregister]!", notes = "Returns instance of AnimalDeregister stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("animalID") final String animalID) {
		AnimalDeregister animalderegister = this.animalderegisterService.get(animalID);
		if (java.util.Objects.isNull(animalderegister)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(animalderegisterEntityConverter.getDTOAnimalDeregister( animalderegister)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AnimalDeregister]!", notes = "Returns instance of AnimalDeregister stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimal(@PathParam("animalID") final String animalID) {
		AnimalDeregister animalderegister = this.animalderegisterService.get(animalID);
		if (java.util.Objects.isNull(animalderegister)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(animalderegisterEntityConverter.getDTOAnimalDeregister( animalderegister)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
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
		
		String status = animalderegisterService.performHealthCheck();
		if(!Objects.isNull(status)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new  Message(status)).build();
	
		}
		return Response.status(Response.Status.OK).entity(new  Message("OK deep health report !")).build();
		
	}
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored AnimalDeregister!", notes = "Get list of all stored AnimalDeregister!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<AnimalDeregister> list = this.animalderegisterService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAnimalDeregisterList animalderegisterListDTO = new DTOAnimalDeregisterList();
		for (AnimalDeregister animalderegister : list) {
			try{
				animalderegisterListDTO.add(animalderegisterEntityConverter.getDTOAnimalDeregister(animalderegister));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(animalderegisterListDTO).build();
	}

	@DELETE
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("animalID") final String animalID) {
		int result = this.animalderegisterService.delete(animalID);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create AnimalDeregister!", notes = "Creating an instance of AnimalDeregister!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOAnimalDeregister dtoanimalderegister) {
			AnimalDeregister animalderegister = null ;
			try{
				animalderegister = animalderegisterEntityConverter.getAnimalDeregister(dtoanimalderegister);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.animalderegisterService.post(animalderegister);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(dtoanimalderegister).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update AnimalDeregister!", notes = "Updating an instance of AnimalDeregister !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("animalID") final String animalID, @Valid DTOAnimalDeregister dtoanimalderegister) {
		dtoanimalderegister.setAnimalID(animalID);
			AnimalDeregister animalderegister = null ;
			try{
				animalderegister = animalderegisterEntityConverter.getAnimalDeregister(dtoanimalderegister);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.animalderegisterService.put(animalderegister);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
