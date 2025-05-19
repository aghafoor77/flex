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

public class VCExtendedPresentationEngine {

	public static final int STRING = 0;
	public static final int NON_STRING = 1;
	public static final String QOUTE = "\"";
	public static final String ARRAY_SEPERATOR_START = "_";
	public static final String ARRAY_SEPERATOR_END = "_";

	public static void main(String[] args) throws Exception {
		// String source =
		// "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/health2.txt";
		// String source =
		// "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/health1.txt";

		// String source =
		// "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/health1.txt";
		// String source =
		// "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.selectivedisclosure.extended/elm.json";
		// String sourceSchema =
		// "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.selectivedisclosure.extended/elmSchema.json";
		String source = "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.selectivedisclosure/vcelmexample.txt";

		//////////// sdfsdfsdfdsf/dsf
		System.out.println("----------------------------");
		byte[] data = Files.readAllBytes(Paths.get(source));
		Map<String, Object> claimsData = (Map<String, Object>) new ObjectMapper().readValue(data, Map.class);
		List<String> toInclude = new ArrayList<String>();
		toInclude.add("2.3");
		toInclude.add("2.1");
		// toInclude.add("1");
		// toInclude.add("2");
		// toInclude.add("3.2");
		VCExtendedPresentationEngine vcExtendedEngine = new VCExtendedPresentationEngine(claimsData, toInclude);
		String jsonExt = vcExtendedEngine.getExtendedJSON();
		System.out.println(jsonExt);
	}

	private StringBuffer buffer = null;

	public VCExtendedPresentationEngine(Map<String, Object> claimsData, List<String> toInclude) throws IOException {

		buffer = new StringBuffer();
		buffer.append("{");
		boolean isConsider = false;
		createPresentation(claimsData, buffer, toInclude, isConsider);
		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());
		
		Map<String, Object> presentData = (Map<String, Object>) new ObjectMapper().readValue(buffer.toString(), Map.class);
		VCExtendedPresentationCleanerEngine vcExtendedPresentationCleanerEngine = new VCExtendedPresentationCleanerEngine(presentData);
		buffer = new StringBuffer();
		buffer.append(vcExtendedPresentationCleanerEngine.getExtendedJSON());
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

	public boolean createPresentation(Map<String, Object> claimsData, StringBuffer buffer, List<String> toInclude,
			boolean isConsider) throws IOException {
		//JSONObject json = new JSONObject(claimsData);
		List sortedKeysJson = new ArrayList(claimsData.keySet());

		int no = 0;
		for (int i = 0; i < sortedKeysJson.size(); i++) {
			no++;
			String attribute = (String) sortedKeysJson.get(i);
			Object obj = claimsData.get(attribute);
			if (attribute.equals("@context")) {
				String newContext = "";
				Object context = claimsData.get(attribute);
				List<String> contexts = (List<String>)claimsData.get(attribute);
				newContext = newContext + QOUTE + attribute + QOUTE + ": [";
				for(String aContext : contexts) {
					newContext = newContext+ QOUTE + aContext + QOUTE + ",";
				}
				if(newContext.endsWith(","))
						newContext = newContext.substring(0, newContext.length()-1);
				
				newContext = newContext+"],";
				buffer.append(newContext );				
				
			} else 
			// ======================================
			if (obj instanceof Date || obj instanceof String) {
				if (isConsider)
					buffer.append(QOUTE + attribute + QOUTE + ":" + QOUTE + obj + QOUTE + ",");
			} else if (obj instanceof Integer || obj instanceof Long || obj instanceof Double
					|| obj instanceof Boolean) {
				if (isConsider)
					buffer.append(QOUTE + attribute + QOUTE + ":" + QOUTE + obj + QOUTE + ",");
			} else if (obj instanceof ArrayList) {
				buffer.append(QOUTE + attribute + QOUTE + ":[");
				ArrayList<Object> o = (ArrayList) obj;
				manageArray(attribute, o, buffer, toInclude, toInclude.contains(attribute));
			} else if (obj instanceof Map) {
				buffer.append(QOUTE + attribute + QOUTE + ": {");
				Map<String, Object> claimsData2 = (Map<String, Object>) obj;
				createPresentation(claimsData2, buffer, toInclude, toInclude.contains(attribute));
			} else {
				int test = (Integer) obj;
				return false;
			}
		}

		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());
		buffer.append("},");
		return true;
	}

	public boolean manageArray(String key, ArrayList<Object> o, StringBuffer buffer, List<String> toInclude,
			boolean isConsider) throws IOException {
		for (int i = 0; i < o.size(); i++) {

			if (o.get(i) instanceof Date || o.get(i) instanceof String) {
				if (isConsider)
					buffer.append(QOUTE + o.get(i) + QOUTE + ",");
			} else if (o.get(i) instanceof Integer || o.get(i) instanceof Long || o.get(i) instanceof Double
					|| o.get(i) instanceof Boolean) {
				if (isConsider)
					buffer.append(o.get(i) + ",");
			} else if (o.get(i) instanceof ArrayList) {
				buffer.append(QOUTE + key + QOUTE + " {");
				manageArray(key, (ArrayList) o.get(i), buffer, toInclude, toInclude.contains(key));
			} else if (o.get(i) instanceof Map) {
				buffer.append("{");
				Map<String, Object> claimsData2 = (Map<String, Object>) o.get(i);
				createPresentation(claimsData2, buffer, toInclude, toInclude.contains(false));
			} else {
				throw new IOException("Problems when processing JSON !");
			}
		}
		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());
		buffer.append("],");
		return true;
	}
}
