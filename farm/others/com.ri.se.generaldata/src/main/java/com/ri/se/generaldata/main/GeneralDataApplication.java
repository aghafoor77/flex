package com.ri.se.generaldata.main;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.breed.persistance.BreedService;
import com.ri.se.breed.utils.BreedResource;
import com.ri.se.breed.utils.TemplateHealthCheck;
import com.ri.se.feedtype.persistance.FeedTypeService;
import com.ri.se.feedtype.utils.FeedTypeResource;
import com.ri.se.feedtype.utils.HealthCheckResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


public class GeneralDataApplication extends Application<GeneralDataConfiguration> {


	public GeneralDataApplication(){
		
	}

	@Override
	public String getName() {
		return "GeneralData";
	}

	public static void main(String[] args) throws Exception {

		new GeneralDataApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<GeneralDataConfiguration> bootstrap){
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
			bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		
		bootstrap.addBundle(new ViewBundle<GeneralDataConfiguration>(){
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(GeneralDataConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<GeneralDataConfiguration>(){
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(GeneralDataConfiguration configuration){
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
	public void run(GeneralDataConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		enableCORS(environment);
		BreedService breedService = dbi.onDemand(BreedService.class);
		breedService.create();
		FeedTypeService feedtypeService = dbi.onDemand(FeedTypeService.class);
		feedtypeService.create();
		
		BreedResource breedResource = new BreedResource(breedService);
		FeedTypeResource feedtypeResource = new FeedTypeResource(feedtypeService);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(breedResource);
		environment.jersey().register(feedtypeResource);
		HealthCheckResource healthCheckResource = new HealthCheckResource(feedtypeService);
		environment.jersey().register(healthCheckResource);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
	}
}
