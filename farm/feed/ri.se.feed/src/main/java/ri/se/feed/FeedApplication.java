package ri.se.feed;

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
import com.ri.se.feedtype.persistance.FeedTypeService;
import com.ri.se.feedtype.utils.FeedTypeResource;
import com.ri.se.temporarymovement.persistance.TemporaryMovementService;
import com.ri.se.temporarymovement.utils.TemporaryMovementResource;
import com.ri.se.temporarymovementgroup.persistance.TemporaryMovementGroupService;
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

public class FeedApplication extends Application<FeedConfiguration> {

	public FeedApplication() {

	}

	@Override
	public String getName() {
		return "FeedPattern";
	}

	public static void main(String[] args) throws Exception {

		new FeedApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<FeedConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());

		bootstrap.addBundle(new ViewBundle<FeedConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(FeedConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<FeedConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(FeedConfiguration configuration) {
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
	public void run(FeedConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		// ======================================================================================
		// Creating data relations
		FeedPatternService feedpatternService = dbi.onDemand(FeedPatternService.class);
		feedpatternService.create();
		AssignFeedService assignfeedService = dbi.onDemand(AssignFeedService.class);
		assignfeedService.create();
		FeedTypeService feedTypeService = dbi.onDemand(FeedTypeService.class);
		feedTypeService.create();
		TemporaryMovementService temporarymovementService = dbi.onDemand(TemporaryMovementService.class);
		temporarymovementService.create();
		TemporaryMovementGroupService temporarymovementgroupService = dbi.onDemand(TemporaryMovementGroupService.class);
		temporarymovementgroupService.create();
		
		// ======================================================================================
		// Resources
		// FeedType
		FeedTypeResource feedTypeResource= new FeedTypeResource(feedTypeService);
				
		// FeedPattern
		FeedPatternResource feedpatternResource = new FeedPatternResource(feedpatternService);

		// AssignFeed
		AssignFeedResource assignfeedResource = new AssignFeedResource(assignfeedService);

		// Temporary movement and grouping 
		TemporaryMovementResource temporarymovementResource = new TemporaryMovementResource(temporarymovementService,temporarymovementgroupService );
		TemporaryMovementGroupResource temporarymovementgroupResource = new TemporaryMovementGroupResource(temporarymovementgroupService);
		
		// Health Service
		HealthCheckResource healthCheckResource = new HealthCheckResource(feedpatternService);

		// ======================================================================================

		enableCORS(environment);

		// Health checking
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		
		environment.jersey().register(feedTypeResource);
		environment.jersey().register(assignfeedResource);
		environment.jersey().register(feedpatternResource);
		environment.jersey().register(healthCheckResource);
		environment.jersey().register(temporarymovementResource);
		environment.jersey().register(temporarymovementgroupResource);		
	}
}
