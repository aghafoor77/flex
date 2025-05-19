package com.ri.se.feedtype.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

import com.ri.se.feedtype.dto.DTOFeedType;
import com.ri.se.feedtype.dto.DTOFeedTypeList;
import com.ri.se.feedtype.dto.FeedTypeEntityConverter;
import com.ri.se.feedtype.dto.Message;
import com.ri.se.feedtype.persistance.FeedType;
import com.ri.se.feedtype.persistance.FeedTypeService;
import com.ri.se.generaldata.main.ReaderUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/feedtype")
@Api(value = "FeedType by RRB", description = "API used to provide CRUD operations for FeedType service ")
public class FeedTypeResource {


	private FeedTypeService feedtypeService;

	private FeedTypeEntityConverter feedtypeEntityConverter = new FeedTypeEntityConverter();

		public FeedTypeResource(FeedTypeService feedtypeService){
		this.feedtypeService = feedtypeService;
		List<FeedType> list = this.feedtypeService.list();
		if (Objects.isNull(list) || list.size() == 0) {
			try {
				
				ArrayList<String> feedtypes = new ReaderUtils().getFeeds("feedtype.txt");
				for (String feedtype : feedtypes) {
					feedtype = feedtype.trim();
					FeedType b = new FeedType();
					b.setFeedName(feedtype);
					b.setCreationDate(new Date());
					this.feedtypeService.post(b);	
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
	}

		@GET
		@Path("/health")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "Returns OK report if service is running !", notes = "Returns OK report if service is running !", response = String.class)
		@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
				@ApiResponse(code = 500, message = "Internal Server error !") })
		public Response health() {
			
				return Response.status(Response.Status.OK).entity(new  Message("OK health report !")).build();

		}

		@GET
		@Path("/deephealth")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "Returns OK report if service is running !", notes = "Returns OK report if service is running !", response = String.class)
		@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
				@ApiResponse(code = 500, message = "Internal Server error !") })
		public Response deephealth() {
			
			String status = feedtypeService.performHealthCheck();
			if(!Objects.isNull(status)) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new  Message(status)).build();
		
			}
			return Response.status(Response.Status.OK).entity(new  Message("OK deep health report !")).build();
			
		}
		
	@GET
	@Path("/{feedName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [FeedType]!", notes = "Returns instance of FeedType stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("feedName") final String feedName) {
		FeedType feedtype = this.feedtypeService.get(feedName);
		if (java.util.Objects.isNull(feedtype)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK).entity(feedtypeEntityConverter.getDTOFeedType( feedtype)).build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}


	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored FeedType!", notes = "Get list of all stored FeedType!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<FeedType> list = this.feedtypeService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOFeedTypeList feedtypeListDTO = new DTOFeedTypeList();
		for (FeedType feedtype : list) {
			try{
				feedtypeListDTO.add(feedtypeEntityConverter.getDTOFeedType(feedtype));
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		}
		return Response.status(Response.Status.OK).entity(feedtypeListDTO).build();
	}

	@DELETE
	@Path("/{feedName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("feedName") final String feedName) {
		int result = this.feedtypeService.delete(feedName);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create FeedType!", notes = "Creating an instance of FeedType!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOFeedType dtofeedtype) {
			FeedType feedtype = null ;
			try{
				feedtype = feedtypeEntityConverter.getFeedType(dtofeedtype);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.feedtypeService.post(feedtype);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
		}
	}

	@PUT
	@Path("/{feedName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update FeedType!", notes = "Updating an instance of FeedType !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("feedName") final String feedName, @Valid DTOFeedType dtofeedtype) {
		dtofeedtype.setFeedName(feedName);
			FeedType feedtype = null ;
			try{
				feedtype = feedtypeEntityConverter.getFeedType(dtofeedtype);
			}catch(Exception exp){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
			}
		int i = this.feedtypeService.put(feedtype);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when updating entry. Please contact system admin for more information!")).build();
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
}
