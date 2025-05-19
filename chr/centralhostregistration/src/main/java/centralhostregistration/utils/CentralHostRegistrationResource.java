package centralhostregistration.utils;

import java.io.IOException;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import centralhostregistration.dto.CentralHostRegistrationEntityConverter;
import centralhostregistration.dto.ConfigUnit;
import centralhostregistration.dto.DTOCentralHostRegistration;
import centralhostregistration.dto.DTOCentralHostRegistrationList;
import centralhostregistration.dto.Message;
import centralhostregistration.persistance.CentralHostRegistration;
import centralhostregistration.persistance.CentralHostRegistrationService;
import centralhostregistration.utils.ecaccts.ECAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/centralhostregistration")
@Api(value = "CentralHostRegistration by RRB", description = "API used to provide CRUD operations for CentralHostRegistration service ")
public class CentralHostRegistrationResource {

	private CentralHostRegistrationService centralhostregistrationService;

	private CentralHostRegistrationEntityConverter centralhostregistrationEntityConverter = new CentralHostRegistrationEntityConverter();

	public CentralHostRegistrationResource(CentralHostRegistrationService centralhostregistrationService) {
		this.centralhostregistrationService = centralhostregistrationService;
	}

	@GET
	@Path("/{hostname}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [CentralHostRegistration]!", notes = "Returns instance of CentralHostRegistration stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("hostname") final String hostname) {
		CentralHostRegistration centralhostregistration = this.centralhostregistrationService.get(hostname);
		if (java.util.Objects.isNull(centralhostregistration)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK).entity(
					centralhostregistrationEntityConverter.getDTOCentralHostRegistration(centralhostregistration))
					.build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored CentralHostRegistration!", notes = "Get list of all stored CentralHostRegistration!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<CentralHostRegistration> list = this.centralhostregistrationService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOCentralHostRegistrationList centralhostregistrationListDTO = new DTOCentralHostRegistrationList();
		for (CentralHostRegistration centralhostregistration : list) {
			try {
				centralhostregistrationListDTO.add(
						centralhostregistrationEntityConverter.getDTOCentralHostRegistration(centralhostregistration));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(centralhostregistrationListDTO).build();
	}

	@DELETE
	@Path("/{hostname}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("hostname") final String hostname) {
		int result = this.centralhostregistrationService.delete(hostname);
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
	@ApiOperation(value = "Create CentralHostRegistration!", notes = "Creating an instance of CentralHostRegistration!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid ConfigUnit configUnit) {

		System.err.println("===================================================================");
		System.err.println("Adding host : " + configUnit.getKey() + "\t" + configUnit.getValue() + "\t"
				+ configUnit.getHealthCheck());
		System.err.println("===================================================================");

		if (!Objects.isNull(this.centralhostregistrationService.get(configUnit.getKey()))) {
			this.centralhostregistrationService.delete(configUnit.getKey());
		}

		DTOCentralHostRegistration dtocentralhostregistration = new DTOCentralHostRegistration();
		dtocentralhostregistration.setName(configUnit.getKey());
		dtocentralhostregistration.setAddress(configUnit.getValue());
		dtocentralhostregistration.setPort(configUnit.getPort());
		dtocentralhostregistration.setHealthCheck(configUnit.getHealthCheck());
		dtocentralhostregistration.setType(configUnit.getType());
		if (configUnit.getRequired().equalsIgnoreCase("hostname")) {
			String url = "http://" + dtocentralhostregistration.getAddress() + ":"
					+ dtocentralhostregistration.getPort() + dtocentralhostregistration.getHealthCheck();
			dtocentralhostregistration.setStatus(getUpdate(url));
		} else {
			dtocentralhostregistration.setStatus("-1");
		}

		CentralHostRegistration centralhostregistration = null;
		try {
			centralhostregistration = centralhostregistrationEntityConverter
					.getCentralHostRegistration(dtocentralhostregistration);
			centralhostregistration.setHb(0);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		centralhostregistration.setRegistrationDate(new Date());
		int i = this.centralhostregistrationService.post(centralhostregistration);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}

	}

	@POST
	@Path("/keys")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create CentralHostRegistration!", notes = "Creating an instance of CentralHostRegistration!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response postKeys(@Valid ConfigUnit configUnit) {
		try {
			String storeFilePath = "store.txt";
			new ManageOthers().manageKeys(storeFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(new Message("Managed other option")).build();

	}

	@GET
	@Path("/keys")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns keypairs stored by the ganache !", notes = "Returns keypairs stored by the ganache  ", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getKeys() {
		try {
			return Response.status(Response.Status.OK).entity(new ManageOthers().getKeys()).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching default account list on ganache !")).build();
		}

	}

	@GET
	@Path("/avail/key")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns keypair which has ether in account and stored by the ganache !", notes = "Returns keypair which has ether in account and stored by the ganache  ", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAvailableKey() {
		try {
			ECAccount acct = new ManageOthers().getKey();
			if (Objects.isNull(acct)) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(new Message("Not ECAccouunt available !")).build();

			}
			return Response.status(Response.Status.OK).entity(new ManageOthers().getKey()).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching default account list on ganache !")).build();
		}

	}

	@POST
	@Path("/avail/key/{keyno}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Set that the keypair is consumed and ethers are used !", notes = "Set that the keypair is consumed and ethers are used ", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response setKeyConsumed(@PathParam("keyno") final String keyno) {
		try {
			return Response.status(Response.Status.OK).entity(new ManageOthers().keyConsumed(keyno)).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching default account list on ganache !")).build();
		}

	}

	@PUT
	@Path("/{hostname}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update CentralHostRegistration!", notes = "Updating an instance of CentralHostRegistration !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("hostname") final String hostname,
			@Valid DTOCentralHostRegistration dtocentralhostregistration) {
		dtocentralhostregistration.setName(hostname);
		CentralHostRegistration centralhostregistration = null;
		try {
			centralhostregistration = centralhostregistrationEntityConverter
					.getCentralHostRegistration(dtocentralhostregistration);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.centralhostregistrationService.put(centralhostregistration);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	public String getUpdate(String url) {

		try {
			HttpResponse response;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getConnection = new HttpGet(url);
			response = httpClient.execute(getConnection);
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 400) {
				return "1";

			}

		} catch (Exception e) {
		}
		return "0";
	}
}
