package org.ri.se.selectivedisclosure.vp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VCExtendedPresentationCleanerEngine {

	public static final int STRING = 0;
	public static final int NON_STRING = 1;
	public static final String QOUTE = "\"";
	public static final String ARRAY_SEPERATOR_START = "_";
	public static final String ARRAY_SEPERATOR_END = "_";

	public static void main(String[] args) throws Exception {
		String source = "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.selectivedisclosure.extended/vcexample.txt";

		//////////// sdfsdfsdfdsf/dsf
		System.out.println("----------------------------");
		byte[] data = Files.readAllBytes(Paths.get(source));
		Map<String, Object> claimsData = (Map<String, Object>) new ObjectMapper().readValue(data, Map.class);
		List<String> toInclude = new ArrayList<String>();
		toInclude.add("1.1.2.1.2.2.2");
		toInclude.add("1.1.2.1.1.1.1");
		// toInclude.add("1");
		// toInclude.add("2");
		// toInclude.add("3.2");
		VCExtendedPresentationEngine vcExtendedEngine = new VCExtendedPresentationEngine(claimsData, toInclude);
		String jsonExt = vcExtendedEngine.getExtendedJSON();
		System.out.println(jsonExt);
		Map<String, Object> presentData = (Map<String, Object>) new ObjectMapper().readValue(jsonExt, Map.class);
		VCExtendedPresentationCleanerEngine vcExtendedPresentationCleaner = new VCExtendedPresentationCleanerEngine(presentData);

		System.out.println(vcExtendedPresentationCleaner.getExtendedJSON());

	}

	public VCExtendedPresentationCleanerEngine(Map<String, Object> claimsData) throws IOException {
		buffer = new StringBuffer();
		buffer.append("{");
		cleanPresentation(claimsData, buffer);
		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());
	}

	private StringBuffer buffer = null;

	public boolean cleanPresentation(Map<String, Object> claimsData, StringBuffer buffer) throws IOException {
		//JSONObject json = new JSONObject(claimsData);
		List sortedKeysJson = new ArrayList(claimsData.keySet());
		int no = 0;
		boolean isIterationReq = false;
		for (int i = 0; i < sortedKeysJson.size(); i++) {
			no++;
			String attribute = (String) sortedKeysJson.get(i);
			Object obj = claimsData.get(attribute);
			if (obj.toString().equals("{}")) {
				// Ignore it
			} else if (obj instanceof Date || obj instanceof String) {

				buffer.append(QOUTE + attribute + QOUTE + ":" + QOUTE + obj + QOUTE + ",");
			} else if (obj instanceof Integer || obj instanceof Long || obj instanceof Double
					|| obj instanceof Boolean) {

				buffer.append(QOUTE + attribute + QOUTE + ":" + QOUTE + obj + QOUTE + ",");
			} else if (obj instanceof ArrayList) {

				buffer.append(QOUTE + attribute + QOUTE + ":[");
				ArrayList<Object> o = (ArrayList) obj;
				isIterationReq = manageArray(attribute, o, buffer);
			} else if (obj instanceof Map) {

				buffer.append(QOUTE + attribute + QOUTE + ": {");
				Map<String, Object> claimsData2 = (Map<String, Object>) obj;
				isIterationReq = cleanPresentation(claimsData2, buffer);
			} else {
				int test = (Integer) obj;
				return false;
			}
		}

		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());

		buffer.append("},");
		return isIterationReq;
	}

	public boolean manageArray(String key, ArrayList<Object> o, StringBuffer buffer) throws IOException {
		boolean isIterationReq = false;
		for (int i = 0; i < o.size(); i++) {
			if (o.toString().equals("{}")) {
				// Ignore it
			} else if (o.get(i) instanceof Date || o.get(i) instanceof String) {

				buffer.append(QOUTE + o.get(i) + QOUTE + ",");
			} else if (o.get(i) instanceof Integer || o.get(i) instanceof Long || o.get(i) instanceof Double
					|| o.get(i) instanceof Boolean) {
				buffer.append(o.get(i) + ",");
			} else if (o.get(i) instanceof ArrayList) {

				buffer.append(QOUTE + key + QOUTE + " {");
				isIterationReq = manageArray(key, (ArrayList) o.get(i), buffer);
			} else if (o.get(i) instanceof Map) {

				buffer.append("{");
				Map<String, Object> claimsData2 = (Map<String, Object>) o.get(i);
				isIterationReq = cleanPresentation(claimsData2, buffer);
			} else {
				throw new IOException("Problems when processing JSON !");
			}
		}
		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());

		buffer.append("],");
		return isIterationReq;
	}

	public String getExtendedJSON() throws JsonParseException, JsonMappingException, IOException {
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			Map<String, Object> c = (Map<String, Object>) mapper.readValue(buffer.toString(), Map.class);
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new IOException("There is an issue to generate a JSON, please contact with System owner !");
		}
	}
}
