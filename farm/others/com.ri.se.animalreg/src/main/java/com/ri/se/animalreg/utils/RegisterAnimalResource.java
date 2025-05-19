package com.ri.se.animalreg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

import com.ri.se.animalreg.dto.DTORegisterAnimal;
import com.ri.se.animalreg.dto.DTORegisterAnimalList;
import com.ri.se.animalreg.dto.Message;
import com.ri.se.animalreg.dto.RegisterAnimalEntityConverter;
import com.ri.se.animalreg.persistance.RegisterAnimal;
import com.ri.se.animalreg.persistance.RegisterAnimalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/registeranimal")
@Api(value = "RegisterAnimal by RRB", description = "API used to provide CRUD operations for RegisterAnimal service ")
public class RegisterAnimalResource {

	private RegisterAnimalService registeranimalService;

	private RegisterAnimalEntityConverter registeranimalEntityConverter = new RegisterAnimalEntityConverter();

	public RegisterAnimalResource(RegisterAnimalService registeranimalService) {
		this.registeranimalService = registeranimalService;
		List<RegisterAnimal> list = this.registeranimalService.list();
		if (Objects.isNull(list) || list.size() == 0) {

			for (int i = 0; i < 20; i++) {
				RegisterAnimal registerAnimal = new RegisterAnimal();
				registerAnimal.setBreed("Neli");
				registerAnimal.setAnimalID("AR-" + System.currentTimeMillis());
				registerAnimal.setAnimalIDMother("AR-" + System.currentTimeMillis());
				registerAnimal.setDateOfBirth(new Date());
				registerAnimal.setStatus(Status.ACTIVE.value);
				
				this.registeranimalService.post(registerAnimal);
			}

		}
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
	 * String status = registeranimalService.performHealthCheck();
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
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [RegisterAnimal]!", notes = "Returns instance of RegisterAnimal stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("animalID") final String animalID) {
		RegisterAnimal registeranimal = this.registeranimalService.get(animalID);
		if (java.util.Objects.isNull(registeranimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(registeranimalEntityConverter.getDTORegisterAnimal(registeranimal)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [RegisterAnimal]!", notes = "Returns instance of RegisterAnimal stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimalByID(@PathParam("animalID") final String animalID) {
		RegisterAnimal registeranimal = this.registeranimalService.get(animalID);
		if (java.util.Objects.isNull(registeranimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(registeranimalEntityConverter.getDTORegisterAnimal(registeranimal)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}
	
	
	
	@GET
	@Path("/age")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored RegisterAnimal!", notes = "Get list of all stored RegisterAnimal!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<RegisterAnimal> list = this.registeranimalService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTORegisterAnimalList registeranimalListDTO = new DTORegisterAnimalList();
		for (RegisterAnimal registeranimal : list) {
			try {
				DTORegisterAnimal temp = registeranimalEntityConverter.getDTORegisterAnimal(registeranimal);
				temp.setDateOfBirth(calcdays(registeranimal.getDateOfBirth()));
				registeranimalListDTO.add(temp);
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(registeranimalListDTO).build();
	}
	public static String calcdays(Date date1) {
		Date date2 = new Date();
		long diff = date2.getTime() - date1.getTime();
		diff= (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1);
		int m, year;
		m = (int) diff;
		year = m / 365;
		long remDays = m % 365;
		double months = remDays / 31;
		remDays = remDays % 31;
		return year+"-"+ (int)months+"-"+ (int)remDays;
	}
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored RegisterAnimal by age!", notes = "Get list of all stored RegisterAnimal by age!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimalsByAge() {
		List<RegisterAnimal> list = this.registeranimalService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTORegisterAnimalList registeranimalListDTO = new DTORegisterAnimalList();
		for (RegisterAnimal registeranimal : list) {
			try {
				registeranimalListDTO.add(registeranimalEntityConverter.getDTORegisterAnimal(registeranimal));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(registeranimalListDTO).build();
	}

	@DELETE
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("animalID") final String animalID) {
		int result = this.registeranimalService.delete(animalID);
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
	@ApiOperation(value = "Create RegisterAnimal!", notes = "Creating an instance of RegisterAnimal!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTORegisterAnimal dtoregisteranimal) {

		//dtoregisteranimal.setAnimalID("AR-" + System.currentTimeMillis());

		RegisterAnimal registeranimal = null;
		try {
			registeranimal = registeranimalEntityConverter.getRegisterAnimal(dtoregisteranimal);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.registeranimalService.post(registeranimal);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update RegisterAnimal!", notes = "Updating an instance of RegisterAnimal !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("animalID") final String animalID, @Valid DTORegisterAnimal dtoregisteranimal) {
		dtoregisteranimal.setAnimalID(animalID);
		RegisterAnimal registeranimal = null;
		try {
			registeranimal = registeranimalEntityConverter.getRegisterAnimal(dtoregisteranimal);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.registeranimalService.put(registeranimal);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
	
	@PUT
	@Path("/status/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update RegisterAnimal!", notes = "Updating an instance of RegisterAnimal !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response chnageStatus(@PathParam("animalID") final String animalID, @Valid String status) {
		RegisterAnimal registeranimal = this.registeranimalService.get(animalID);
		if(Objects.isNull(registeranimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Requested animal does not exisit !")).build();
		}
		if(!exisits(status)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Invalid status !"))
					.build();
		}
		registeranimal.setStatus(status);
		int i = this.registeranimalService.put(registeranimal);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
	

	 private boolean exisits(String status) {
			switch (status) {
			case "In":
				return true;
			case "out":
				return true;
			case "Feeding":
				return true;
			case "Slaughter":
				return true;
			case "Deregister":
				return true;
			case "Active":
				return true;
			}
			return false;
		}
	@GET
	@Path("/dummy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored RegisterAnimal!", notes = "Get list of all stored RegisterAnimal!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response dummy() {
		List<RegisterAnimal> list = new ArrayList<RegisterAnimal>();
		ArrayList<String> array = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			RegisterAnimal registerAnimal = new RegisterAnimal();
			registerAnimal.setAnimalID("AR-123-345" + i);
			registerAnimal.setBreed("Neeli");
			list.add(registerAnimal);
		}
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Dummy list is empty !"))
					.build();
		}
		DTORegisterAnimalList registeranimalList = new DTORegisterAnimalList();
		for (RegisterAnimal registeranimal : list) {
			try {
				registeranimalList.add(registeranimalEntityConverter.getDTORegisterAnimal(registeranimal));
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(new Message("Dummy list is empty !")).build();
			}
		}
		return Response.status(Response.Status.OK).entity(registeranimalList).build();
	}

}