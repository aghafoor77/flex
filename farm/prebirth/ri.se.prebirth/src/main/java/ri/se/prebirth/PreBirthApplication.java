package ri.se.prebirth;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.movebullforherd.persistance.MoveBullForHerdService;
import com.ri.se.movebullforherd.utils.MoveBullForHerdResource;
import com.ri.se.ordersemen.persistance.OrderSemenService;
import com.ri.se.ordersemen.utils.OrderSemenResource;
import com.ri.se.responseordersemen.persistance.ResponseOrderSemenService;
import com.ri.se.responseordersemen.utils.HealthCheckResource;
import com.ri.se.responseordersemen.utils.ResponseOrderSemenResource;
import com.ri.se.responseordersemen.utils.TemplateHealthCheck;
import com.ri.se.semenutilization.persistance.SemenUtilizationService;
import com.ri.se.semenutilization.utils.SemenUtilizationResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class PreBirthApplication extends Application<PreBirthConfiguration> {

	public PreBirthApplication() {

	}

	@Override
	public String getName() {
		return "OrderSemen";
	}

	public static void main(String[] args) throws Exception {

		new PreBirthApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<PreBirthConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());

		bootstrap.addBundle(new ViewBundle<PreBirthConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(PreBirthConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<PreBirthConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(PreBirthConfiguration configuration) {
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
	public void run(PreBirthConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);

		// ======================================================================================
		// Creating data relations
		OrderSemenService ordersemenService = dbi.onDemand(OrderSemenService.class);
		ordersemenService.create();
		ResponseOrderSemenService responseordersemenService = dbi.onDemand(ResponseOrderSemenService.class);
		responseordersemenService.create();
		SemenUtilizationService semenutilizationService = dbi.onDemand(SemenUtilizationService.class);
		semenutilizationService.create();
		MoveBullForHerdService movebullforherdService = dbi.onDemand(MoveBullForHerdService.class);
		movebullforherdService.create();
		
		
		// ======================================================================================
		// Resources
		// Order Semen
		OrderSemenResource ordersemenResource = new OrderSemenResource(ordersemenService);

		// Response Order Semen
		ResponseOrderSemenResource responseordersemenResource = new ResponseOrderSemenResource(
				responseordersemenService);

		// Semen Utilization
		SemenUtilizationResource semenutilizationResource = new SemenUtilizationResource(semenutilizationService);

		// Move Bull For Herd
		MoveBullForHerdResource movebullforherdResource = new MoveBullForHerdResource(movebullforherdService);
		
		
		// Health Service
		HealthCheckResource healthCheckResource = new HealthCheckResource(responseordersemenService);

		// ======================================================================================
		enableCORS(environment);

		// Health checking
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(healthCheckResource);

		environment.jersey().register(ordersemenResource);
		environment.jersey().register(responseordersemenResource);
		environment.jersey().register(semenutilizationResource);
		environment.jersey().register(movebullforherdResource);
	}
}
