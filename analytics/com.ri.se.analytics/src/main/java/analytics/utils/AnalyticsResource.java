package analytics.utils;

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

import analytics.dto.AnalyticsEntityConverter;
import analytics.dto.DTOAnalytics;
import analytics.dto.DTOAnalyticsList;
import analytics.dto.Message;
import analytics.dto.graphs.GraphData;
import analytics.graphs.utils.ADGGraphData;
import analytics.persistance.Analytics;
import analytics.persistance.AnalyticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/analytics")
@Api(value = "Analytics by RRB", description = "API used to provide CRUD operations for Analytics service ")
public class AnalyticsResource {

	private AnalyticsService analyticsService;

	private AnalyticsEntityConverter analyticsEntityConverter = new AnalyticsEntityConverter();

	public AnalyticsResource(AnalyticsService analyticsService) {
		this.analyticsService = analyticsService;
	}

	@GET
	@Path("/{AID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [Analytics]!", notes = "Returns instance of Analytics stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("AID") final String AID) {
		Analytics analytics = this.analyticsService.get(AID);
		if (java.util.Objects.isNull(analytics)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK).entity(analyticsEntityConverter.getDTOAnalytics(analytics))
					.build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored Analytics!", notes = "Get list of all stored Analytics!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<Analytics> list = this.analyticsService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAnalyticsList analyticsListDTO = new DTOAnalyticsList();
		for (Analytics analytics : list) {
			try {
				analyticsListDTO.add(analyticsEntityConverter.getDTOAnalytics(analytics));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(analyticsListDTO).build();
	}

	@GET
	@Path("/ADG")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored ADG data for Analytics!", notes = "Get list of all stored ADG data for Analytics!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response ADG() {
		List<Analytics> list = this.analyticsService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when claculating ADG !")).build();
		}
		ADGGraphData adgGraphData = new ADGGraphData();
		GraphData graphData = adgGraphData.getGraphData();
		System.out.println(graphData.getData().size());
		return Response.status(Response.Status.OK).entity(graphData).build();
	}

	@DELETE
	@Path("/{AID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("AID") final String AID) {
		int result = this.analyticsService.delete(AID);
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
	@ApiOperation(value = "Create Analytics!", notes = "Creating an instance of Analytics!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOAnalytics dtoanalytics) {
		Analytics analytics = null;
		try {
			analytics = analyticsEntityConverter.getAnalytics(dtoanalytics);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.analyticsService.post(analytics);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(analytics).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{AID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update Analytics!", notes = "Updating an instance of Analytics !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("AID") final String AID, @Valid DTOAnalytics dtoanalytics) {
		dtoanalytics.setAID(AID);
		Analytics analytics = null;
		try {
			analytics = analyticsEntityConverter.getAnalytics(dtoanalytics);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.analyticsService.put(analytics);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
}
