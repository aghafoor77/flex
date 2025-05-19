package org.ri.se.trace.api.resource;

import java.io.File;
import java.net.URISyntaxException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acreo.security.bc.CertificateHandlingBC;
import org.acreo.security.certificate.CMSDelegator;
import org.acreo.security.certificate.CertificateSuite;
import org.acreo.security.utils.CertificateData;
import org.acreo.security.utils.DistinguishName;
import org.acreo.security.utils.PEMStream;
import org.acreo.security.utils.SGen;
import org.acreo.security.utils.StoreHandling;
import org.apache.log4j.Logger;
import org.ri.se.trace.api.eth.EtherAccountData;
import org.ri.se.trace.api.eth.EtherTransferManager;
import org.ri.se.trace.api.eth.ManageEther;
import org.ri.se.trace.api.main.TAPIConfig;
import org.ri.se.trace.api.utils.ECAccount;
import org.ri.se.trace.api.utils.IOUtils;
import org.ri.se.trace.api.utils.Message;
import org.ri.se.trace.api.utils.PublishResource;
import org.ri.se.trace.api.utils.RestClientSideOperations;
import org.ri.se.vt.blockchain.AccountsManager;
import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.CredentialException;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Numeric;

import com.ri.se.dap.VeidblockManager;
import com.ri.se.users.dto.AssignRole;
import com.ri.se.users.persistance.Users;
import com.ri.se.users.persistance.UsersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/tapi/idms/v1")
@Api(value = "/tapi", description = "Traceability API")
public class AccountResource {

	private static final Logger logger_ = Logger.getLogger(AccountResource.class);
	private TAPIConfig tapiConfig;
	private UsersService usersService;

	public AccountResource(TAPIConfig tapiConfig, UsersService usersService) {
		this.tapiConfig = tapiConfig;
		System.out.println(new File(this.tapiConfig.getWalletDir()).getAbsolutePath());
		this.usersService = usersService;
	}

	@Context
	protected HttpServletResponse response;
	@Context
	protected HttpServletRequest request;

	@Context
	protected HttpHeaders headers;

	@POST
	@Path("/account")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Provide a list of key values to create an account. 'email' and 'password' should be part of these values like {\"email\":\"your@email.com\",\"passowrd\",\"yourpass\" }!", notes = "Provide a list of key values to create an account. 'email' and 'password' should be part of these values like {\"email\":\"your@email.com\",\"passowrd\",\"yourpass\" }", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response createAccount(@Valid Hashtable<String, String> identity) {
		if (!(identity.containsKey("email") || identity.containsKey("password"))) {
			logger_.error("===> 'email' and 'password' key not found !");
			return Response.status(Response.Status.NOT_FOUND).entity("'email' and 'password' key not found !").build();
		}
		AccountsManager accountsManager = new AccountsManager();
		String pubKey64 = null;
		try {
			if (accountsManager.exists(tapiConfig.getWalletDir(), identity.get("email"))) {
				logger_.error("===> Account wallet already exist !");
				// return Response.status(Response.Status.CONFLICT).entity("Account already
				// exist !").build();
			} else {

				accountsManager.createAccount(identity.get("email"), identity.get("password"));
				CertificateSuite certificateSuite = new CertificateSuite(identity.get("email"),
						CertificateHandlingBC.getClientKeyUsage());
				CertificateHandlingBC certificateHandlingBC = new CertificateHandlingBC(certificateSuite,
						identity.get("password"));
				DistinguishName distinguishName = DistinguishName.builder().name(identity.get("email")).build();

				CertificateData certData = certificateHandlingBC.createSelfSignedClientCert(distinguishName,
						CertificateHandlingBC.getClientKeyUsage());
			}
			// StoreHandling storeHandling = new StoreHandling();

			Users users_ = usersService.get(identity.get("email"));
			if (!Objects.isNull(users_)) {
				logger_.error("===> Aaccount already exist !!");
				return Response.status(Response.Status.NO_CONTENT).entity(new Message("Aaccount already exist !"))
						.build();
			}
			users_ = new Users();
			SGen gen = new SGen();
			users_.setVeid(gen.nextHexString(30));
			users_.setEmail(identity.get("email"));
			users_.setPassword(identity.get("password"));
			users_.setRoles("Not Assigned");
			usersService.post(users_);
					
			
			logger_.info("===> 4!");
			/*
			 * RestClient rc =
			 * RestClient.builder().baseUrl(tapiConfig.getResourceAPI()).build();
			 * 
			 * Farmer farmer = new Farmer(); farmer.setEmail(identity.get("email"));
			 * farmer.setRole("Not Assigned"); farmer.setResourceId(identity.get("email"));
			 * rc.post("/application/v1/farmer", farmer, null);
			 */
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			logger_.error("===> Problems when creating new account !");
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new Message("Problems when creating new account !")).build();
		}
	}

	@GET
	@Path("/account/users/{veid}")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Assign a role !", notes = "Assign a role !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response getUsers(@PathParam("veid") final String veid) {
		try {
			Users users_ = usersService.getByVeid(veid);
			return Response.ok(users_).build();
		} catch (Exception e) {
			logger_.error("===> Problems when fetching user !");
			return Response.status(Response.Status.NOT_FOUND).entity(new Message("Problems when fetching user!"))
					.build();
		}
	}

	@PUT
	@Path("/account/assignrole")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Assign a role !", notes = "Assign a role !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response assignROle(@Valid AssignRole assignRole) {
		try {
			if (Objects.isNull(assignRole.getEmail()) || Objects.isNull(assignRole.getRoles())) {
				logger_.error("===> 'email' and 'role' does not exist !");
				return Response.status(Response.Status.NOT_FOUND).entity("'email' and 'role' does not exist !").build();
			}
			Users users_ = usersService.get(assignRole.getEmail());
			users_.setRoles(assignRole.getRoles());
			
			usersService.put(users_);
			
			
			CertificateSuite certificateSuite = new CertificateSuite(assignRole.getEmail(),
					CertificateHandlingBC.getClientKeyUsage());
			CertificateHandlingBC certificateHandlingBC = new CertificateHandlingBC(certificateSuite,
					users_.getPassword());
			DistinguishName distinguishName = DistinguishName.builder().name(users_.getEmail()).build();

			CMSDelegator cmsDelegator = new CMSDelegator(users_.getPassword(),certificateSuite) ;
			
			byte [] pubKey = cmsDelegator.fetchCACert(distinguishName).getPublicKey().getEncoded();
			String pubKey64 = new String(java.util.Base64.getEncoder().encode(pubKey));
			System.out.println("=========================== >" + pubKey64.length());
			PublishResource publish = new PublishResource();
			publish.publish(users_.getVeid(), users_.getRoles(), pubKey64, assignRole.getOrganization());	
			
			
			
			return Response.ok().build();
		} catch (Exception e) {
			logger_.error("===> Problems when assigning role !");
			return Response.status(Response.Status.NOT_FOUND).entity(new Message("Problems when assigning role !"))
					.build();
		}
	}

	@GET
	@Path("/account/listusers")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List users !", notes = "List users !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response listusers() {
		try {
			List<Users> users_ = usersService.list();
			return Response.ok(users_).build();
		} catch (Exception e) {
			logger_.error("===> Problems when listing users !");
			return Response.status(Response.Status.NOT_FOUND).entity(new Message("Problems when listing users!"))
					.build();
		}
	}

	@GET
	@Path("/account/listusers/{role}")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List users !", notes = "List users !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response listUserByRole(@PathParam("role") final String role) {
		try {
			List<Users> users_ = usersService.listUserByRole(role);
			return Response.ok(users_).build();
		} catch (Exception e) {
			logger_.error("===> Problems when assigning role !");
			return Response.status(Response.Status.NOT_FOUND).entity(new Message("Problems when assigning role !"))
					.build();
		}
	}

	@GET
	@Path("/account/listroles")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List users !", notes = "List users !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response listRole() {
		try {
			List<String> roles = new ArrayList<String>();
			roles.add("Not Assigned");
			roles.add("EMPLOYEE");
			roles.add("OWNER");
			roles.add("ADMIN");
			roles.add("OBSERVER");
			roles.add("VETERINARIAN");
			roles.add("TRANSPORTER");
			roles.add("SALUGHTERHOUSEOWNER");
			return Response.ok(roles).build();
		} catch (Exception e) {
			logger_.error("===> Problems when fething roles !");
			return Response.status(Response.Status.NOT_FOUND).entity(new Message("Problems when fething roles!"))
					.build();
		}
	}

	@GET
	@Path("/account")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns a current state of Etherum account. !", notes = "Returns current status of Enterum account !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response getAccount() throws Exception {
		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();

		try {
			if (!accountsManager.exists(email)) {
				logger_.error("===> Account does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity(new Message("Account does not exist !"))
						.build();
			}
		} catch (CredentialException e) {

			logger_.error("===> Problems when fetching account info !");
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new Message("Problems when fetching account info!")).build();

		}
		Credentials cred = manageCredentials();

		EtherAccountData accountData = new EtherAccountData();
		accountData.setAddress(cred.getAddress());
		accountData.setUsername(email);

		try {
			double bal = new EtherTransferManager()
					.getEthBalance(new Web3JConnector(tapiConfig.getUrlEther()).getWeb3j(), cred.getAddress());
			accountData.setEther(bal);
		} catch (EtherGenericException e) {
			accountData.setEther(-1);
		}

		try {
			DistinguishName distinguishName = DistinguishName.builder().name(email).build();

			CertificateSuite certificateSuite = new CertificateSuite(email, CertificateHandlingBC.getClientKeyUsage());
			StoreHandling storeHandling = new StoreHandling();
			PublicKey publicKey = storeHandling.fetchCertificate(certificateSuite, distinguishName).getPublicKey();
			String temp = new PEMStream().toPem(publicKey);
			accountData.setRsaPublicKeyPem(new PEMStream().toPem(publicKey));
			accountData.setRsaPublicKeyHex(PEMStream.bytesToHex(publicKey.getEncoded()));

			return Response.ok().entity(accountData).build();
		} catch (Exception e) {
			logger_.error("===> Problems when fetching RSA account info !");
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new Message("Problems when fetching RSA account info!")).build();
		}

	}

	@POST
	@Path("/auth")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Provide valid 'email' and 'password' in the standard HTTP Authorization header with BSIC tag format!", notes = "Create a new account based on provided email and password!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response auth() {
		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();
		try {
			if (!accountsManager.exists(email)) {
				logger_.error("===> Aaccount does not exist !");
				return Response.status(Response.Status.NO_CONTENT)
						.entity(new Message("Aaccount does not exist in database!")).build();
			}
			Credentials cred = manageCredentials();
			Users users_ = usersService.get(email);
			if (Objects.isNull(users_)) {
				logger_.error("===> Aaccount does not exist in database!");
				return Response.status(Response.Status.NO_CONTENT)
						.entity(new Message("Aaccount does not exist in database!")).build();
			}
			return Response.ok(users_).build();
		} catch (Exception e) {
			logger_.error("===> Problems when fetching account info from Blockchain !");
			return Response.status(Response.Status.UNAUTHORIZED).build();// .entity("Problems when fetching account info
		}
	}

	@GET
	@Path("/deposit")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deposit 9000000000 Dummy Ether to the account for making transactions !", notes = "Deposit 9000000000 Dummy Ether to the account for making transactions !", response = EtherAccountData.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response deposit() throws Exception {
		String email = getUser();
		String password = getPassword();
		AccountsManager accountsManager = new AccountsManager();
		try {
			if (!accountsManager.exists(email)) {
				logger_.error("===> Account does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity(new Message("Account does not exist !"))
						.build();
			}
		} catch (CredentialException e) {

			logger_.error("===> Problems when fetching account info !");
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new Message("Problems when fetching account info!")).build();

		}

		Credentials cred = manageCredentials();
		try {
			manageDeposit(0, email, password);
		} catch (Exception e) {
			logger_.error("===> Problems when managing account for depositing ether !");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when managing account fro depositing ether !")).build();
		}

		EtherAccountData etherAccountData = new EtherAccountData();
		etherAccountData.setAddress(cred.getAddress());
		etherAccountData.setUsername(email);

		try {
			double bal = new EtherTransferManager()
					.getEthBalance(new Web3JConnector(tapiConfig.getUrlEther()).getWeb3j(), cred.getAddress());
			etherAccountData.setEther(bal);
		} catch (EtherGenericException e) {
			etherAccountData.setEther(-1);
		}
		try {
			DistinguishName distinguishName = DistinguishName.builder().name(email).build();

			CertificateSuite certificateSuite = new CertificateSuite(email, CertificateHandlingBC.getClientKeyUsage());
			StoreHandling storeHandling = new StoreHandling();
			PublicKey publicKey = storeHandling.fetchCertificate(certificateSuite, distinguishName).getPublicKey();
			String temp = new PEMStream().toPem(publicKey);
			etherAccountData.setRsaPublicKeyPem(new PEMStream().toPem(publicKey));
			etherAccountData.setRsaPublicKeyHex(PEMStream.bytesToHex(publicKey.getEncoded()));

			return Response.ok().entity(etherAccountData).build();
		} catch (Exception e) {
			logger_.error("===> Problems when fetching RSA account info !");
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new Message("Problems when fetching RSA account info!")).build();
		}
	}

	private String getUser() {
		String owner = headers.getHeaderString("owner");
		if (Objects.isNull(owner)) {
			logger_.error("===> Unauthorized user cannot execute this function !");
			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/login");
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
			logger_.error("===> Unauthorized user cannot execute this function !");

			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/login");
			} catch (URISyntaxException e) {

				e.printStackTrace();
			}
			throw new WebApplicationException(Response.temporaryRedirect(location).build());
		}
		return pass;
	}

	private Credentials manageCredentials() {
		try {
			return new AccountsManager().getCredentials(tapiConfig.getWalletDir(), getUser(), getPassword());
		} catch (CredentialException e) {
			logger_.error("===> Invalid credentials !");
			throw new WebApplicationException("Invalid credentials !", Status.UNAUTHORIZED);
		}
	}

	private boolean manageDeposit(int count, String email, String password) throws Exception {
		String crUrl = tapiConfig.getUrlCentraliRegistration() + "/application/v1/centralhostregistration/avail/key";
		RestClientSideOperations restClientSideOperations = new RestClientSideOperations();
		ECAccount ecAccount = restClientSideOperations.getAcct(crUrl);
		boolean bool = new ManageEther().manageAccounts(ecAccount.getPrivateKey(), "9000000000",
				tapiConfig.getWalletDir(), email, password, tapiConfig.getUrlEther());
		if (bool) {
			restClientSideOperations.consumed(crUrl, ecAccount.getKeyNo());
			return true;
		}
		count++;
		if (count <= 3) {
			manageDeposit(count, email, password);
		}
		return false;
	}
	// manage public keys

	@GET
	@Path("/rsa")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add a public key into veid block against etherum address !", notes = "Add a public key into veid block against etherum address !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response addRSAKey() {
		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();

		try {
			if (!accountsManager.exists(email)) {
				logger_.error("===> Aaccount does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity(new Message("Aaccount does not exist !"))
						.build();
			}
			Credentials cred = manageCredentials();
			IOUtils ioUtils = new IOUtils();
			String dapSCAddress = ioUtils.readSmartContract("DAP");

			DistinguishName distinguishName = DistinguishName.builder().name(email).build();

			CertificateSuite certificateSuite = new CertificateSuite(email, CertificateHandlingBC.getClientKeyUsage());
			StoreHandling storeHandling = new StoreHandling();
			PublicKey publicKey = storeHandling.fetchCertificate(certificateSuite, distinguishName).getPublicKey();
			String hexPk = new VeidblockManager(tapiConfig.getUrlEther()).getRSAPublicKey(dapSCAddress,
					Numeric.toHexStringWithPrefix(cred.getEcKeyPair().getPrivateKey()), cred.getAddress());
			if (!Objects.isNull(hexPk)) {
				if (PEMStream.bytesToHex(publicKey.getEncoded()).equals(hexPk)) {
					return Response.ok().build();
				}
			}

			System.out.println("Registering RSA key: " + PEMStream.toHex(publicKey));

			new VeidblockManager(tapiConfig.getUrlEther()).addRSAPublicKey(dapSCAddress,
					Numeric.toHexStringWithPrefix(cred.getEcKeyPair().getPrivateKey()),
					PEMStream.bytesToHex(publicKey.getEncoded()));

			System.out.println("Fetched Registered RSA Key: "
					+ new VeidblockManager(tapiConfig.getUrlEther()).getRSAPublicKey(dapSCAddress,
							Numeric.toHexStringWithPrefix(cred.getEcKeyPair().getPrivateKey()), cred.getAddress()));

			return Response.ok().build();
		} catch (Exception e) {
			logger_.error("===> Problems when fetching account info from Blockchain !");
			e.printStackTrace();
			return Response.status(Response.Status.UNAUTHORIZED).build();// .entity("Problems when fetching account info
		}
	}

	@GET
	@Path("/rsa/status")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add a public key into veid block against etherum address !", notes = "Add a public key into veid block against etherum address !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response isRSAKeyRegistered() {

		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();

		try {

			if (!accountsManager.exists(email)) {
				logger_.error("===> Aaccount does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity(new Message("Aaccount does not exist !"))
						.build();
			}

			Credentials cred = manageCredentials();

			IOUtils ioUtils = new IOUtils();
			String dapSCAddress = ioUtils.readSmartContract("DAP");

			String hexPk = new VeidblockManager(tapiConfig.getUrlEther()).getRSAPublicKey(dapSCAddress,
					Numeric.toHexStringWithPrefix(cred.getEcKeyPair().getPrivateKey()), cred.getAddress());
			if (Objects.isNull(hexPk) || hexPk.length() == 0) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			DistinguishName distinguishName = DistinguishName.builder().name(email).build();

			CertificateSuite certificateSuite = new CertificateSuite(email, CertificateHandlingBC.getClientKeyUsage());
			StoreHandling storeHandling = new StoreHandling();
			PublicKey publicKey = storeHandling.fetchCertificate(certificateSuite, distinguishName).getPublicKey();

			System.out.println("Fetched from blockchain : " + hexPk);

			if (!PEMStream.toHex(publicKey).equals(hexPk)) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			return Response.ok(hexPk).build();

		} catch (Exception e) {
			logger_.error("===> Problems when fetching account info from Blockchain !");
			logger_.error("===> " + e.getMessage());
			return Response.status(Response.Status.UNAUTHORIZED).build();// .entity("Problems when fetching account info
		}
	}

	public boolean createAdminAccount() {
		Users users_ = new Users();
		SGen gen = new SGen();
		users_.setVeid(gen.nextHexString(30));
		users_.setEmail("admin@lsc.se");
		users_.setPassword("1234");
		users_.setRoles("ADMIN");
		AccountsManager accountsManager = new AccountsManager();
		try {
			if (accountsManager.exists(tapiConfig.getWalletDir(), users_.getEmail())) {
				logger_.error("===> Admin credentails already registered !");

			} else {
				accountsManager.createAccount(users_.getEmail(), users_.getPassword());
				CertificateSuite certificateSuite = new CertificateSuite(users_.getEmail(),
						CertificateHandlingBC.getClientKeyUsage());
				CertificateHandlingBC certificateHandlingBC = new CertificateHandlingBC(certificateSuite,
						users_.getPassword());
				DistinguishName distinguishName = DistinguishName.builder().name(users_.getEmail()).build();
				certificateHandlingBC.createSelfSignedClientCert(distinguishName,
						CertificateHandlingBC.getClientKeyUsage());
			}

			Users users = usersService.get(users_.getEmail());
			if (Objects.isNull(users)) {
				usersService.post(users_);
			} else {
				logger_.error("===> Admin already registered as User !");
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger_.error("===> Problems when creating admin account so please fix it !");
			System.exit(1);
			return false;
		}
	}
}
