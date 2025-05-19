package com.ri.se.generalhealthexamination.utils;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.assignanimalstatus.persistance.AssignAnimalStatus;
import com.ri.se.assignanimalstatus.persistance.AssignAnimalStatusService;
import com.ri.se.generalhealthexamination.dto.DTOGeneralHealthExamination;
import com.ri.se.generalhealthexamination.dto.DTOGeneralHealthExaminationList;
import com.ri.se.generalhealthexamination.dto.GeneralHealthExaminationEntityConverter;
import com.ri.se.generalhealthexamination.dto.Message;
import com.ri.se.generalhealthexamination.persistance.GeneralHealthExamination;
import com.ri.se.generalhealthexamination.persistance.GeneralHealthExaminationService;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservation;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/generalhealthexamination")
@Api(value = "GeneralHealthExamination by RRB", description = "API used to provide CRUD operations for GeneralHealthExamination service ")
public class GeneralHealthExaminationResource {


	private GeneralHealthExaminationService generalhealthexaminationService;
	private GeneralHealthObservationService generalHealthObservationService;
	private AssignAnimalStatusService assignAnimalStatusService;

	private GeneralHealthExaminationEntityConverter generalhealthexaminationEntityConverter = new GeneralHealthExaminationEntityConverter();

		public GeneralHealthExaminationResource(GeneralHealthExaminationService generalhealthexaminationService, GeneralHealthObservationService generalHealthObservationService, AssignAnimalStatusService assignAnimalStatusService ){
		this.generalhealthexaminationService = generalhealthexaminationService;
		this.generalHealthObservationService = generalHealthObservationService;
		this.assignAnimalStatusService= assignAnimalStatusService;
	}

	@GET
	@Path("/{gaheid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [GeneralHealthExamination]!", notes = "Returns instance of GeneralHealthExamination stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("gaheid") final String gaheid) {
		GeneralHealthExamination generalhealthexamination = this.generalhealthexaminationService.get(gaheid);
		if (java.util.Objects.isNull(generalhealthexamination)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(generalhealthexaminationEntityConverter.getDTOGeneralHealthExamination( generalhealthexamination)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list stored against given animal identity [GeneralHealthExamination]!", notes = "Returns list of GeneralHealthExamination stored against given animal identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByAnimal(@PathParam("animalID") final String animalID) {
		
		List<GeneralHealthObservation> gahoList = this.generalHealthObservationService.getByAnimal(animalID); 
		if (java.util.Objects.isNull(gahoList)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		
		List<GeneralHealthExamination> list = this.generalhealthexaminationService.getByAnimal(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		
		DTOGeneralHealthExaminationList generalhealthexaminationListDTO = new DTOGeneralHealthExaminationList();
		
		for (GeneralHealthExamination generalhealthexamination : list) {
			try{
				generalhealthexaminationListDTO.add(generalhealthexaminationEntityConverter.getDTOGeneralHealthExamination(generalhealthexamination));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		
		for (GeneralHealthObservation generalHealthObservation: gahoList) {
			try{
				GeneralHealthExamination generalhealthexamination = new GeneralHealthExamination ();
				generalhealthexamination.setGaheid(generalHealthObservation.getGhoid());  
				generalhealthexamination.setGheDate(generalHealthObservation.getGheDate());
				generalhealthexamination.setWound( generalHealthObservation.getWound());
				generalhealthexamination.setObserver(generalHealthObservation.getObserver());  
				generalhealthexamination.setSwelling(generalHealthObservation.getSwelling());  				
				generalhealthexaminationListDTO.add(generalhealthexaminationEntityConverter.getDTOGeneralHealthExamination(generalhealthexamination));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(generalhealthexaminationListDTO).build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored GeneralHealthExamination!", notes = "Get list of all stored GeneralHealthExamination!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		

		/*List<GeneralHealthObservation> gahoList = this.generalHealthObservationService.list(); 
		if (java.util.Objects.isNull(gahoList)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}*/
		
		List<GeneralHealthExamination> list = this.generalhealthexaminationService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		
		DTOGeneralHealthExaminationList generalhealthexaminationListDTO = new DTOGeneralHealthExaminationList();
		
		/*
		 * for (GeneralHealthObservation generalHealthObservation: gahoList) { try{
		 * GeneralHealthExamination generalhealthexamination = new
		 * GeneralHealthExamination ();
		 * generalhealthexamination.setGaheid(generalHealthObservation.getGhoid());
		 * generalhealthexamination.setGheDate(generalHealthObservation.getGheDate());
		 * generalhealthexamination.setWound( generalHealthObservation.getWound());
		 * generalhealthexamination.setObserver(generalHealthObservation.getObserver());
		 * generalhealthexamination.setSwelling(generalHealthObservation.getSwelling());
		 * generalhealthexamination.setAnimalID(generalHealthObservation.getAnimalID());
		 * 
		 * generalhealthexaminationListDTO.add(generalhealthexaminationEntityConverter.
		 * getDTOGeneralHealthExamination(generalhealthexamination)); }catch(Exception
		 * exp){ return
		 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
		 * Message(exp.getMessage())).build(); } }
		 */
		
		for (GeneralHealthExamination generalhealthexamination : list) {
			try{
				generalhealthexaminationListDTO.add(generalhealthexaminationEntityConverter.getDTOGeneralHealthExamination(generalhealthexamination));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(generalhealthexaminationListDTO).build();
	}

	@DELETE
	@Path("/{gaheid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("gaheid") final String gaheid) {
		int result = this.generalhealthexaminationService.delete(gaheid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create GeneralHealthExamination!", notes = "Creating an instance of GeneralHealthExamination!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOGeneralHealthExamination dtogeneralhealthexamination) {
		dtogeneralhealthexamination.setGaheid("GHE-"+System.currentTimeMillis());
			GeneralHealthExamination generalhealthexamination = null ;
			try{
				generalhealthexamination = generalhealthexaminationEntityConverter.getGeneralHealthExamination(dtogeneralhealthexamination);				
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.generalhealthexaminationService.post(generalhealthexamination);
		if (i > 0) {
			// Save VC in the local report and then add reference in the role status
			String jsonHealthData = null;
			try {
				jsonHealthData = new ObjectMapper().writeValueAsString(generalhealthexamination);
			} catch (JsonProcessingException e) {
				
			}
			if(Objects.isNull(jsonHealthData)) {
				this.generalhealthexaminationService.delete(generalhealthexamination.getGaheid());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Health data json parsing issue !")).build();
			}
			String vcDir = "/home/ag/Desktop/RISE/development/formasAggregated/formas/data/vcs";//"/home/app/data/vcs";
			try {
				//VCManager vcManager = new VCManager(vcDir);
				String username= "";
				String password = "";
				//String storedVC = vcManager.createAndStoreVC(jsonHealthData, generalhealthexamination.getGaheid(), username, password);
				/*
				 * AssignAnimalStatus assignanimalstatus_ =
				 * assignAnimalStatusService.get(dtogeneralhealthexamination.getAssignedStatusId
				 * ()); assignanimalstatus_.setReportSubmitted(dtogeneralhealthexamination.
				 * getObserver());
				 * assignanimalstatus_.setReportReference(generalhealthexamination.getGaheid()+
				 * ":"); assignanimalstatus_.setSubmissionDate(new Date());
				 * assignAnimalStatusService.put(assignanimalstatus_);
				 */
				//generalhealthexamination
				return Response.status(Response.Status.OK).entity(dtogeneralhealthexamination).build();			
			} catch (Exception e) {
				this.generalhealthexaminationService.delete(generalhealthexamination.getGaheid());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("{Problems when storing health VC !")).build();
			}			
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{gaheid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update GeneralHealthExamination!", notes = "Updating an instance of GeneralHealthExamination !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("gaheid") final String gaheid, @Valid DTOGeneralHealthExamination dtogeneralhealthexamination) {
		dtogeneralhealthexamination.setGaheid(gaheid);
			GeneralHealthExamination generalhealthexamination = null ;
			try{
				generalhealthexamination = generalhealthexaminationEntityConverter.getGeneralHealthExamination(dtogeneralhealthexamination);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.generalhealthexaminationService.put(generalhealthexamination);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
