package transportedcattle.utils;

import transportedcattle.persistance.*;
import transportedcattle.dto.*;
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

@Path("/v1/transportedcattle")
@Api(value = "TransportedCattle by RRB", description = "API used to provide CRUD operations for TransportedCattle service ")
public class TransportedCattleResource {


	private TransportedCattleService transportedcattleService;

	private TransportedCattleEntityConverter transportedcattleEntityConverter = new TransportedCattleEntityConverter();

		public TransportedCattleResource(TransportedCattleService transportedcattleService){
		this.transportedcattleService = transportedcattleService;
	}

	@GET
	@Path("/{TCID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TransportedCattle]!", notes = "Returns instance of TransportedCattle stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("TCID") final String TCID) {
		TransportedCattle transportedcattle = this.transportedcattleService.get(TCID);
		if (java.util.Objects.isNull(transportedcattle)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(transportedcattleEntityConverter.getDTOTransportedCattle( transportedcattle)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}
	

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored TransportedCattle!", notes = "Get list of all stored TransportedCattle!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<TransportedCattle> list = this.transportedcattleService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOTransportedCattleList transportedcattleListDTO = new DTOTransportedCattleList();
		for (TransportedCattle transportedcattle : list) {
			try{
				transportedcattleListDTO.add(transportedcattleEntityConverter.getDTOTransportedCattle(transportedcattle));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(transportedcattleListDTO).build();
	}

	@DELETE
	@Path("/{TCID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("TCID") final String TCID) {
		int result = this.transportedcattleService.delete(TCID);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create TransportedCattle!", notes = "Creating an instance of TransportedCattle!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOTransportedCattle dtotransportedcattle) {
			TransportedCattle transportedcattle = null ;
			try{
				transportedcattle = transportedcattleEntityConverter.getTransportedCattle(dtotransportedcattle);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.transportedcattleService.post(transportedcattle);
		if (i > 0) {
			try {
				return Response.status(Response.Status.OK).entity(transportedcattleEntityConverter.getDTOTransportedCattle(transportedcattle)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
			}
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{TCID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update TransportedCattle!", notes = "Updating an instance of TransportedCattle !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("TCID") final String TCID, @Valid DTOTransportedCattle dtotransportedcattle) {
		dtotransportedcattle.setTCID(TCID);
			TransportedCattle transportedcattle = null ;
			try{
				transportedcattle = transportedcattleEntityConverter.getTransportedCattle(dtotransportedcattle);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.transportedcattleService.put(transportedcattle);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
