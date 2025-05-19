/*
 * package com.ri.se.generalhealthexamination.utils;
 * 
 * import java.io.File; import java.io.IOException; import java.nio.file.Files;
 * import java.nio.file.Path;
 * 
 * public class VCManager {
 * 
 * ///home/app/data/vcs private String vcDir = null; public VCManager(String
 * vcDir) throws IOException { File dir = new File(vcDir); if (!dir.exists()) {
 * Path path = Path.of(dir.getAbsolutePath()); Files.createDirectories(path); }
 * this.vcDir = vcDir ; }
 * 
 * public String createAndStoreVC(String json, String id, String username,
 * String password) throws IOException { Path path = Path.of(vcDir+
 * File.separator + id); Files.write(path, json.getBytes()); return vcDir+
 * File.separator + id; } }
 */