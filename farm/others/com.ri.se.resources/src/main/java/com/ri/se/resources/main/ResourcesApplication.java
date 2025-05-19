package com.ri.se.resources.main;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.resources.persistance.FacilityService;
import com.ri.se.resources.persistance.FarmService;
import com.ri.se.resources.persistance.FarmerService;
import com.ri.se.resources.utils.FacilityResource;
import com.ri.se.resources.utils.FarmResource;
import com.ri.se.resources.utils.FarmerResource;
import com.ri.se.resources.utils.HealthCheckResource;
import com.ri.se.resources.utils.TemplateHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


public class ResourcesApplication extends Application<ResourcesConfiguration> {


	public ResourcesApplication(){
		
	}

	@Override
	public String getName() {
		return "Resources";
	}

	public static void main(String[] args) throws Exception {

		new ResourcesApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<ResourcesConfiguration> bootstrap){
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
			bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new ViewBundle<ResourcesConfiguration>(){
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(ResourcesConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<ResourcesConfiguration>(){
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(ResourcesConfiguration configuration){
			return configuration.getSwaggerBundleConfiguration();
			}
		});
	}


	private void enableCORS(Environment environment) {
		FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
		filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
		filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}

	private static final String SQL = "sql";

	@Override
	public void run(ResourcesConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		
		FacilityService facilityService = dbi.onDemand(FacilityService.class);
		facilityService.create();
		FacilityResource facilityResource = new FacilityResource(facilityService);
		
		FarmService farmService = dbi.onDemand(FarmService.class);
		farmService.create();
		FarmResource farmResource = new FarmResource(farmService);
		
		FarmerService farmerService = dbi.onDemand(FarmerService.class);
		farmerService.create();
		FarmerResource farmerResource = new FarmerResource(farmerService);
		
		enableCORS(environment);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(facilityResource);
		environment.jersey().register(farmerResource);
		environment.jersey().register(farmResource);	
		HealthCheckResource healthCheckResource = new HealthCheckResource(farmerService);
		environment.jersey().register(healthCheckResource);
	}
}
