package com.ri.se.breed.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

import com.ri.se.breed.dto.BreedEntityConverter;
import com.ri.se.breed.dto.DTOBreed;
import com.ri.se.breed.dto.DTOBreedList;
import com.ri.se.breed.dto.Message;
import com.ri.se.breed.persistance.Breed;
import com.ri.se.breed.persistance.BreedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/breed")
@Api(value = "Breed by RRB", description = "API used to provide CRUD operations for Breed service ")
public class BreedResource {

	private BreedService breedService;

	private BreedEntityConverter breedEntityConverter = new BreedEntityConverter();

	public BreedResource(BreedService breedService) {
		this.breedService = breedService;
		List<Breed> list = this.breedService.list();
		if (Objects.isNull(list) || list.size() == 0) {
			try {
				ArrayList<String> breeds = new ReaderUtils().getFeeds("breedsdata.txt");
				for (String breed : breeds) {
					breed = breed.trim();
					Breed b = new Breed();
					b.setBreedname(breed);
					this.breedService.post(b);	
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
	}

	private String usingBufferedReader(String filePath) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			contentBuilder.append(sCurrentLine);
		}
		return contentBuilder.toString();
	}
	
	@GET
	@Path("/{breedname}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [Breed]!", notes = "Returns instance of Breed stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("breedname") final String breedname) {
		Breed breed = this.breedService.get(breedname);
		if (java.util.Objects.isNull(breed)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK).entity(breedEntityConverter.getDTOBreed(breed)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored Breed!", notes = "Get list of all stored Breed!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<Breed> list = this.breedService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOBreedList breedListDTO = new DTOBreedList();
		for (Breed breed : list) {
			try {
				breedListDTO.add(breedEntityConverter.getDTOBreed(breed));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(breedListDTO).build();
	}

	@DELETE
	@Path("/{breedname}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("breedname") final String breedname) {
		int result = this.breedService.delete(breedname);
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
	@ApiOperation(value = "Create Breed!", notes = "Creating an instance of Breed!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOBreed dtobreed) {
		Breed breed = null;
		try {
			breed = breedEntityConverter.getBreed(dtobreed);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.breedService.post(breed);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{breedname}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update Breed!", notes = "Updating an instance of Breed !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("breedname") final String breedname, @Valid DTOBreed dtobreed) {
		dtobreed.setBreedname(breedname);
		Breed breed = null;
		try {
			breed = breedEntityConverter.getBreed(dtobreed);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.breedService.put(breed);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
}
