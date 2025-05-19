package ri.se.health;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.animalexamination.persistance.AnimalExaminationService;
import com.ri.se.animalexamination.utils.AnimalExaminationResource;
import com.ri.se.animalexamination.utils.HealthCheckResource;
import com.ri.se.animalexamination.utils.TemplateHealthCheck;
import com.ri.se.assignanimalstatus.persistance.AssignAnimalStatusService;
import com.ri.se.assignanimalstatus.utils.AssignAnimalStatusResource;
import com.ri.se.drugstreatments.persistance.DrugsTreatmentsService;
import com.ri.se.drugstreatments.utils.DrugsTreatmentsResource;
import com.ri.se.generalhealthexamination.persistance.GeneralHealthExaminationService;
import com.ri.se.generalhealthexamination.utils.GeneralHealthExaminationResource;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservationService;
import com.ri.se.generalhealthobservation.utils.GeneralHealthObservationResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class HealthApplication extends Application<HealthConfiguration> {

	public HealthApplication() {

	}

	@Override
	public String getName() {
		return "AnimalExamination";
	}

	public static void main(String[] args) throws Exception {

		new HealthApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<HealthConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());

		bootstrap.addBundle(new ViewBundle<HealthConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(HealthConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<HealthConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(HealthConfiguration configuration) {
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
	public void run(HealthConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);

		environment.jersey().register(MultiPartFeature.class);

		// Creating data relations
		AnimalExaminationService animalexaminationService = dbi.onDemand(AnimalExaminationService.class);
		animalexaminationService.create();
		GeneralHealthExaminationService generalhealthexaminationService = dbi
				.onDemand(GeneralHealthExaminationService.class);
		generalhealthexaminationService.create();
		GeneralHealthObservationService generalhealthobservationService = dbi
				.onDemand(GeneralHealthObservationService.class);
		generalhealthobservationService.create();
		DrugsTreatmentsService drugstreatmentsService = dbi.onDemand(DrugsTreatmentsService.class);
		drugstreatmentsService.create();
		// Animal health status
		AssignAnimalStatusService assignAnimalStatusService = dbi.onDemand(AssignAnimalStatusService.class);
		
		// AnimalExaminationResource management
		AnimalExaminationResource animalexaminationResource = new AnimalExaminationResource(animalexaminationService);

		// ================= General Health Examination management
		GeneralHealthExaminationResource generalhealthexaminationResource = new GeneralHealthExaminationResource(
				generalhealthexaminationService, generalhealthobservationService, assignAnimalStatusService);
		// ================= General Observation Examination management
		GeneralHealthObservationResource generalhealthobservationResource = new GeneralHealthObservationResource(
				generalhealthobservationService);

		//================= Drug Treatment management
		DrugsTreatmentsResource drugstreatmentsResource = new DrugsTreatmentsResource(drugstreatmentsService);
		
		// Health Service
		HealthCheckResource healthCheckResource = new HealthCheckResource(animalexaminationService);

		enableCORS(environment);

		// Health checking
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);

		environment.jersey().register(healthCheckResource);
		environment.jersey().register(animalexaminationResource);
		environment.jersey().register(drugstreatmentsResource);
		environment.jersey().register(generalhealthobservationResource);
		environment.jersey().register(generalhealthexaminationResource);		
	}
}
