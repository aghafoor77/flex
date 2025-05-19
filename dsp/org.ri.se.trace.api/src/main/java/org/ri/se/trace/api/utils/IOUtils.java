package org.ri.se.trace.api.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public class IOUtils {

	private String locDir = null;

	public IOUtils() {

	}

	public IOUtils(String owner) {
		locDir = "resources" + File.separator + owner + File.separator;
		System.out.println(new File(locDir).getAbsolutePath());

	}

	/**
	 * 
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @return
	 * @throws IOException
	 */
	public String write(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) throws IOException {
		try {
			if (!new File(locDir).exists()) {
				java.nio.file.Path path = Paths.get(locDir);
				Files.createDirectories(path);
			}
			long did = System.currentTimeMillis();
			String location = locDir + did + "_" + fileDetail.getFileName();
			writeToFile(uploadedInputStream, location);
			return new File(location).getAbsolutePath();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 
	 * @param uploadedInputStream
	 * @param uploadedFileLocation
	 * @throws IOException
	 */
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
		int read;
		final int BUFFER_LENGTH = 1024;
		final byte[] buffer = new byte[BUFFER_LENGTH];
		OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
		while ((read = uploadedInputStream.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public String writeToFile(String content) throws IOException {

		String filePath = locDir + "identity.json";
		System.out.println(filePath);

		if (!new File(locDir).exists()) {
			java.nio.file.Path path = Paths.get(locDir);
			Files.createDirectories(path);
		}
		Files.write(Paths.get(filePath), content.getBytes());
		return filePath;
	}

	public void writeSmartContract(String name, String smartContractAddress) throws IOException {
		String filePath = name + ".txt";
		Files.write(Paths.get(filePath), smartContractAddress.getBytes());
	}

	public String readSmartContract(String name) throws IOException {
		String filePath = name + ".txt";
		System.out.println(new File(filePath).getAbsolutePath());
		if (!new File(filePath).exists()) {
			return null;
		}
		byte[] smartBytes = Files.readAllBytes(Paths.get(filePath));
		if (Objects.isNull(smartBytes) || smartBytes.length == 0) {
			return null;
		}
		return new String(smartBytes);
	}
}
