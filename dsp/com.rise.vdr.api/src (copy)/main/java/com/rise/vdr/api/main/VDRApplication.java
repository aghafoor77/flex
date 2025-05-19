package com.rise.vdr.api.main;


import java.io.File;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rise.vdr.api.persistance.SCEvidenceService;
import com.rise.vdr.api.persistance.SCResourceService;
import com.rise.vdr.api.persistance.SCSecretKeyService;
import com.rise.vdr.api.persistance.SCTransactionService;
import com.rise.vdr.api.utils.SCEvidenceResource;
import com.rise.vdr.api.utils.SCResourceResource;
import com.rise.vdr.api.utils.SCSecretKeyResource;
import com.rise.vdr.api.utils.SCTransactionResource;
import com.rise.vdr.api.utils.TemplateHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class VDRApplication extends Application<SCTransactionConfiguration> {

	private static final Logger logger_ = LoggerFactory.getLogger(VDRApplication.class);

	public VDRApplication() {
	}

	public static void main(String[] args) throws Exception {
		new VDRApplication().run(args);
	}

	@Override
	public String getName() {
		return "VDR";
	}

	@Override
	public void initialize(Bootstrap<SCTransactionConfiguration> bootstrap) {
		// Enable variable substitution with environment variables
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

		bootstrap.addBundle(new AssetsBundle());
	
		// bootstrap.addBundle(hibernateBundle);
		bootstrap.addBundle(new ViewBundle<SCTransactionConfiguration>() {
			@Override
			public Map<String, Map<String, String>> getViewConfiguration(SCTransactionConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});

		bootstrap.addBundle(new SwaggerBundle<SCTransactionConfiguration>() {
			@Override
			public SwaggerBundleConfiguration getSwaggerBundleConfiguration(SCTransactionConfiguration configuration) {
				return configuration.getSwaggerBundleConfiguration();
			}
		});
	}

	private static final String SQL = "sql";

	@Override
	public void run(SCTransactionConfiguration configuration, Environment environment) {

		enableCORS(environment);
		logger_.debug("===> Configuration file processed successfully !");

			
		final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), SQL);
		DBI dbi = new DBI(dataSource);
		
		
		SCEvidenceService scevidenceService = dbi.onDemand(SCEvidenceService.class);
		scevidenceService.create();
		SCEvidenceResource scevidenceResource = new SCEvidenceResource(scevidenceService);
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(scevidenceResource);
		
		SCResourceService scresourceService = dbi.onDemand(SCResourceService.class);
		scresourceService.create();
		SCResourceResource scresourceResource = new SCResourceResource(scresourceService);
		
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(scresourceResource);
		
		SCSecretKeyService scsecretkeyService = dbi.onDemand(SCSecretKeyService.class);
		scsecretkeyService.create();
		SCSecretKeyResource scsecretkeyResource = new SCSecretKeyResource(scsecretkeyService);
		
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(scsecretkeyResource);
		VDRVerificationFilter vdrVerificationFilter = new VDRVerificationFilter();
		SCTransactionService sctransactionService = dbi.onDemand(SCTransactionService.class);
		sctransactionService.create();
		SCTransactionResource sctransactionResource = new SCTransactionResource(sctransactionService);
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(sctransactionResource);
		
		environment.jersey().register(new AuthDynamicFeature(vdrVerificationFilter));
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
	
	public String getWalletDir() {
		String homeDir = System.getProperty("user.home");
		String dir = homeDir + File.separator + "veidblock_RT" + File.separator + "credentials"
				+ File.separator;
		return dir;
	}	
	
}
