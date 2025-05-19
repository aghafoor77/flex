package com.rise.se.slaughterhouse.main;

import java.net.SocketException;
import java.util.Map;

import javax.sql.DataSource;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.rise.se.slaughterhouse.persistance.RAnimalSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.RCarrierSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.RSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.SlaughterhouseService;
import com.rise.se.slaughterhouse.utils.RAnimalSlaughterhouseDataResource;
import com.rise.se.slaughterhouse.utils.RCarrierSlaughterhouseDataResource;
import com.rise.se.slaughterhouse.utils.RSlaughterhouseDataResource;
import com.rise.se.slaughterhouse.utils.SlaughterhouseResource;
import com.rise.se.slaughterhouse.utils.TemplateHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


public class SlaughterhouseApplication extends Application<SlaughterhouseConfiguration> {


	public SlaughterhouseApplication(){
		
	}

	@Override
	public String getName() {
		return "Slaughterhouse";
	}

	public static void main(String[] args) throws Exception {

		new SlaughterhouseApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<SlaughterhouseConfiguration> bootstrap){
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
			bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new MigrationsBundle<SlaughterhouseConfiguration>(){
			@Override
			public DataSourceFactory getDataSourceFactory(SlaughterhouseConfiguration configuration){
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(new ViewBundle<SlaughterhouseConfiguration>(){
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(SlaughterhouseConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<SlaughterhouseConfiguration>(){
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(SlaughterhouseConfiguration configuration){
			return configuration.getSwaggerBundleConfiguration();
			}
		});
	}


	private static final String SQL = "sql";

	@Override
	public void run(SlaughterhouseConfiguration configuration, Environment environment) throws SocketException {
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		
		RAnimalSlaughterhouseDataService ranimalslaughterhousedataService = dbi.onDemand(RAnimalSlaughterhouseDataService.class);
		ranimalslaughterhousedataService.create();
		RAnimalSlaughterhouseDataResource ranimalslaughterhousedataResource = new RAnimalSlaughterhouseDataResource(ranimalslaughterhousedataService);
		
		environment.jersey().register(ranimalslaughterhousedataResource);
		
		RCarrierSlaughterhouseDataService rcarrierslaughterhousedataService = dbi.onDemand(RCarrierSlaughterhouseDataService.class);
		rcarrierslaughterhousedataService.create();
		RCarrierSlaughterhouseDataResource rcarrierslaughterhousedataResource = new RCarrierSlaughterhouseDataResource(rcarrierslaughterhousedataService);
		environment.jersey().register(rcarrierslaughterhousedataResource);
		
		RSlaughterhouseDataService rslaughterhousedataService = dbi.onDemand(RSlaughterhouseDataService.class);
		rslaughterhousedataService.create();
		RSlaughterhouseDataResource rslaughterhousedataResource = new RSlaughterhouseDataResource(rslaughterhousedataService);
		environment.jersey().register(rslaughterhousedataResource);
		
		SlaughterhouseService slaughterhouseService = dbi.onDemand(SlaughterhouseService.class);
		slaughterhouseService.create();
		SlaughterhouseResource slaughterhouseResource = new SlaughterhouseResource(slaughterhouseService);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(slaughterhouseResource);
		
	}
}
