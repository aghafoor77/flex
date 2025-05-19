package com.ri.se.feedpattern.main;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.assignfeed.persistance.AssignFeedService;
import com.ri.se.assignfeed.utils.AssignFeedResource;
import com.ri.se.assignfeed.utils.TemplateHealthCheck;
import com.ri.se.feedpattern.persistance.FeedPatternService;
import com.ri.se.feedpattern.utils.FeedPatternResource;
import com.ri.se.feedpattern.utils.HealthCheckResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


public class FeedPatternApplication extends Application<FeedPatternConfiguration> {


	public FeedPatternApplication(){
		
	}

	@Override
	public String getName() {
		return "FeedPattern";
	}

	public static void main(String[] args) throws Exception {

		new FeedPatternApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<FeedPatternConfiguration> bootstrap){
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
			bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		
		bootstrap.addBundle(new ViewBundle<FeedPatternConfiguration>(){
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(FeedPatternConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<FeedPatternConfiguration>(){
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(FeedPatternConfiguration configuration){
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
	public void run(FeedPatternConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		FeedPatternService feedpatternService = dbi.onDemand(FeedPatternService.class);
		feedpatternService.create();
		AssignFeedService assignfeedService = dbi.onDemand(AssignFeedService.class);
		assignfeedService.create();
		FeedPatternResource feedpatternResource = new FeedPatternResource(feedpatternService);
		enableCORS(environment);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		AssignFeedResource assignfeedResource = new AssignFeedResource(assignfeedService);
		environment.jersey().register(assignfeedResource);
		environment.jersey().register(feedpatternResource);
		environment.healthChecks().register("template", healthCheck);
		HealthCheckResource healthCheckResource = new HealthCheckResource(feedpatternService);
		environment.jersey().register(healthCheckResource);
		environment.jersey().register(MultiPartFeature.class);			
	}
}
