package com.ri.se.resources.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.resources.dto.DTOFarmer;
import com.ri.se.resources.dto.DTOFarmerList;
import com.ri.se.resources.dto.FarmerEntityConverter;
import com.ri.se.resources.dto.Message;
import com.ri.se.resources.persistance.Farmer;
import com.ri.se.resources.persistance.FarmerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/farmer")
@Api(value = "Farmer by RRB", description = "API used to provide CRUD operations for Farmer service ")
public class FarmerResource {

	private FarmerService farmerService;

	private FarmerEntityConverter farmerEntityConverter = new FarmerEntityConverter();

	public FarmerResource(FarmerService farmerService) {
		this.farmerService = farmerService;
	}

	@GET
	@Path("/roles")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given roles [String]!", notes = "Returns instance of roles!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getRoles() {

		String filePath = "roles.txt";
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when openning and fetching contents of role file !")).build();
		}
		ArrayList<String> array = null;
		try {
			System.out.println(new String(encoded, StandardCharsets.UTF_8));

			array = new ObjectMapper().readValue(new String(encoded, StandardCharsets.UTF_8), ArrayList.class);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching role list !")).build();
		}
		if (java.util.Objects.isNull(array)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK).entity(array).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Path("/{resourceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [Farmer]!", notes = "Returns instance of Farmer stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("resourceId") final String resourceId) {
		Farmer farmer = this.farmerService.get(resourceId);
		if (java.util.Objects.isNull(farmer)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK).entity(farmerEntityConverter.getDTOFarmer(farmer)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored Farmer!", notes = "Get list of all stored Farmer!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<Farmer> list = this.farmerService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOFarmerList farmerListDTO = new DTOFarmerList();
		for (Farmer farmer : list) {
			try {
				farmerListDTO.add(farmerEntityConverter.getDTOFarmer(farmer));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(farmerListDTO).build();
	}

	@DELETE
	@Path("/{resourceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("resourceId") final String resourceId) {
		int result = this.farmerService.delete(resourceId);
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
	@ApiOperation(value = "Create Farmer!", notes = "Creating an instance of Farmer!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOFarmer dtofarmer) {
		Farmer farmer = null;
		try {
			farmer = farmerEntityConverter.getFarmer(dtofarmer);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.farmerService.post(farmer);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{resourceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update Farmer!", notes = "Updating an instance of Farmer !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("resourceId") final String resourceId, @Valid DTOFarmer dtofarmer) {
		dtofarmer.setResourceId(resourceId);
		Farmer farmer = null;
		try {
			farmer = farmerEntityConverter.getFarmer(dtofarmer);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.farmerService.put(farmer);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/profile/{resourceId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update Farmer!", notes = "Updating an instance of Farmer !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response profile(@PathParam("resourceId") final String resourceId, @Valid DTOFarmer dtofarmer) {

		dtofarmer.setResourceId(resourceId);
		Farmer farmer = null;
		try {
			farmer = farmerEntityConverter.getFarmer(dtofarmer);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		Farmer temp = this.farmerService.get(farmer.getResourceId());
		if (!(farmer.getResourceId().equals(temp.getResourceId()) && farmer.getEmail().equals(temp.getEmail())
				&& farmer.getRole().equals(temp.getRole()))) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("You are not authorized to update profile !")).build();
		}
		int i = this.farmerService.put(farmer);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
}
