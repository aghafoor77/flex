package com.ri.se.temporarymovement.utils;

import java.util.ArrayList;
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

import com.ri.se.temporarymovement.dto.DTOTemporaryMovement;
import com.ri.se.temporarymovement.dto.DTOTemporaryMovementList;
import com.ri.se.temporarymovement.dto.Message;
import com.ri.se.temporarymovement.dto.StringList;
import com.ri.se.temporarymovement.dto.TemporaryMovementEntityConverter;
import com.ri.se.temporarymovement.persistance.TemporaryMovement;
import com.ri.se.temporarymovement.persistance.TemporaryMovementService;
import com.ri.se.temporarymovementgroup.persistance.TemporaryMovementGroup;
import com.ri.se.temporarymovementgroup.persistance.TemporaryMovementGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/temporarymovement")
@Api(value = "TemporaryMovement by RRB", description = "API used to provide CRUD operations for TemporaryMovement service ")
public class TemporaryMovementResource {

	private TemporaryMovementService temporarymovementService;
	private TemporaryMovementGroupService temporarymovementgroupService;

	private TemporaryMovementEntityConverter temporarymovementEntityConverter = new TemporaryMovementEntityConverter();

	public TemporaryMovementResource(TemporaryMovementService temporarymovementService,
			TemporaryMovementGroupService temporarymovementgroupService) {
		this.temporarymovementService = temporarymovementService;
		this.temporarymovementgroupService = temporarymovementgroupService;
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
		
		String status = temporarymovementgroupService.performHealthCheck();
		if(!Objects.isNull(status)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new  Message(status)).build();
	
		}
		return Response.status(Response.Status.OK).entity(new  Message("OK deep health report !")).build();
		
	}
	
	@GET
	@Path("/cur/{tmid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TemporaryMovement]!", notes = "Returns instance of TemporaryMovement stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimalsStillInField(@PathParam("tmid") final String tmid) {
		System.out.print("");
		TemporaryMovement temporarymovement = this.temporarymovementService.get(tmid);
		
		if (java.util.Objects.isNull(temporarymovement)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			DTOTemporaryMovement dtoTemporaryMovement = temporarymovementEntityConverter.getDTOTemporaryMovement(temporarymovement);
			List<TemporaryMovementGroup> temporaryMovementGroupList = this.temporarymovementgroupService.getAnimalsStillInField(tmid);
			List<String> data = new ArrayList<String>();
			for(TemporaryMovementGroup temporaryMovementGroup: temporaryMovementGroupList) {
				data.add(temporaryMovementGroup.getAnimalID());
			}
			StringList animals = new StringList();
			animals.setData(data);
			dtoTemporaryMovement.setAnimals(animals);
			return Response.status(Response.Status.OK).entity(dtoTemporaryMovement).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	
	@GET
	@Path("/{tmid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TemporaryMovement]!", notes = "Returns instance of TemporaryMovement stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("tmid") final String tmid) {
		TemporaryMovement temporarymovement = this.temporarymovementService.get(tmid);
		
		if (java.util.Objects.isNull(temporarymovement)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			DTOTemporaryMovement dtoTemporaryMovement = temporarymovementEntityConverter.getDTOTemporaryMovement(temporarymovement);
			List<TemporaryMovementGroup> temporaryMovementGroupList = this.temporarymovementgroupService.getAnimals(tmid);
			List<String> data = new ArrayList<String>();
			for(TemporaryMovementGroup temporaryMovementGroup: temporaryMovementGroupList) {
				data.add(temporaryMovementGroup.getAnimalID());
			}
			StringList animals = new StringList();
			animals.setData(data);
			dtoTemporaryMovement.setAnimals(animals);
			return Response.status(Response.Status.OK).entity(dtoTemporaryMovement).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored TemporaryMovement!", notes = "Get list of all stored TemporaryMovement!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<TemporaryMovement> list = this.temporarymovementService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOTemporaryMovementList temporarymovementListDTO = new DTOTemporaryMovementList();
		for (TemporaryMovement temporarymovement : list) {
			try {
				temporarymovementListDTO
						.add(temporarymovementEntityConverter.getDTOTemporaryMovement(temporarymovement));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(temporarymovementListDTO).build();
	}

	@DELETE
	@Path("/{tmid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("tmid") final String tmid) {
		int result = this.temporarymovementService.delete(tmid);
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
	@ApiOperation(value = "Create TemporaryMovement!", notes = "Creating an instance of TemporaryMovement!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOTemporaryMovement dtotemporarymovement) {
		dtotemporarymovement.setTmid("TM-" + System.currentTimeMillis());
		StringList list = dtotemporarymovement.getAnimals();
		if (Objects.isNull(list.getData()) || list.getData().size() <= 0) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("No animal selected !"))
					.build();
		}
		TemporaryMovement temporarymovement = null;
		try {
			temporarymovement = temporarymovementEntityConverter.getTemporaryMovement(dtotemporarymovement);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.temporarymovementService.post(temporarymovement);
		if (i > 0) {
			for (int l = 0; l < list.getData().size(); l++) {
				TemporaryMovementGroup temporaryMovementGroup = new TemporaryMovementGroup();
				temporaryMovementGroup.setAnimalID(list.getData().get(l));
				temporaryMovementGroup.setEmployeeID("");
				temporaryMovementGroup.setOutDate(new Date());
				temporaryMovementGroup.setInDate(null);
				temporaryMovementGroup.setTmid(dtotemporarymovement.getTmid());
				temporaryMovementGroup.setTmgid("TMG-" + System.currentTimeMillis());
				temporarymovementgroupService.post(temporaryMovementGroup);
			}
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{tmid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update TemporaryMovement!", notes = "Updating an instance of TemporaryMovement !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("tmid") final String tmid, @Valid DTOTemporaryMovement dtotemporarymovement) {
		
		TemporaryMovement temporarymovement = null;
		try {
			temporarymovement = temporarymovementEntityConverter.getTemporaryMovement(dtotemporarymovement);
			temporarymovementgroupService.manageUpdate(dtotemporarymovement.getAnimals().getData(), tmid, "employeeID");
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.temporarymovementService.put(temporarymovement);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
	@PUT
	@Path("/all/{tmid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update TemporaryMovement!", notes = "Updating an instance of TemporaryMovement !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response putall(@PathParam("tmid") final String tmid) {
		Date d = new Date();
		
		TemporaryMovement temporarymovement = this.temporarymovementService.get(tmid);
		if (java.util.Objects.isNull(temporarymovement)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		
		List<TemporaryMovementGroup> stillInField = temporarymovementgroupService.getAnimalsStillInField(tmid);
		for(TemporaryMovementGroup tmg: stillInField){
			tmg.setInDate(d );
			temporarymovementgroupService.put(tmg);
		}
		
		temporarymovement.setInDate(d);
		int i = this.temporarymovementService.put(temporarymovement);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
	
	
}
