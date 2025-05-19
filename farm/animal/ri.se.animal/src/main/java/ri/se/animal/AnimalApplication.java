package ri.se.animal;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.animalderegister.persistance.AnimalDeregisterService;
import com.ri.se.animalderegister.utils.AnimalDeregisterResource;
import com.ri.se.animalreg.persistance.RegisterAnimalService;
import com.ri.se.animalreg.utils.HealthCheckResource;
import com.ri.se.animalreg.utils.RegisterAnimalResource;
import com.ri.se.animalreg.utils.TemplateHealthCheck;
import com.ri.se.assignanimal.persistance.AssignAnimalService;
import com.ri.se.assignanimal.utils.AssignAnimalResource;
import com.ri.se.assignanimalstatus.persistance.AssignAnimalStatusService;
import com.ri.se.assignanimalstatus.utils.AssignAnimalStatusResource;
import com.ri.se.breed.persistance.BreedService;

import com.ri.se.breed.utils.BreedResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AnimalApplication extends Application<AnimalConfiguration> {

	public AnimalApplication() {
	}

	@Override
	public String getName() {
		return "RegisterAnimal";
	}

	public static void main(String[] args) throws Exception {
		new AnimalApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<AnimalConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());

		bootstrap.addBundle(new ViewBundle<AnimalConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(AnimalConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<AnimalConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(AnimalConfiguration configuration) {
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
	public void run(AnimalConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);

		//======================================================================================
		// Creating data relations 
		BreedService breedService = dbi.onDemand(BreedService.class);
		breedService.create();
		RegisterAnimalService registeranimalService = dbi.onDemand(RegisterAnimalService.class);
		registeranimalService.create();
		AssignAnimalService assignanimalService = dbi.onDemand(AssignAnimalService.class);
		assignanimalService.create();
		AnimalDeregisterService animalderegisterService = dbi.onDemand(AnimalDeregisterService.class);
		animalderegisterService.create();
		AssignAnimalStatusService assignAnimalStatusService = dbi.onDemand(AssignAnimalStatusService.class);
		assignAnimalStatusService.create();
		
		//======================================================================================
		// Resources 
		// Breeding
		BreedResource breedResource = new BreedResource(breedService);

		// Register Animal
		RegisterAnimalResource registeranimalResource = new RegisterAnimalResource(registeranimalService);

		AssignAnimalResource assignanimalResource = new AssignAnimalResource(assignanimalService);
		
		AssignAnimalStatusResource assignAnimalStatusResource = new AssignAnimalStatusResource(assignAnimalStatusService); 

		//Deregister Animal
		AnimalDeregisterResource animalderegisterResource = new AnimalDeregisterResource(animalderegisterService);
		
		// Health Service
		HealthCheckResource healthCheckResource = new HealthCheckResource(registeranimalService);
				
		//======================================================================================
		enableCORS(environment);
		
		// Health checking
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		
		environment.jersey().register(healthCheckResource);
		environment.jersey().register(breedResource);		
		environment.jersey().register(registeranimalResource);
		environment.jersey().register(assignAnimalStatusResource);
		environment.jersey().register(assignanimalResource);
		environment.jersey().register(animalderegisterResource);
	}
}
