package analytics.main;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.animalreg.persistance.RegisterAnimalService;
import com.ri.se.assignfeed.persistance.AssignFeedService;
import com.ri.se.feedpattern.persistance.FeedPatternService;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservationService;

import analytics.persistance.AnalyticsService;
import analytics.utils.AnalyticsResource;
import analytics.utils.TemplateHealthCheck;
import analytics.utils.UsecaseDemo1;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AnalyticsApplication extends Application<AnalyticsConfiguration> {

	public AnalyticsApplication() {

	}

	@Override
	public String getName() {
		return "Analytics";
	}

	public static void main(String[] args) throws Exception {

		new AnalyticsApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<AnalyticsConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new ViewBundle<AnalyticsConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(AnalyticsConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<AnalyticsConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(AnalyticsConfiguration configuration) {
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
	public void run(AnalyticsConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		// Get AnimalRegister Service
		RegisterAnimalService registerAnimalService = dbi.onDemand(RegisterAnimalService.class);
		// Get GAHO Service
		GeneralHealthObservationService generalHealthObservationService = dbi
				.onDemand(GeneralHealthObservationService.class);
		FeedPatternService feedPatternService = dbi.onDemand(FeedPatternService.class);
		AssignFeedService assignFeedService = dbi.onDemand(AssignFeedService.class);

		/*
		 * UC1Delegator uc1Delegator = new UC1Delegator(registerAnimalService,
		 * generalHealthObservationService, feedPatternService, assignFeedService);
		 */
		/*
		 * System.out.println("ADG : "+uc1Delegator.calculateADG());
		 * System.out.println("FCR : "+uc1Delegator.calculateFCR());
		 * System.out.println("DtM : "+uc1Delegator.calculateDtM(projectedWeight));
		 * System.out.println("adjustFeedingProgram : "+uc1Delegator.
		 * adjustFeedingProgramNotification(daystoSlaughter, projectedWeight));
		 */
		String animalID = "AR-1736989577458";
		try {
			
			UsecaseDemo1 demo1 = new UsecaseDemo1(feedPatternService, assignFeedService,registerAnimalService,generalHealthObservationService, animalID);
			demo1.demo();			
			
		} catch (Exception exp) {
			exp.printStackTrace();			
		}
		AnalyticsService analyticsService = dbi.onDemand(AnalyticsService.class);
		// analyticsService.create();
		AnalyticsResource analyticsResource = new AnalyticsResource(analyticsService);
		enableCORS(environment);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(analyticsResource);
	}

}
