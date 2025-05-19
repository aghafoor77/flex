package org.ri.se.trace.api.main;

import java.io.File;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.ri.se.trace.api.health.TraceHealthCheck;
import org.ri.se.trace.api.resource.AccountResource;
import org.ri.se.trace.api.resource.AttachedEvidenceResource;
import org.ri.se.trace.api.resource.HealthCheckResource;
import org.ri.se.trace.api.resource.SmartContractResource;
import org.ri.se.trace.api.resource.TraceResource;
import org.ri.se.vt.blockchain.Web3JConnector;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ri.se.users.persistance.UsersService;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ri.se.trace.persistant.AttachedResourceService;

public class TraceApplication extends Application<TraceConfiguration> {

	private static final Logger logger_ = LoggerFactory.getLogger(TraceApplication.class);

	public TraceApplication() {
	}

	public static void main(String[] args) throws Exception {
		new TraceApplication().run(args);
	}

	@Override
	public String getName() {
		return "tapi";
	}

	@Override
	public void initialize(Bootstrap<TraceConfiguration> bootstrap) {
		// Enable variable substitution with environment variables
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

		bootstrap.addBundle(new AssetsBundle());
	
		// bootstrap.addBundle(hibernateBundle);
		bootstrap.addBundle(new ViewBundle<TraceConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(TraceConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});

		bootstrap.addBundle(new SwaggerBundle<TraceConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(TraceConfiguration configuration) {
				return configuration.getSwaggerBundleConfiguration();
			}
		});
	}

	private static final String SQL = "sql";

	@Override
	public void run(TraceConfiguration configuration, Environment environment) {

		enableCORS(environment);
		logger_.debug("===> Configuration file processed successfully !");

		configuration.getTapi().setWalletDir(getWalletDir());

		File f = new File(configuration.getTapi().getWalletDir());
		if(!f.exists()) {
			f.mkdirs();
		}
		logger_.info("===> Checking blockchain connectivity !");
		//configuration.getTapi().setUrlEther("http://"+getBlockchainIP(configuration.getTapi())+":"+8545);
		logger_.info("<============Wallet path=================");
		logger_.info("===> "+f.getAbsolutePath());
		logger_.info("========================================>");
		logger_.info("<============Blockchain path=================");
		logger_.info("===> "+configuration.getTapi().getUrlEther());
		logger_.info("========================================>");
		
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		AttachedResourceService attachedResourceService = dbi.onDemand(AttachedResourceService.class);
		logger_.info("===> Creating DB Entity !");
		attachedResourceService.create();
		UsersService usersService = dbi.onDemand(UsersService.class);
		usersService.create();

		TraceResource traceResource = new TraceResource(configuration.getTapi(), attachedResourceService);
		
		AttachedEvidenceResource attachedEvidenceResource = new AttachedEvidenceResource(configuration.getTapi(),
				attachedResourceService);
		AccountResource accountResource = new AccountResource(configuration.getTapi(), usersService);
		accountResource.createAdminAccount();
		SmartContractResource smartContractResource= new SmartContractResource(configuration.getTapi());
		TraceVerificationFilter verificationFilter = new TraceVerificationFilter();
		environment.healthChecks().register("TAPIHealthCheck", new TraceHealthCheck(
				new Web3JConnector(configuration.getTapi().getUrlEther()), configuration.getTapi().getUrlIPFS()));
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(traceResource);
		environment.jersey().register(attachedEvidenceResource);
		environment.jersey().register(new HealthCheckResource());
		environment.jersey().register(accountResource);
		environment.jersey().register(smartContractResource);
		environment.jersey().register(new AuthDynamicFeature(verificationFilter));
	}

	private void enableCORS(Environment environment) {
		FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
		filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
		filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}
	
	public String getWalletDir() {
		String homeDir = System.getProperty("user.home");
		String dir = homeDir + File.separator + "veidblock_RT" + File.separator + "credentials"
				+ File.separator;
		return dir;
	}		
	/*public String getBlockchainIP(TAPIConfig tapiConfig) {
		RestClientSideOperations restClientSideOperations = new RestClientSideOperations();
		String url = tapiConfig.getUrlCentraliRegistration()+"/application/v1/centralhostregistration";
		String hostname = "ganachectrl";
		DTOCentralHostRegistration centralHostRegistration =  restClientSideOperations.getIP(url, hostname );
		return centralHostRegistration .getAddress();		
	}*/	
}
