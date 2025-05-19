package transporter.main;

import transporter.persistance.*;
import transporter.utils.*;
import java.net.SocketException;
import java.util.Map;
import javax.sql.DataSource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import carrier.persistance.CarrierService;
import carrier.utils.CarrierResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import transportedcattle.persistance.TransportedCattleService;
import transportedcattle.utils.TransportedCattleResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import java.util.EnumSet;


public class TransporterApplication extends Application<TransporterConfiguration> {


	public TransporterApplication(){
		
	}

	@Override
	public String getName() {
		return "Transporter";
	}

	public static void main(String[] args) throws Exception {

		new TransporterApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<TransporterConfiguration> bootstrap){
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
			bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new ViewBundle<TransporterConfiguration>(){
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(TransporterConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<TransporterConfiguration>(){
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(TransporterConfiguration configuration){
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
	public void run(TransporterConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		TransporterService transporterService = dbi.onDemand(TransporterService.class);
		transporterService.create();
		CarrierService carrierService = dbi.onDemand(CarrierService.class);
		carrierService.create();
		TransportedCattleService transportedcattleService = dbi.onDemand(TransportedCattleService.class);
		transportedcattleService.create();
		TransportedCattleResource transportedcattleResource = new TransportedCattleResource(transportedcattleService);
		CarrierResource carrierResource = new CarrierResource(carrierService);
		TransporterResource transporterResource = new TransporterResource(transporterService);
		enableCORS(environment);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(transporterResource);
		environment.jersey().register(carrierResource);
		environment.jersey().register(transportedcattleResource);		
	}
}

