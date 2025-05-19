package com.ri.se.reportslaughterhouseanimals.utils;

import java.text.SimpleDateFormat;
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

import com.ri.se.reportslaughterhouseanimals.dto.DTOReportSlaughterhouseAnimals;
import com.ri.se.reportslaughterhouseanimals.dto.DTOReportSlaughterhouseAnimalsList;
import com.ri.se.reportslaughterhouseanimals.dto.Message;
import com.ri.se.reportslaughterhouseanimals.dto.ReportSlaughterhouseAnimalsEntityConverter;
import com.ri.se.reportslaughterhouseanimals.persistance.ReportSlaughterhouseAnimals;
import com.ri.se.reportslaughterhouseanimals.persistance.ReportSlaughterhouseAnimalsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/reportslaughterhouseanimals")
@Api(value = "ReportSlaughterhouseAnimals by RRB", description = "API used to provide CRUD operations for ReportSlaughterhouseAnimals service ")
public class ReportSlaughterhouseAnimalsResource {


	private ReportSlaughterhouseAnimalsService reportslaughterhouseanimalsService;

	private ReportSlaughterhouseAnimalsEntityConverter reportslaughterhouseanimalsEntityConverter = new ReportSlaughterhouseAnimalsEntityConverter();

		public ReportSlaughterhouseAnimalsResource(ReportSlaughterhouseAnimalsService reportslaughterhouseanimalsService){
		this.reportslaughterhouseanimalsService = reportslaughterhouseanimalsService;
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
	 * String status = reportslaughterhouseanimalsService.performHealthCheck(); if
	 * (!Objects.isNull(status)) { return
	 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
	 * Message(status)).build();
	 * 
	 * } return Response.status(Response.Status.OK).entity(new
	 * Message("OK deep health report !")).build();
	 * 
	 * }
	 */
		
	@GET
	@Path("/{rsaid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [ReportSlaughterhouseAnimals]!", notes = "Returns instance of ReportSlaughterhouseAnimals stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("rsaid") final String rsaid) {
		ReportSlaughterhouseAnimals reportslaughterhouseanimals = this.reportslaughterhouseanimalsService.get(rsaid);
		if (java.util.Objects.isNull(reportslaughterhouseanimals)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(reportslaughterhouseanimalsEntityConverter.getDTOReportSlaughterhouseAnimals( reportslaughterhouseanimals)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}


	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored ReportSlaughterhouseAnimals!", notes = "Get list of all stored ReportSlaughterhouseAnimals!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<ReportSlaughterhouseAnimals> list = this.reportslaughterhouseanimalsService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOReportSlaughterhouseAnimalsList reportslaughterhouseanimalsListDTO = new DTOReportSlaughterhouseAnimalsList();
		for (ReportSlaughterhouseAnimals reportslaughterhouseanimals : list) {
			try{
				reportslaughterhouseanimalsListDTO.add(reportslaughterhouseanimalsEntityConverter.getDTOReportSlaughterhouseAnimals(reportslaughterhouseanimals));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(reportslaughterhouseanimalsListDTO).build();
	}
	
	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored ReportSlaughterhouseAnimals by animal ID!", notes = "Get list of all stored ReportSlaughterhouseAnimals by animal ID!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByAnimalID(@PathParam("animalID") final String animalID) {
		List<ReportSlaughterhouseAnimals> list = this.reportslaughterhouseanimalsService.getByAnimalID(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOReportSlaughterhouseAnimalsList reportslaughterhouseanimalsListDTO = new DTOReportSlaughterhouseAnimalsList();
		for (ReportSlaughterhouseAnimals reportslaughterhouseanimals : list) {
			try{
				reportslaughterhouseanimalsListDTO.add(reportslaughterhouseanimalsEntityConverter.getDTOReportSlaughterhouseAnimals(reportslaughterhouseanimals));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(reportslaughterhouseanimalsListDTO).build();
	}
	
	
	@GET
	@Path("/animals/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored ReportSlaughterhouseAnimals against RID!", notes = "Get list of all stored ReportSlaughterhouseAnimals against RID!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimals(@PathParam("rid") final String rid) {

		List<ReportSlaughterhouseAnimals> reportSlaughterhouseAnimalsList = this.reportslaughterhouseanimalsService.getAnimalsByRID(rid);
		StringList ls = new StringList();
		List<String> list = new ArrayList<String>();

		for (ReportSlaughterhouseAnimals af : reportSlaughterhouseAnimalsList ) {
			list.add(af.getAnimalID());
		}

		ls.setData(list);

		if (java.util.Objects.isNull(ls)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}

		try {
			return Response.status(Response.Status.OK).entity(ls).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}
	
	
	

	@DELETE
	@Path("/{rsaid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("rsaid") final String rsaid) {
		int result = this.reportslaughterhouseanimalsService.delete(rsaid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create ReportSlaughterhouseAnimals!", notes = "Creating an instance of ReportSlaughterhouseAnimals!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOReportSlaughterhouseAnimals dtoreportslaughterhouseanimals) {
			ReportSlaughterhouseAnimals reportslaughterhouseanimals = null ;
			try{
				reportslaughterhouseanimals = reportslaughterhouseanimalsEntityConverter.getReportSlaughterhouseAnimals(dtoreportslaughterhouseanimals);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.reportslaughterhouseanimalsService.post(reportslaughterhouseanimals);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@POST
	@Path("/animals/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create ReportSlaughterhouseAnimals!", notes = "Creating an instance of ReportSlaughterhouseAnimals!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response postByRid(@PathParam("rid") final String rid, @Valid StringList animals) {
			
		this.reportslaughterhouseanimalsService.deleteByRID(rid);
		for (String animalID : animals.getData()) {
			DTOReportSlaughterhouseAnimals dtoReportSlaughterhouseAnimals = new DTOReportSlaughterhouseAnimals();
			ReportSlaughterhouseAnimals reportslaughterhouseanimals= null;
			try {
				dtoReportSlaughterhouseAnimals.setEmployeeID("HR-Test");
				dtoReportSlaughterhouseAnimals.setRid(rid);
				dtoReportSlaughterhouseAnimals.setAnimalID(animalID);
				dtoReportSlaughterhouseAnimals.setRsaid("SHA-" + System.currentTimeMillis());
				dtoReportSlaughterhouseAnimals.setSelectionDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				reportslaughterhouseanimals = reportslaughterhouseanimalsEntityConverter.getReportSlaughterhouseAnimals(dtoReportSlaughterhouseAnimals);
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
			int i = this.reportslaughterhouseanimalsService.post(reportslaughterhouseanimals);
			if (i > 0) {
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(new Message(
								"Problems when updating entry. Please contact system admin for more information!"))
						.build();
			}
		}
		return Response.status(Response.Status.OK).build();		
	}
	
	@PUT
	@Path("/{rsaid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update ReportSlaughterhouseAnimals!", notes = "Updating an instance of ReportSlaughterhouseAnimals !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("rsaid") final String rsaid, @Valid DTOReportSlaughterhouseAnimals dtoreportslaughterhouseanimals) {
		dtoreportslaughterhouseanimals.setRsaid(rsaid);
			ReportSlaughterhouseAnimals reportslaughterhouseanimals = null ;
			try{
				reportslaughterhouseanimals = reportslaughterhouseanimalsEntityConverter.getReportSlaughterhouseAnimals(dtoreportslaughterhouseanimals);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.reportslaughterhouseanimalsService.put(reportslaughterhouseanimals);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
