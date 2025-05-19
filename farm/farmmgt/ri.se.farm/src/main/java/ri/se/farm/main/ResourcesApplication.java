package ri.se.farm.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ri.se.farm.dto.DTOFacility;
import ri.se.farm.dto.DTOFacilityList;
import ri.se.farm.dto.FacilityEntityConverter;
import ri.se.farm.dto.FarmEntityConverter;
import ri.se.farm.dto.FarmFacility;
import ri.se.farm.persistance.Facility;
import ri.se.farm.persistance.FacilityList;
import ri.se.farm.persistance.FacilityService;
import ri.se.farm.persistance.Farm;
import ri.se.farm.persistance.FarmService;
import ri.se.farm.persistance.FarmerService;
import ri.se.farm.utils.FacilityResource;
import ri.se.farm.utils.FarmResource;
import ri.se.farm.utils.FarmerResource;
import ri.se.farm.utils.HealthCheckResource;
import ri.se.farm.utils.TemplateHealthCheck;

public class ResourcesApplication extends Application<ResourcesConfiguration> {

	public ResourcesApplication() {

	}

	@Override
	public String getName() {
		return "Resources";
	}

	public static void main(String[] args) throws Exception {

		new ResourcesApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<ResourcesConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new ViewBundle<ResourcesConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(ResourcesConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<ResourcesConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(ResourcesConfiguration configuration) {
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
	public void run(ResourcesConfiguration configuration, Environment environment) throws Exception {
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
		loadFarmFacility(farmService, facilityService);
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

	private static void loadFarmFacility(FarmService farmService, FacilityService facilityService) {
		byte[] content = null;
		try {
			 content = Files.readAllBytes(Paths.get("/home/devvm/Desktop/RISE/formas/farm/farmmgt/ri.se.farm/farm.txt"));
			//
		} catch (IOException exp) {
			exp.printStackTrace();
			try {
				content = Files.readAllBytes(Paths.get("/home/app/data/farm.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
			
		}
		FarmFacility farmFacility = null;
		try {
			farmFacility = new ObjectMapper().readValue(content, FarmFacility.class);
		} catch (Exception exp) {
			exp.printStackTrace();
			System.exit(1);
		}
		FacilityEntityConverter facilityEntityConverter = new FacilityEntityConverter();

		DTOFacilityList farmfList = farmFacility.getDtoFacilityList();

		FacilityList facilityList = new FacilityList();
		Farm farm = null;
		try {
			farm = new FarmEntityConverter().getFarm(farmFacility.getFarm());
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		for (DTOFacility dtoFacility : farmfList) {
			Facility temp = null;
			try {
				temp = facilityEntityConverter.getFacility(dtoFacility);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long random = new Random().nextLong();
			temp.setFacilityId("FF:" + (random < 0 ? (-1 * random) : random));
			temp.setFarmId(farm.getFarmId());
			facilityList.add(temp);
		}
		try {
			farmService.post(farm);
		} catch (Exception exp) {
			exp.printStackTrace();
			System.err.println("" + exp.getMessage());
			System.err.println("Farm is already registerted. Moving to next statement !");
		}

		for (Facility temp : facilityList) {
			try {
				if(Objects.isNull(facilityService.getByFacilityFarm(temp.getFacilityNr(), temp.getFarmId())))
						facilityService.post(temp);
			} catch (Exception exp) {
				exp.printStackTrace();
				System.err.println("" + exp.getMessage());
				System.err.println("Facility is already registerted. Moving to next statement !");
			}
		}
	}
}