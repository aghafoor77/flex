package com.ri.se.assignfeed.utils;

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

import com.ri.se.assignfeed.dto.AssignFeedEntityConverter;
import com.ri.se.assignfeed.dto.DTOAssignFeed;
import com.ri.se.assignfeed.dto.DTOAssignFeedList;
import com.ri.se.assignfeed.dto.Message;
import com.ri.se.assignfeed.persistance.AssignFeed;
import com.ri.se.assignfeed.persistance.AssignFeedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/assignfeed")
@Api(value = "AssignFeed by RRB", description = "API used to provide CRUD operations for AssignFeed service ")
public class AssignFeedResource {

	private AssignFeedService assignfeedService;

	private AssignFeedEntityConverter assignfeedEntityConverter = new AssignFeedEntityConverter();

	public AssignFeedResource(AssignFeedService assignfeedService) {
		this.assignfeedService = assignfeedService;
	}

	@GET
	@Path("/{afid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AssignFeed]!", notes = "Returns instance of AssignFeed stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("afid") final String afid) {
		AssignFeed assignfeed = this.assignfeedService.get(afid);

		if (java.util.Objects.isNull(assignfeed)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK).entity(assignfeedEntityConverter.getDTOAssignFeed(assignfeed))
					.build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given animal identity [AR]!", notes = "Returns instance of AssignFeed stored against given animal identity!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getFeedByAnimal(@PathParam("animalID") final String animalID) {
		List<AssignFeed> assignfeedByAnimal = this.assignfeedService.getFeedByAnimal(animalID);

		if (java.util.Objects.isNull(assignfeedByAnimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		DTOAssignFeedList assignfeedListDTO = new DTOAssignFeedList();
		for (AssignFeed assignfeed : assignfeedByAnimal) {
			try {
				assignfeedListDTO.add(assignfeedEntityConverter.getDTOAssignFeed(assignfeed));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(assignfeedListDTO).build();
	}

	@GET
	@Path("/fp1/{fpid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AssignFeed]!", notes = "Returns instance of AssignFeed stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimals(@PathParam("fpid") final String fpid) {

		List<AssignFeed> assignfeed = this.assignfeedService.getAnimalsByFP(fpid);
		StringList ls = new StringList();
		List<String> list = new ArrayList<String>();
		String expFinish = "";
		for (AssignFeed af : assignfeed) {
			list.add(af.getAssignedTo());
		}
		String small= "yyyy-MM-dd";	
		SimpleDateFormat dateFormat = new SimpleDateFormat(small);
		if (assignfeed.size() > 0) {
			if(!Objects.isNull(assignfeed.get(0).getExpectedFinishDate()))
				ls.setExpectedFinishDate(dateFormat.format(assignfeed.get(0).getExpectedFinishDate()));
			else 
				ls.setExpectedFinishDate("-");	
			
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

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored AssignFeed!", notes = "Get list of all stored AssignFeed!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<AssignFeed> list = this.assignfeedService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAssignFeedList assignfeedListDTO = new DTOAssignFeedList();
		for (AssignFeed assignfeed : list) {
			try {
				assignfeedListDTO.add(assignfeedEntityConverter.getDTOAssignFeed(assignfeed));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(assignfeedListDTO).build();
	}

	@DELETE
	@Path("/{afid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("afid") final String afid) {
		int result = this.assignfeedService.delete(afid);
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
	@ApiOperation(value = "Create AssignFeed!", notes = "Creating an instance of AssignFeed!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOAssignFeed dtoassignfeed) {
		AssignFeed assignfeed = null;
		try {
			assignfeed = assignfeedEntityConverter.getAssignFeed(dtoassignfeed);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.assignfeedService.post(assignfeed);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(assignfeed).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@POST
	@Path("/fp/{fpid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create AssignFeed!", notes = "Creating an instance of AssignFeed!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response postByAnimal(@PathParam("fpid") final String fpid, @Valid StringList data) {
		this.assignfeedService.deleteByFP(fpid);

		for (String animalID : data.getData()) {
			DTOAssignFeed dtoAssignFeed = new DTOAssignFeed();
			AssignFeed assignfeed = null;
			try {
				dtoAssignFeed.setAssignedBy("HR-Test");
				dtoAssignFeed.setFpid(fpid);
				dtoAssignFeed.setAssignedTo(animalID);
				dtoAssignFeed.setExpectedFinishDate(data.getExpectedFinishDate());
				dtoAssignFeed.setAfid("AFP-" + System.currentTimeMillis());
				dtoAssignFeed.setAssignedDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				assignfeed = assignfeedEntityConverter.getAssignFeed(dtoAssignFeed);
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
			int i = this.assignfeedService.post(assignfeed);
			if (i > 0) {
				return Response.status(Response.Status.OK).entity(assignfeed).build();
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
	@Path("/{afid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update AssignFeed!", notes = "Updating an instance of AssignFeed !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("afid") final String afid, @Valid DTOAssignFeed dtoassignfeed) {
		dtoassignfeed.setAfid(afid);
		AssignFeed assignfeed = null;
		try {
			assignfeed = assignfeedEntityConverter.getAssignFeed(dtoassignfeed);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.assignfeedService.put(assignfeed);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
}
