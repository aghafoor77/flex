package org.ri.se.trace.api.resource;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.ri.se.trace.api.main.TAPIConfig;
import org.ri.se.trace.api.register.RegisterSmartContract;
import org.ri.se.trace.api.utils.IOUtils;
import org.ri.se.trace.api.utils.Message;
import org.ri.se.vt.blockchain.AccountsManager;
import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.CredentialException;
import org.ri.se.vt.contract.ContractDeployer;
import org.ri.se.vt.v1.ITrace;
import org.ri.se.vt.v1.Trace;
import org.web3j.crypto.Credentials;

import com.ri.se.dap.VeidblockManager;
import com.ri.se.dap.VeidblockSmartContract;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/tapi/sc/v1")
@Api(value = "/tapi", description = "Traceability API : Smart contract deployer")
public class SmartContractResource {

	private static final Logger logger_ = Logger.getLogger(SmartContractResource.class);
	private TAPIConfig tapiConfig;

	public SmartContractResource(TAPIConfig tapiConfig) {
		this.tapiConfig = tapiConfig;
	}

	@Context
	protected HttpServletResponse response;
	@Context
	protected HttpHeaders headers;

	@GET
	@Path("/DAP")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List of all deployed smart contract !", notes = "Return a list of all deployed smart contract!", response = String.class, responseContainer = "Record")
	public Response get() {
		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();
		try {
			if (!accountsManager.exists(email)) {
				logger_.error("Account does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity("Account does not exist !").build();
			}
			IOUtils ioUtils = new IOUtils();
			
				String dapSCAddress = ioUtils.readSmartContract("DAP");
				if (!Objects.isNull(dapSCAddress)) {
					VeidblockManager veidblockManager = new VeidblockManager(tapiConfig.getUrlEther());
					Credentials cred = new AccountsManager().getCredentials(tapiConfig.getWalletDir(), email,
							getPassword());
					if (veidblockManager.isSmartContractExists(dapSCAddress, cred)) {
						return Response.status(Response.Status.OK)
								.entity(new Message( dapSCAddress))
								.build();
					}
				}
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Message("DAP Smart contract does not exisit !")).build();
			
		} catch (Exception e) {
			throw new WebApplicationException(
					"{message:'Problem when extracting records. \n[" + e.getMessage() + "] !'}",
					Status.INTERNAL_SERVER_ERROR);
		}
	}
	@GET
	@Path("/TRACE")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List of all deployed smart contract !", notes = "Return a list of all deployed smart contract!", response = String.class, responseContainer = "Record")
	public Response getTrace() {
		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();
		try {
			if (!accountsManager.exists(email)) {
				logger_.error("Account does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity("Account does not exist !").build();
			}
			IOUtils ioUtils = new IOUtils();
			
				String traceSCAddress = ioUtils.readSmartContract("TRACE");
				if (!Objects.isNull(traceSCAddress)) {
					Credentials cred = new AccountsManager().getCredentials(tapiConfig.getWalletDir(), email,
							getPassword());
					ITrace trace = new Trace(new Web3JConnector(tapiConfig.getUrlEther()), traceSCAddress, cred);
					if (trace.isSmartContractExists()) {
						return Response.status(Response.Status.OK).entity(new Message(
								traceSCAddress))
								.build();
					}
				}
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Message("TRACE Smart contract does not exisit !")).build();
			
		} catch (Exception e) {
			throw new WebApplicationException(
					"{message:'Problem when extracting records. \n[" + e.getMessage() + "] !'}",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	
	@POST
	@Path("/deploy/{name}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deploy smart contract !", notes = "Deploy smart contract!", response = String.class, responseContainer = "String")
	public Response deploy(@PathParam("name") final String name) {
		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();
		try {
			if (!accountsManager.exists(email)) {
				logger_.error("Account does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity("Account does not exist !").build();
			}
			
			IOUtils ioUtils = new IOUtils();
			
			if (name.equalsIgnoreCase("TRACE")) {
				
				String traceSCAddress = ioUtils.readSmartContract(name);
				
				if (!Objects.isNull(traceSCAddress)) {
					Credentials cred = new AccountsManager().getCredentials(tapiConfig.getWalletDir(), email,
							getPassword());
					ITrace trace = new Trace(new Web3JConnector(tapiConfig.getUrlEther()), traceSCAddress, cred);
					if (trace.isSmartContractExists()) {
						RegisterSmartContract registerSmartContract = new RegisterSmartContract();
						registerSmartContract.registerSmartContractAddress(tapiConfig, name, traceSCAddress); 
						return Response.status(Response.Status.OK).entity(new Message(
								"Already exisits smart contract having address Address:" + traceSCAddress + " !"))
								.build();
					}
				}
				
				ContractDeployer contractDeployer = new ContractDeployer(tapiConfig.getUrlEther());
				
				String smartContractAddress = contractDeployer.deployTraceabilityContract(tapiConfig.getWalletDir(),
						email, getPassword());
				
				ioUtils.writeSmartContract(name, smartContractAddress);
				RegisterSmartContract registerSmartContract = new RegisterSmartContract();
				registerSmartContract.registerSmartContractAddress(tapiConfig, name, smartContractAddress); 
				return Response.status(Response.Status.OK)
						.entity(new Message("Smart Contract Address:" + smartContractAddress + " !")).build();
			
			}
			
			if (name.equalsIgnoreCase("DAP")) {
				
				String dapSCAddress = ioUtils.readSmartContract(name);
				
				if (!Objects.isNull(dapSCAddress)) {
					VeidblockManager veidblockManager = new VeidblockManager(tapiConfig.getUrlEther());
					Credentials cred = new AccountsManager().getCredentials(tapiConfig.getWalletDir(), email,
							getPassword());
					if (veidblockManager.isSmartContractExists(dapSCAddress, cred)) {
						RegisterSmartContract registerSmartContract = new RegisterSmartContract();
						registerSmartContract.registerSmartContractAddress(tapiConfig, name, dapSCAddress); 
						return Response.status(Response.Status.OK)
								.entity(new Message(
										"Already exisits smart contract having address Address:" + dapSCAddress + " !"))
								.build();
					}
				}
				
				VeidblockSmartContract veidblockSmartContract = new VeidblockSmartContract(tapiConfig.getUrlEther());
				
				String smartContractAddress = veidblockSmartContract.deployContract(tapiConfig.getWalletDir(), email,
						getPassword());
				
				ioUtils.writeSmartContract(name, smartContractAddress);
				
				RegisterSmartContract registerSmartContract = new RegisterSmartContract();
				registerSmartContract.registerSmartContractAddress(tapiConfig, name, smartContractAddress); 
				
				return Response.status(Response.Status.OK)
						.entity(new Message("Smart Contract Address:" + smartContractAddress + " !")).build();
			}
			return Response.status(Response.Status.NOT_FOUND).entity(new Message("Unknown smart contract name !"))
					.build();

		} catch (Exception e) {
			logger_.error("Problem when extracting records.");
			if (e.getMessage().contains("sender doesn't have enough funds to send tx")) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(new Message("Not enough funds in account !")).build();
			}
			e.printStackTrace();
			throw new WebApplicationException(
					"{message:'Problem when extracting records. \n[" + e.getMessage() + "] !'}",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	private String getUser() {
		String owner = headers.getHeaderString("owner");
		if (Objects.isNull(owner)) {
			logger_.error("Unauthorized user cannot execute this function !");
			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscdash/forbidden.html");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			throw new WebApplicationException(Response.temporaryRedirect(location).build());
		}
		return owner;
	}

	private String getPassword() {
		String pass = headers.getHeaderString("pass");
		if (Objects.isNull(pass)) {
			logger_.error("Unauthorized user cannot execute this function !");

			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscdash/forbidden.html");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			throw new WebApplicationException(Response.temporaryRedirect(location).build());
		}
		return pass;
	}

	private Credentials manageCredentials() {
		try {
			return new AccountsManager().getCredentials(getWalletDir(), getUser(), getPassword());
		} catch (CredentialException e) {
			logger_.error("Invalid credentials !");
			throw new WebApplicationException("Invalid credentials !", Status.UNAUTHORIZED);
		}
	}

	public String getWalletDir() {
		String homeDir = System.getProperty("user.home");
		String dir = homeDir + File.separator + "veidblock_RT" + File.separator + "credentials" + File.separator;
		return dir;
	}
	
	
}
