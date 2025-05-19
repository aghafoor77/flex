package centralhostregistration.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.databind.ObjectMapper;

import centralhostregistration.healthmonitor.HealthMonitor;
import centralhostregistration.persistance.CentralHostRegistration;
import centralhostregistration.persistance.CentralHostRegistrationService;
import centralhostregistration.utils.CentralHostRegistrationResource;
import centralhostregistration.utils.KeyStoreProcessor;
import centralhostregistration.utils.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class CentralHostRegistrationApplication extends Application<CentralHostRegistrationConfiguration> {

	public CentralHostRegistrationApplication() {

	}

	@Override
	public String getName() {
		return "CentralHostRegistration";
	}

	public static void main(String[] args) throws Exception {
		new CentralHostRegistrationApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<CentralHostRegistrationConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());

		bootstrap.addBundle(new ViewBundle<CentralHostRegistrationConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(
					CentralHostRegistrationConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<CentralHostRegistrationConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(
					CentralHostRegistrationConfiguration configuration) {
				return configuration.getSwaggerBundleConfiguration();
			}
		});
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

	private static final String SQL = "sql";

	@Override
	public void run(CentralHostRegistrationConfiguration configuration, Environment environment) throws Exception {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		CentralHostRegistrationService centralhostregistrationService = dbi
				.onDemand(CentralHostRegistrationService.class);
		
		//String keyStorePath = "/home/app/data/ganachelog.txt";
		centralhostregistrationService.create();
		
		registerHosts(centralhostregistrationService, readRegisteredHosts());
		//Thread monitor = new Thread(new HealthMonitor(centralhostregistrationService));
		//monitor.start();
		//Thread thread = new Thread(new KeyStoreProcessor(keyStorePath));
		//thread.start();
		
		CentralHostRegistrationResource centralhostregistrationResource = new CentralHostRegistrationResource(
				centralhostregistrationService);
		enableCORS(environment);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(centralhostregistrationResource);
	}

	private static List<HashMap<String, String>> readRegisteredHosts() throws IOException {
		
		
		String hostData= "/home/app/data/hosts.txt";
		if(!new File(hostData).exists()) {
			hostData = "/home/devvm/Desktop/RISE/formas/data/hosts.txt";			
		}
		
		byte[] hosts = Files.readAllBytes(Paths.get(hostData));
		List<HashMap<String, String>> list = new ObjectMapper().readValue(new String(hosts), ArrayList.class);
		return list;
	}

	public static void registerHosts(CentralHostRegistrationService centralhostregistrationService, List<HashMap<String, String>> cuList) {
		centralhostregistrationService.deleteAll();
		for (HashMap<String, String> configUnit : cuList) {
			System.err.println("===================================================================");
			System.err.println("Adding host : " + configUnit.get("key") + "\t" + configUnit.get("value") + "\t"
					+ configUnit.get("healthCheck"));
			System.err.println("===================================================================");

			if (!Objects.isNull(centralhostregistrationService.get(configUnit.get("key")))) {
				centralhostregistrationService.delete(configUnit.get("key"));
			}

			CentralHostRegistration centralhostregistration = new CentralHostRegistration();
			centralhostregistration.setName(configUnit.get("key"));
			centralhostregistration.setAddress(configUnit.get("value"));
			centralhostregistration.setPort(Integer.parseInt((Objects.isNull(String.valueOf(configUnit.get("port")))?"0":String.valueOf(configUnit.get("port")))));
			centralhostregistration.setHealthCheck(configUnit.get("healthCheck"));
			centralhostregistration.setType(configUnit.get("type"));
			if (configUnit.get("required").equalsIgnoreCase("hostname")) {				
				centralhostregistration.setStatus("1");
			} else {
				centralhostregistration.setStatus("-1");
			}
			centralhostregistration.setHb(0);
			centralhostregistration.setRegistrationDate(new Date());
			int i = centralhostregistrationService.post(centralhostregistration);
		}
	}
}
