package org.ri.se.selectivedisclosure.vc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.acreo.security.processor.CryptoProcessor;
import org.apache.commons.lang3.RandomStringUtils;
import org.ri.se.selectivedisclosure.ExtendedKeyValue;
import org.ri.se.selectivedisclosure.ExtendedKeyValueList;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class VCExtendedEngine {

	public static final int STRING = 0;
	public static final int NON_STRING = 1;
	public static final String QOUTE = "\"";
	public static final String ARRAY_SEPERATOR_START = "_";
	public static final String ARRAY_SEPERATOR_END = "_";

	public static void main(String[] args) throws Exception {
		
		byte[] dataIn_ = Files.readAllBytes(
				Paths.get("/home/devvm/Desktop/RISE/protects/mvc/microcredentials/backend/org.ri.se.selectivedisclosure/nawankata.json"));
		Map<String, Object> claimsData_ = (Map<String, Object>) new ObjectMapper().readValue(dataIn_, Map.class);
		VCExtendedEngine vcExtendedEngine_ = new VCExtendedEngine(claimsData_);

		Map<String, String> mapper_ = vcExtendedEngine_.getMetaMapper();
		for (Map.Entry<String, String> entry : mapper_.entrySet()) {
			System.out.println(entry.getKey());
		}
		System.out.println("=======================================");
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
		String source = "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/general.txt";
		String sourceSchema = "/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/general.txt";
		List<String> list = new ArrayList<>();
		list.add("/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/health1.txt");
		list.add("/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/health2.txt");
		list.add("/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.verifiablecredentials.usecases/simple.txt");
		list.add("/home/gpvm1/Desktop/RISE/projects/sd/org.ri.se.selectivedisclosure/elm.json");

		{
			//////////// sdfsdfsdfdsf/dsf
			System.out.println("----------------------------");
			byte[] data = Files.readAllBytes(Paths.get(list.get(3)));

			try {

				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_EMPTY);
				Map<String, Object> c = (Map<String, Object>) mapper.readValue(data, Map.class);
				System.out.println("==============================ORGIONAL==============================");
				// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c));
			} catch (Exception exp) {
				exp.printStackTrace();
				throw new IOException("There is an issue to generate a JSON, please contact with System owner !");
			}
			Map<String, Object> claimsData = (Map<String, Object>) new ObjectMapper().readValue(data, Map.class);
			VCExtendedEngine vcExtendedEngine = new VCExtendedEngine(claimsData);

			// System.out.println(digestSet.toString());
			String jsonExt = vcExtendedEngine.getExtendedJSON();
			System.out.println("==============================EXTENDED==============================");
			System.out.println(jsonExt);

			System.out.println("==============================DIGEST==============================");
			Map<String, String> digestSet = vcExtendedEngine.getDigestSet();
			// System.out.println(new
			// ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(digestSet));
			System.out.println("============================== END VC Issuance process ==============================");
		}
	}

	private StringBuffer buffer = null;
	private Map<String, String> digestSet;

	public VCExtendedEngine(Map<String, Object> claimsData) throws Exception {
		String indent = "";
		String mainkeys = "";
		ExtendedKeyValueList extendedKeyValue = new ExtendedKeyValueList();
		digestSet = new HashMap();
		buffer = new StringBuffer();
		buffer.append("{");
		process(claimsData, indent, mainkeys, extendedKeyValue, buffer);
		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());
		try {
			Map<String, Object> c = (Map<String, Object>) new ObjectMapper().readValue(buffer.toString(), Map.class);
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new IOException("There is an issue to generate a JSON, please contact with System owner !");
		}
		digestSet = generateDigestSet(extendedKeyValue);

	}

	public String indentation(String indent, int no) {
		return indent.equals("") ? indent + no : indent + "." + no;
	}

	public String keys(String mainkeys, String addition) {
		String temp = mainkeys.equals("") ? mainkeys + addition : mainkeys + "." + addition;
		return temp;
	}

	public Map<String, String> getDigestSet() {
		return digestSet;
	}

	public Map<String, String> getMetaMapper() {
		return key2digestId;
	}

	private Map<String, String> generateDigestSet(ExtendedKeyValueList extendedKeyValue) throws Exception {
		CryptoProcessor cryptoProcessor = new CryptoProcessor();
		Iterator it = extendedKeyValue.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next().toString();
			String calculatedDigest = new String(
					cryptoProcessor.digest(extendedKeyValue.get(key).serialize().getBytes()));
			digestSet.put(key, calculatedDigest);
		}
		return digestSet;
	}

	public String getExtendedJSON() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		Map<String, Object> c = (Map<String, Object>) mapper.readValue(buffer.toString(), Map.class);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
	}

	private Map<String, String> key2digestId = new HashMap<String, String>();

	public boolean process(Map<String, Object> claimsData, String indent, String mainkeys,
			Map<String, ExtendedKeyValue> extendedKeyValue, StringBuffer buffer) throws IOException {
		// JSONObject json = new JSONObject(claimsData);
		List sortedKeysJson = new ArrayList(claimsData.keySet());

		int no = 0;
		for (int i = 0; i < sortedKeysJson.size(); i++) {
			no++;
			String attribute = (String) sortedKeysJson.get(i);
			if (attribute.equals("@context")) {
				String newContext = "";
				Object context = claimsData.get(attribute);
				List<Object> contexts = (List<Object>) claimsData.get(attribute);
				newContext = newContext + QOUTE + attribute + QOUTE + ": [";
				for (int j = 0; j < contexts.size(); j++) {

					if (contexts.get(j) instanceof LinkedHashMap) {
						LinkedHashMap temp = (LinkedHashMap) contexts.get(j);
						String aContext = new ObjectMapper().writeValueAsString(temp);
						newContext = newContext + QOUTE + aContext + QOUTE + ",";
					} else {

						String aContext = contexts.get(j).toString();
						newContext = newContext + QOUTE + aContext + QOUTE + ",";
					}
				}
				if (newContext.endsWith(","))
					newContext = newContext.trim();
				newContext = newContext.substring(0, newContext.length() - 1);
				System.out.println("");
				newContext = newContext + "],";
				System.out.println(newContext);
				buffer.append(newContext);

			} else {
				Object obj = claimsData.get(attribute);
				if(Objects.isNull(obj )) {
					
				}
				// ======================================
				else if (obj instanceof Date || obj instanceof String) {
					ExtendedKeyValue salted = salted(attribute, obj.toString(), STRING);
					String serialNo = indentation(indent, no);
					extendedKeyValue.put(indentation(indent, no), salted);
					String attSaltedJson = toJSON(indentation(indent, no), salted);
					buffer.append(QOUTE + indentation(indent, no) + QOUTE + ":" + attSaltedJson + ",");

					key2digestId.put(keys(mainkeys, attribute), indentation(indent, no));
				} else if (obj instanceof Integer || obj instanceof Long || obj instanceof Double
						|| obj instanceof Boolean) {
					String serialNo = indentation(indent, no);
					ExtendedKeyValue salted = salted(attribute, obj, NON_STRING);
					extendedKeyValue.put(indentation(indent, no), salted);
					String attSaltedJson = toJSON(indentation(indent, no), salted);
					buffer.append(QOUTE + indentation(indent, no) + QOUTE + ":" + attSaltedJson + ",");
					key2digestId.put(keys(mainkeys, attribute), indentation(indent, no));
				} else if (obj instanceof ArrayList) {
					buffer.append(QOUTE + attribute + QOUTE + ":[");
					ArrayList<Object> o = (ArrayList) obj;
					manageArray(attribute, o, indentation(indent, no), keys(mainkeys, attribute), extendedKeyValue,
							buffer);
				} else if (obj instanceof Map) {
					buffer.append(QOUTE + attribute + QOUTE + ": {");
					Map<String, Object> claimsData2 = (Map<String, Object>) obj;
					process(claimsData2, indentation(indent, no), keys(mainkeys, attribute), extendedKeyValue, buffer);
				} else {
					int test = (Integer) obj;
					return false;
				}
			}
		}

		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());
		buffer.append("},");
		return true;
	}

	public boolean manageArray(String key, ArrayList<Object> o, String indent, String mainkeys,
			Map<String, ExtendedKeyValue> extendedKeyValue, StringBuffer buffer) throws IOException {
		for (int i = 0; i < o.size(); i++) {

			if (o.get(i) instanceof Date || o.get(i) instanceof String) {
				ExtendedKeyValue salted = salted("" + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END,
						o.get(i).toString(), STRING);
				extendedKeyValue.put(indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END, salted);
				String attSaltedJson = toJSON(indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END, salted);
				buffer.append("{" + QOUTE + indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END + QOUTE + ":"
						+ attSaltedJson + "},");

				key2digestId.put(keys(mainkeys, "_" + i + "_"),
						indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END + QOUTE);
			} else if (o.get(i) instanceof Integer || o.get(i) instanceof Long || o.get(i) instanceof Double
					|| o.get(i) instanceof Boolean) {
				ExtendedKeyValue salted = salted("" + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END, o.get(i),
						NON_STRING);
				extendedKeyValue.put(indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END, salted);
				String attSaltedJson = toJSON(indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END, salted);
				buffer.append("{" + QOUTE + indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END + QOUTE + ":"
						+ attSaltedJson + "},");
				key2digestId.put(keys(mainkeys, "_" + i + "_"),
						indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END + QOUTE);
			} else if (o.get(i) instanceof ArrayList) {
				buffer.append(QOUTE + key + QOUTE + " {");
				manageArray(key, (ArrayList) o.get(i), indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END,
						keys(mainkeys, "_" + i + "_"), extendedKeyValue, buffer);
			} else if (o.get(i) instanceof Map) {
				buffer.append("{");
				Map<String, Object> claimsData2 = (Map<String, Object>) o.get(i);
				process(claimsData2, indent + ARRAY_SEPERATOR_START + i + ARRAY_SEPERATOR_END,
						keys(mainkeys, "_" + i + "_"), extendedKeyValue, buffer);
			} else {
				throw new IOException("Problems when processing JSON to create salted objects S!");
			}
		}
		if (buffer.toString().endsWith(","))
			buffer.delete(buffer.toString().length() - 1, buffer.toString().length());
		buffer.append("],");
		return true;
	}

	private String toJSON(String id, ExtendedKeyValue keyValue) throws JsonProcessingException {
		String temp = new ObjectMapper().writeValueAsString(keyValue);
		return temp;
	}

	public ExtendedKeyValue salted(String temp, Object value, int isString) {
		ExtendedKeyValue extendedKeyValue = new ExtendedKeyValue();
		extendedKeyValue.setName(temp);
		extendedKeyValue.setValue(value);
		extendedKeyValue.setSalt(RandomStringUtils.randomAlphanumeric(64));
		return extendedKeyValue;
	}
}
