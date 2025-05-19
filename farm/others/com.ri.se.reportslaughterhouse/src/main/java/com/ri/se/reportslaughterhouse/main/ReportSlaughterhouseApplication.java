package com.ri.se.reportslaughterhouse.main;

import java.net.SocketException;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.ri.se.reportslaughterhouse.persistance.ReportSlaughterhouseService;
import com.ri.se.reportslaughterhouse.utils.ReportSlaughterhouseResource;
import com.ri.se.reportslaughterhouseanimals.persistance.ReportSlaughterhouseAnimalsService;
import com.ri.se.reportslaughterhouseanimals.utils.HealthCheckResource;
import com.ri.se.reportslaughterhouseanimals.utils.ReportSlaughterhouseAnimalsResource;
import com.ri.se.reportslaughterhouseanimals.utils.TemplateHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


public class ReportSlaughterhouseApplication extends Application<ReportSlaughterhouseConfiguration> {


	public ReportSlaughterhouseApplication(){
		
	}

	@Override
	public String getName() {
		return "ReportSlaughterhouse";
	}

	public static void main(String[] args) throws Exception {

		new ReportSlaughterhouseApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<ReportSlaughterhouseConfiguration> bootstrap){
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
			bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		
		bootstrap.addBundle(new ViewBundle<ReportSlaughterhouseConfiguration>(){
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(ReportSlaughterhouseConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<ReportSlaughterhouseConfiguration>(){
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(ReportSlaughterhouseConfiguration configuration){
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
	public void run(ReportSlaughterhouseConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		ReportSlaughterhouseService reportslaughterhouseService = dbi.onDemand(ReportSlaughterhouseService.class);
		reportslaughterhouseService.create();
		ReportSlaughterhouseAnimalsService reportslaughterhouseanimalsService = dbi.onDemand(ReportSlaughterhouseAnimalsService.class);
		reportslaughterhouseanimalsService.create();
		
		ReportSlaughterhouseResource reportslaughterhouseResource = new ReportSlaughterhouseResource(reportslaughterhouseService);
		ReportSlaughterhouseAnimalsResource reportslaughterhouseanimalsResource = new ReportSlaughterhouseAnimalsResource(reportslaughterhouseanimalsService);
		
		enableCORS(environment);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		
		HealthCheckResource healthCheckResource = new HealthCheckResource(reportslaughterhouseService);
		environment.jersey().register(healthCheckResource);
		
		
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(reportslaughterhouseResource);
		environment.jersey().register(reportslaughterhouseanimalsResource);
		
	}


}
