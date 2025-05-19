/*
 * package com.rise.vdr.api.main;
 * 
 * import com.rise.vdr.api.persistance.*; import com.rise.vdr.api.utils.*;
 * import java.net.SocketException; import java.util.Map; import
 * javax.sql.DataSource; import
 * org.glassfish.jersey.media.multipart.MultiPartFeature; import
 * org.skife.jdbi.v2.DBI; import io.dropwizard.Application; import
 * io.dropwizard.assets.AssetsBundle; import
 * io.dropwizard.configuration.EnvironmentVariableSubstitutor; import
 * io.dropwizard.configuration.SubstitutingSourceProvider; import
 * io.dropwizard.db.DataSourceFactory; import
 * io.dropwizard.migrations.MigrationsBundle; import
 * io.dropwizard.setup.Bootstrap; import io.dropwizard.setup.Environment; import
 * io.dropwizard.views.ViewBundle; import
 * io.federecio.dropwizard.swagger.SwaggerBundle; import
 * io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
 * 
 * 
 * public class SCTransactionApplication extends
 * Application<SCTransactionConfiguration> {
 * 
 * 
 * public SCTransactionApplication(){
 * 
 * }
 * 
 * @Override public String getName() { return "SCTransaction"; }
 * 
 * public static void main(String[] args) throws Exception {
 * 
 * new SCTransactionApplication().run(args); }
 * 
 * @Override public void initialize(Bootstrap<SCTransactionConfiguration>
 * bootstrap){ bootstrap.setConfigurationSourceProvider(new
 * SubstitutingSourceProvider( bootstrap.getConfigurationSourceProvider(), new
 * EnvironmentVariableSubstitutor(false))); bootstrap.addBundle(new
 * AssetsBundle()); bootstrap.addBundle(new
 * MigrationsBundle<SCTransactionConfiguration>(){
 * 
 * @Override public DataSourceFactory
 * getDataSourceFactory(SCTransactionConfiguration configuration){ return
 * configuration.getDataSourceFactory(); } }); bootstrap.addBundle(new
 * ViewBundle<SCTransactionConfiguration>(){
 * 
 * @Override public Map<String, Map<String, String>>
 * getViewConfiguration(SCTransactionConfiguration configuration) { return
 * configuration.getViewRendererConfiguration(); } }); bootstrap.addBundle(new
 * SwaggerBundle<SCTransactionConfiguration>(){
 * 
 * @Override public SwaggerBundleConfiguration
 * getSwaggerBundleConfiguration(SCTransactionConfiguration configuration){
 * return configuration.getSwaggerBundleConfiguration(); } }); }
 * 
 * 
 * private static final String SQL = "sql";
 * 
 * @Override public void run(SCTransactionConfiguration configuration,
 * Environment environment) throws SocketException { final DataSource dataSource
 * = configuration.getDataSourceFactory().build(environment.metrics(), SQL); DBI
 * dbi = new DBI(dataSource); SCTransactionService sctransactionService =
 * dbi.onDemand(SCTransactionService.class); sctransactionService.create();
 * SCTransactionResource sctransactionResource = new
 * SCTransactionResource(sctransactionService); final TemplateHealthCheck
 * healthCheck = new TemplateHealthCheck(configuration.getTemplate());
 * environment.healthChecks().register("template", healthCheck);
 * environment.jersey().register(MultiPartFeature.class);
 * environment.jersey().register(sctransactionResource); }
 * 
 * 
 * }
 */