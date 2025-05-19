package com.ri.se.temporarymovement.main;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.temporarymovement.persistance.TemporaryMovementService;
import com.ri.se.temporarymovement.utils.TemporaryMovementResource;
import com.ri.se.temporarymovementgroup.persistance.TemporaryMovementGroupService;
import com.ri.se.temporarymovementgroup.utils.HealthCheckResource;
import com.ri.se.temporarymovementgroup.utils.TemplateHealthCheck;
import com.ri.se.temporarymovementgroup.utils.TemporaryMovementGroupResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class TemporaryMovementApplication extends Application<TemporaryMovementConfiguration> {

	public TemporaryMovementApplication() {

	}

	@Override
	public String getName() {
		return "TemporaryMovement";
	}

	public static void main(String[] args) throws Exception {

		new TemporaryMovementApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<TemporaryMovementConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());

		bootstrap.addBundle(new ViewBundle<TemporaryMovementConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(TemporaryMovementConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<TemporaryMovementConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(
					TemporaryMovementConfiguration configuration) {
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
	public void run(TemporaryMovementConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		TemporaryMovementService temporarymovementService = dbi.onDemand(TemporaryMovementService.class);
		temporarymovementService.create();
		TemporaryMovementGroupService temporarymovementgroupService = dbi.onDemand(TemporaryMovementGroupService.class);
		temporarymovementgroupService.create();
		enableCORS(environment);
		TemporaryMovementResource temporarymovementResource = new TemporaryMovementResource(temporarymovementService,
				temporarymovementgroupService);
		TemporaryMovementGroupResource temporarymovementgroupResource = new TemporaryMovementGroupResource(
				temporarymovementgroupService);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		HealthCheckResource healthCheckResource = new HealthCheckResource(temporarymovementService);
		environment.jersey().register(healthCheckResource);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(temporarymovementResource);
		environment.jersey().register(temporarymovementgroupResource);
	}
}
