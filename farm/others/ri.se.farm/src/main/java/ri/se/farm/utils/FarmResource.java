package ri.se.farm.utils;

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
import ri.se.farm.dto.*;
import ri.se.farm.persistance.*;

@Path("/v1/farm")
@Api(value = "Farm by RRB", description = "API used to provide CRUD operations for Farm service ")
public class FarmResource {


	private FarmService farmService;

	private FarmEntityConverter farmEntityConverter = new FarmEntityConverter();

		public FarmResource(FarmService farmService){
		this.farmService = farmService;
	}

	@GET
	@Path("/{farmId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [Farm]!", notes = "Returns instance of Farm stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("farmId") final String farmId) {
		Farm farm = this.farmService.get(farmId);
		if (java.util.Objects.isNull(farm)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(farmEntityConverter.getDTOFarm( farm)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}


	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored Farm!", notes = "Get list of all stored Farm!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<Farm> list = this.farmService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOFarmList farmListDTO = new DTOFarmList();
		for (Farm farm : list) {
			try{
				farmListDTO.add(farmEntityConverter.getDTOFarm(farm));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(farmListDTO).build();
	}

	@DELETE
	@Path("/{farmId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("farmId") final String farmId) {
		int result = this.farmService.delete(farmId);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create Farm!", notes = "Creating an instance of Farm!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOFarm dtofarm) {
			Farm farm = null ;
			try{
				farm = farmEntityConverter.getFarm(dtofarm);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.farmService.post(farm);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{farmId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update Farm!", notes = "Updating an instance of Farm !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("farmId") final String farmId, @Valid DTOFarm dtofarm) {
		dtofarm.setFarmId(farmId);
			Farm farm = null ;
			try{
				farm = farmEntityConverter.getFarm(dtofarm);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.farmService.put(farm);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}
}
