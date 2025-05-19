package com.ri.se.drugstreatments.utils;

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

import com.ri.se.drugstreatments.dto.DTODrugsTreatments;
import com.ri.se.drugstreatments.dto.DTODrugsTreatmentsList;
import com.ri.se.drugstreatments.dto.DrugsTreatmentsEntityConverter;
import com.ri.se.drugstreatments.dto.Message;
import com.ri.se.drugstreatments.persistance.DrugsTreatments;
import com.ri.se.drugstreatments.persistance.DrugsTreatmentsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/drugstreatments")
@Api(value = "DrugsTreatments by RRB", description = "API used to provide CRUD operations for DrugsTreatments service ")
public class DrugsTreatmentsResource {


	private DrugsTreatmentsService drugstreatmentsService;

	private DrugsTreatmentsEntityConverter drugstreatmentsEntityConverter = new DrugsTreatmentsEntityConverter();

		public DrugsTreatmentsResource(DrugsTreatmentsService drugstreatmentsService){
		this.drugstreatmentsService = drugstreatmentsService;
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
	 * String status = drugstreatmentsService.performHealthCheck();
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
	@Path("/{dtid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [DrugsTreatments]!", notes = "Returns instance of DrugsTreatments stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("dtid") final String dtid) {
		DrugsTreatments drugstreatments = this.drugstreatmentsService.get(dtid);
		if (java.util.Objects.isNull(drugstreatments)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(drugstreatmentsEntityConverter.getDTODrugsTreatments( drugstreatments)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}


	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored DrugsTreatments!", notes = "Get list of all stored DrugsTreatments!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<DrugsTreatments> list = this.drugstreatmentsService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTODrugsTreatmentsList drugstreatmentsListDTO = new DTODrugsTreatmentsList();
		for (DrugsTreatments drugstreatments : list) {
			try{
				drugstreatmentsListDTO.add(drugstreatmentsEntityConverter.getDTODrugsTreatments(drugstreatments));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(drugstreatmentsListDTO).build();
	}

	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored DrugsTreatments against an animal!", notes = "Get list of all stored DrugsTreatments against an animal!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response listByAnimal(@PathParam("animalID") final String animalID) {
		
		List<DrugsTreatments> list = this.drugstreatmentsService.listByAnimal(animalID);
		
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTODrugsTreatmentsList drugstreatmentsListDTO = new DTODrugsTreatmentsList();
		for (DrugsTreatments drugstreatments : list) {
			try{
				drugstreatmentsListDTO.add(drugstreatmentsEntityConverter.getDTODrugsTreatments(drugstreatments));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(drugstreatmentsListDTO).build();
	}
	
	@DELETE
	@Path("/{dtid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("dtid") final String dtid) {
		int result = this.drugstreatmentsService.delete(dtid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create DrugsTreatments!", notes = "Creating an instance of DrugsTreatments!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTODrugsTreatments dtodrugstreatments) {
		
		dtodrugstreatments.setDtid("DT-"+System.currentTimeMillis());
			DrugsTreatments drugstreatments = null ;
			try{
				drugstreatments = drugstreatmentsEntityConverter.getDrugsTreatments(dtodrugstreatments);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.drugstreatmentsService.post(drugstreatments);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{dtid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update DrugsTreatments!", notes = "Updating an instance of DrugsTreatments !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("dtid") final String dtid, @Valid DTODrugsTreatments dtodrugstreatments) {
		dtodrugstreatments.setDtid(dtid);
			DrugsTreatments drugstreatments = null ;
			try{
				drugstreatments = drugstreatmentsEntityConverter.getDrugsTreatments(dtodrugstreatments);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.drugstreatmentsService.put(drugstreatments);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
