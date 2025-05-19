/*
 * package org.acreo.common.utils;
 * 
 * import java.util.Objects;
 * 
 * import org.acreo.common.exceptions.VeidblockException; import
 * org.slf4j.Logger; import org.slf4j.LoggerFactory;
 * 
 * public class PathManager {
 * 
 * final static Logger logger = LoggerFactory.getLogger(PathManager.class);
 * private static String authServer =null;//"http://localhost:9000";// verify
 * private static String ipvServer =null;// "http://localhost:8000";// IPV
 * 
 * public PathManager(String authServer_, String ipvServer_) { authServer =
 * authServer_; ipvServer = ipvServer_; }
 * 
 * public String getAuthServerUrl() { if (Objects.isNull(authServer)) { try {
 * throw new VeidblockException(
 * "Autheticatin service end-point not exists. Please specify in config.props file !"
 * ); } catch (VeidblockException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } } return authServer; }
 * 
 * public String getIPVServerUrl() { if (Objects.isNull(ipvServer)) { try {
 * throw new
 * VeidblockException("IPV service end-point not exists. Please specify in config.props file !"
 * ); } catch (VeidblockException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } } return ipvServer; } public static PathManager
 * getPathManager(){ return new PathManager(authServer, ipvServer); } public
 * static void status(){ logger.info(VeidblockLogger.logMarker
 * +"authServer  : "+authServer ); logger.info(VeidblockLogger.logMarker
 * +"ipvServer   : "+ipvServer ); } }
 */