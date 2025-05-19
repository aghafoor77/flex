package com.ri.se.reportslaughterhouse.utils;

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

import com.ri.se.reportslaughterhouse.dto.DTOReportSlaughterhouse;
import com.ri.se.reportslaughterhouse.dto.DTOReportSlaughterhouseList;
import com.ri.se.reportslaughterhouse.dto.ReportSlaughterhouseEntityConverter;
import com.ri.se.reportslaughterhouse.persistance.ReportSlaughterhouse;
import com.ri.se.reportslaughterhouse.persistance.ReportSlaughterhouseService;
import com.ri.se.reportslaughterhouseanimals.dto.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/reportslaughterhouse")
@Api(value = "ReportSlaughterhouse by RRB", description = "API used to provide CRUD operations for ReportSlaughterhouse service ")
public class ReportSlaughterhouseResource {

	private ReportSlaughterhouseService reportslaughterhouseService;

	private ReportSlaughterhouseEntityConverter reportslaughterhouseEntityConverter = new ReportSlaughterhouseEntityConverter();

	public ReportSlaughterhouseResource(ReportSlaughterhouseService reportslaughterhouseService) {
		this.reportslaughterhouseService = reportslaughterhouseService;
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
	 * String status = reportslaughterhouseService.performHealthCheck(); if
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
	@Path("/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [ReportSlaughterhouse]!", notes = "Returns instance of ReportSlaughterhouse stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("rid") final String rid) {
		ReportSlaughterhouse reportslaughterhouse = this.reportslaughterhouseService.get(rid);
		if (java.util.Objects.isNull(reportslaughterhouse)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(reportslaughterhouseEntityConverter.getDTOReportSlaughterhouse(reportslaughterhouse))
					.build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored ReportSlaughterhouse!", notes = "Get list of all stored ReportSlaughterhouse!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<ReportSlaughterhouse> list = this.reportslaughterhouseService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOReportSlaughterhouseList reportslaughterhouseListDTO = new DTOReportSlaughterhouseList();
		for (ReportSlaughterhouse reportslaughterhouse : list) {
			try {
				reportslaughterhouseListDTO
						.add(reportslaughterhouseEntityConverter.getDTOReportSlaughterhouse(reportslaughterhouse));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(reportslaughterhouseListDTO).build();
	}

	@DELETE
	@Path("/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("rid") final String rid) {
		int result = this.reportslaughterhouseService.delete(rid);
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
	@ApiOperation(value = "Create ReportSlaughterhouse!", notes = "Creating an instance of ReportSlaughterhouse!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOReportSlaughterhouse dtoreportslaughterhouse) {
		dtoreportslaughterhouse.setRid("RS-" + System.currentTimeMillis());
		dtoreportslaughterhouse.setEmployeeID("HR-CHNAGEME");
		dtoreportslaughterhouse.setResponse("Pending");
		ReportSlaughterhouse reportslaughterhouse = null;
		try {
			reportslaughterhouse = reportslaughterhouseEntityConverter.getReportSlaughterhouse(dtoreportslaughterhouse);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.reportslaughterhouseService.post(reportslaughterhouse);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update ReportSlaughterhouse!", notes = "Updating an instance of ReportSlaughterhouse !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("rid") final String rid, @Valid DTOReportSlaughterhouse dtoreportslaughterhouse) {
		dtoreportslaughterhouse.setRid(rid);
		ReportSlaughterhouse reportslaughterhouse = null;
		try {
			reportslaughterhouse = reportslaughterhouseEntityConverter.getReportSlaughterhouse(dtoreportslaughterhouse);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.reportslaughterhouseService.put(reportslaughterhouse);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/cancel/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update ReportSlaughterhouse!", notes = "Updating an instance of ReportSlaughterhouse !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response cancel(@PathParam("rid") final String rid) {

		ReportSlaughterhouse reportslaughterhouse = this.reportslaughterhouseService.get(rid);

		if (Objects.isNull(reportslaughterhouse)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Slaughterhouse report not found !")).build();
		}
		reportslaughterhouse.setResponse("Cancel");
		int i = this.reportslaughterhouseService.put(reportslaughterhouse);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message(
							"Problems when canceling entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/done/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update ReportSlaughterhouse!", notes = "Updating an instance of ReportSlaughterhouse !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response done(@PathParam("rid") final String rid) {

		ReportSlaughterhouse reportslaughterhouse = this.reportslaughterhouseService.get(rid);

		if (Objects.isNull(reportslaughterhouse)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Slaughterhouse report not found !")).build();
		}
		reportslaughterhouse.setResponse("Done");
		int i = this.reportslaughterhouseService.put(reportslaughterhouse);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when closinf entry. Please contact system admin for more information!"))
					.build();
		}
	}
}