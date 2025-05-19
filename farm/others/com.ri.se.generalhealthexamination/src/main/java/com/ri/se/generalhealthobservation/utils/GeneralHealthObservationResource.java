package com.ri.se.generalhealthobservation.utils;

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

import com.ri.se.generalhealthobservation.dto.DTOGeneralHealthObservation;
import com.ri.se.generalhealthobservation.dto.DTOGeneralHealthObservationList;
import com.ri.se.generalhealthobservation.dto.GeneralHealthObservationEntityConverter;
import com.ri.se.generalhealthobservation.dto.Message;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservation;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/generalhealthobservation")
@Api(value = "GeneralHealthObservation by RRB", description = "API used to provide CRUD operations for GeneralHealthObservation service ")
public class GeneralHealthObservationResource {


	private GeneralHealthObservationService generalhealthobservationService;

	private GeneralHealthObservationEntityConverter generalhealthobservationEntityConverter = new GeneralHealthObservationEntityConverter();

		public GeneralHealthObservationResource(GeneralHealthObservationService generalhealthobservationService){
		this.generalhealthobservationService = generalhealthobservationService;
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
	 * String status = generalhealthobservationService.performHealthCheck();
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
	@Path("/{ghoid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [GeneralHealthObservation]!", notes = "Returns instance of GeneralHealthObservation stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("ghoid") final String ghoid) {
		GeneralHealthObservation generalhealthobservation = this.generalhealthobservationService.get(ghoid);
		if (java.util.Objects.isNull(generalhealthobservation)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(generalhealthobservationEntityConverter.getDTOGeneralHealthObservation( generalhealthobservation)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored GeneralHealthObservation!", notes = "Get list of all stored GeneralHealthObservation!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<GeneralHealthObservation> list = this.generalhealthobservationService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOGeneralHealthObservationList generalhealthobservationListDTO = new DTOGeneralHealthObservationList();
		for (GeneralHealthObservation generalhealthobservation : list) {
			try{
				generalhealthobservationListDTO.add(generalhealthobservationEntityConverter.getDTOGeneralHealthObservation(generalhealthobservation));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(generalhealthobservationListDTO).build();
	}
	
	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored GeneralHealthObservation against animalID!", notes = "Get list of all stored GeneralHealthObservation against animalID!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByAnimalID(@PathParam("animalID") final String animalID) {
		List<GeneralHealthObservation> list = this.generalhealthobservationService.getByAnimalID(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOGeneralHealthObservationList generalhealthobservationListDTO = new DTOGeneralHealthObservationList();
		for (GeneralHealthObservation generalhealthobservation : list) {
			try{
				generalhealthobservationListDTO.add(generalhealthobservationEntityConverter.getDTOGeneralHealthObservation(generalhealthobservation));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(generalhealthobservationListDTO).build();
	}
	

	@DELETE
	@Path("/{ghoid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("ghoid") final String ghoid) {
		int result = this.generalhealthobservationService.delete(ghoid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create GeneralHealthObservation!", notes = "Creating an instance of GeneralHealthObservation!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOGeneralHealthObservation dtogeneralhealthobservation) {
		
		dtogeneralhealthobservation.setGhoid("GHO-"+System.currentTimeMillis());
			GeneralHealthObservation generalhealthobservation = null ;
			try{
				generalhealthobservation = generalhealthobservationEntityConverter.getGeneralHealthObservation(dtogeneralhealthobservation);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.generalhealthobservationService.post(generalhealthobservation);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{ghoid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update GeneralHealthObservation!", notes = "Updating an instance of GeneralHealthObservation !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("ghoid") final String ghoid, @Valid DTOGeneralHealthObservation dtogeneralhealthobservation) {
		dtogeneralhealthobservation.setGhoid(ghoid);
			GeneralHealthObservation generalhealthobservation = null ;
			try{
				generalhealthobservation = generalhealthobservationEntityConverter.getGeneralHealthObservation(dtogeneralhealthobservation);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.generalhealthobservationService.put(generalhealthobservation);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
