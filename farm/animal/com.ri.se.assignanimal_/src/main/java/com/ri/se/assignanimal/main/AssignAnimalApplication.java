package com.ri.se.assignanimal.main;

import com.ri.se.assignanimal.persistance.*;
import com.ri.se.assignanimal.utils.*;
import java.net.SocketException;
import java.util.Map;
import javax.sql.DataSource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;
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
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import java.util.EnumSet;


public class AssignAnimalApplication extends Application<AssignAnimalConfiguration> {


	public AssignAnimalApplication(){
		
	}

	@Override
	public String getName() {
		return "AssignAnimal";
	}

	public static void main(String[] args) throws Exception {

		new AssignAnimalApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<AssignAnimalConfiguration> bootstrap){
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
			bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new ViewBundle<AssignAnimalConfiguration>(){
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(AssignAnimalConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<AssignAnimalConfiguration>(){
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(AssignAnimalConfiguration configuration){
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
	public void run(AssignAnimalConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		AssignAnimalService assignanimalService = dbi.onDemand(AssignAnimalService.class);
		assignanimalService.create();
		AssignAnimalResource assignanimalResource = new AssignAnimalResource(assignanimalService);
		enableCORS(environment);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(assignanimalResource);
	}


}
