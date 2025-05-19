package org.ri.se.selectivedisclosure.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SchemaValidationEngine {

	public static void main(String[] args) throws Exception {
		
			String source = "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.selectivedisclosure.extended/elm.json";
			String sourceSchema = "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.selectivedisclosure.extended/elmSchema.json";
			byte[] data = Files.readAllBytes(Paths.get(source));
			byte[] dataSchema = Files.readAllBytes(Paths.get(sourceSchema));
			SchemaValidationEngine schemaValidationEngine = new SchemaValidationEngine();
			System.out.println(schemaValidationEngine.validateJSON(new String(dataSchema)));
			System.out.println(schemaValidationEngine.validateDocument(new String(dataSchema), new String(data)));
		
	}
	public SchemaValidationEngine() {
		
	}
	public boolean validateJSON(String json) throws IOException {
		Map<String, Object> jsonMap = (Map<String, Object>) new ObjectMapper().readValue(json, Map.class);
		//new ObjectMapper().readTree(json);
		return true;
	}

	public boolean validateDocument(String schema, String document) throws Exception {
		Map<String, Object> claimsData = (Map<String, Object>) new ObjectMapper().readValue(document, Map.class);
		Map<String, Object> schemaData = (Map<String, Object>) new ObjectMapper().readValue(schema, Map.class);
		process(schemaData, claimsData);
		return true;
	}

	private boolean process(Map<String, Object> schema, Map<String, Object> claimsData) throws IOException {
		//JSONObject json = new JSONObject(claimsData);
		List<String> sortedKeysJson = new ArrayList<String>(claimsData.keySet());
		Collections.sort(sortedKeysJson);
		// ================ Handling schema ================
		//JSONObject jsonSchema = new JSONObject(schema);
		List sortedKeysSchema = new ArrayList(claimsData.keySet());
		Collections.sort(sortedKeysSchema);
		if (sortedKeysSchema.size() != sortedKeysJson.size()) {
			throw new IOException("Invalid Document !");
		}

		int no = 0;
		for (int i = 0; i < sortedKeysJson.size(); i++) {
			no++;
			String attribute = (String) sortedKeysJson.get(i);
			Object obj = claimsData.get(attribute);
			// ================ Handling schema ================
			String attSchema = (String) sortedKeysSchema.get(i);
			
			if(attribute.equals("Test")) {
				System.out.println("============"  + attribute);	
			}
			if (!schema.containsKey(attribute)) {
				throw new IOException("Invalid attribute (in the Document[" + attribute+ ":" + obj
						+ "] while in Schema[" + attSchema  + ":" + claimsData.get(attSchema) + "]");
			}
			// ======================================
			if (obj instanceof ArrayList) {
				ArrayList<Object> o = (ArrayList<Object>) obj;
				ArrayList<Object> oSchema = (ArrayList) schema.get(attribute);
				manageArray(oSchema, attribute, o);
			} else if (obj instanceof Map) {

				Map<String, Object> claimsData2 = (Map<String, Object>) obj;
				Map<String, Object> schemaData2 = (Map<String, Object>) schema.get(attribute);
				
				if (Objects.isNull(schemaData2)) {
					throw new IOException(
							"Invalid attribute (Schema[" + attSchema + "], Document[" + claimsData2 + "]");
				}
				process(schemaData2, claimsData2);
			} 
		}
		return true;
	}

	private boolean manageArray(ArrayList<Object> oSchema, String key, ArrayList<Object> o) throws IOException {
		for (int i = 0; i < o.size(); i++) {
			if (o.get(i) instanceof ArrayList) {
				manageArray((ArrayList) oSchema.get(i), key, (ArrayList) o.get(i));
			} else if (o.get(i) instanceof Map) {
				Map<String, Object> claimsData2 = (Map<String, Object>) o.get(i);
				Map<String, Object> schemaData2 = (Map<String, Object>) oSchema.get(i);
				process(schemaData2, claimsData2);
			}
		}
		return true;
	}
}