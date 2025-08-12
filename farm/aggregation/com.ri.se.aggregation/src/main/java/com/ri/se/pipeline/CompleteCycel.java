package com.ri.se.pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.aggregation.Representation;
import com.ri.se.aggregation.RestClient;

public class CompleteCycel {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		// Fetch Transporter
		// VDR /application/v1/scresource/vdr/role/TRANSPORTER
		String ip = "http://localhost";
		int vdrport = 9030;
		int animalRegport = 9002;
		String resource = "/v1/scresource/vdr/role/TRANSPORTER";
		String trasnpoterString = downloader(ip, vdrport, resource);

		ObjectMapper om = new ObjectMapper();

		List<Map<String, String>> tr = (List<Map<String, String>>) om.readValue(trasnpoterString.getBytes(),
				List.class);
		System.out.println(tr.get(0).get("did"));

		resource = "/v1/scresource/vdr/role/SALUGHTERHOUSEOWNER";
		String slaughterString = downloader(ip, vdrport, resource);
		List<Map<String, String>> sh = (List<Map<String, String>>) om.readValue(slaughterString.getBytes(), List.class);
		System.out.println(sh.get(0).get("did"));

		resource = "/v1/scresource/vdr/role/OWNER";
		String ownerString = downloader(ip, vdrport, resource);
		List<Map<String, String>> owner = (List<Map<String, String>>) om.readValue(ownerString.getBytes(), List.class);
		System.out.println(owner.get(0).get("did"));

		// Fetch Animals
		// RegAnimal/v1/registeranimal/totransferlist
		resource = "/v1/registeranimal/totransferlist";
		String animalsString = downloader(ip, animalRegport, resource);
		System.out.println(animalsString);
		// VDR/v1/sctransaction/fetch/records/{animalID}
		List<Map<String, String>> ar = (List<Map<String, String>>) om.readValue(animalsString.getBytes(), List.class);
		for (int j = 0; j < ar.size(); j++) {
			System.out.println(ar.get(j).get("animalID"));

			resource = "/v1/sctransaction/fetch/records/" + ar.get(j).get("animalID");
			String temp = downloader(ip, vdrport, resource);

			List<Map<String, String>> ttt = (List<Map<String, String>>) om.readValue(temp.getBytes(), List.class);
			Map<String, String> tosend = new HashMap<String, String>();
			for (int i = 0; i < ttt.size(); i++) {
				Map<String, String> ttttt = ttt.get(i);
				System.out.println(ttttt.get("uniqueNo"));
				tosend.put(ttttt.get("uniqueNo"), "Transporter");
			}
			// POST:
			// VDR/application/v1/sctransaction/submitwithal
			String transporter = tr.get(0).get("did");
			String slaughterhouse = sh.get(0).get("did");
			String from = owner.get(0).get("did");
			AccessLevelEntity ale = new AccessLevelEntity();
			ale.setFrom(from);
			ale.setSlaughterhouse(slaughterhouse);
			ale.setTransporter(transporter);
			Map<String, Object> animals = new HashMap<String, Object>();
			animals.put(ar.get(j).get("animalID"), tosend);
			ale.setAnimals(animals);
			commit(ip, vdrport, "/v1/sctransaction/submitwithal", ale);
		}
	}

	public static void execute(String animalID ) throws Exception {

		// Fetch Transporter
		// VDR /application/v1/scresource/vdr/role/TRANSPORTER
		String ip = "http://localhost";
		int vdrport = 9030;
		int animalRegport = 9002;
		String resource = "/v1/scresource/vdr/role/TRANSPORTER";
		String trasnpoterString = downloader(ip, vdrport, resource);

		ObjectMapper om = new ObjectMapper();

		List<Map<String, String>> tr = (List<Map<String, String>>) om.readValue(trasnpoterString.getBytes(),
				List.class);
		System.out.println(tr.get(0).get("did"));

		resource = "/v1/scresource/vdr/role/SALUGHTERHOUSEOWNER";
		String slaughterString = downloader(ip, vdrport, resource);
		List<Map<String, String>> sh = (List<Map<String, String>>) om.readValue(slaughterString.getBytes(), List.class);
		System.out.println(sh.get(0).get("did"));

		resource = "/v1/scresource/vdr/role/OWNER";
		String ownerString = downloader(ip, vdrport, resource);
		List<Map<String, String>> owner = (List<Map<String, String>>) om.readValue(ownerString.getBytes(), List.class);
		System.out.println(owner.get(0).get("did"));

		// Fetch Animals
		// RegAnimal/v1/registeranimal/totransferlist
		//resource = "/v1/registeranimal/totransferlist";
		//String animalsString = downloader(ip, animalRegport, resource);
		//System.out.println(animalsString);
		// VDR/v1/sctransaction/fetch/records/{animalID}
		//List<Map<String, String>> ar = (List<Map<String, String>>) om.readValue(animalsString.getBytes(), List.class);
		for (int j = 0; j < 1; j++) {
			//System.out.println(ar.get(j).get("animalID"));
			
			//resource = "/v1/sctransaction/fetch/records/" + ar.get(j).get("animalID");
			resource = "/v1/sctransaction/fetch/records/" + animalID;
			
			String temp = downloader(ip, vdrport, resource);

			List<Map<String, String>> ttt = (List<Map<String, String>>) om.readValue(temp.getBytes(), List.class);
			Map<String, String> tosend = new HashMap<String, String>();
			for (int i = 0; i < ttt.size(); i++) {
				Map<String, String> ttttt = ttt.get(i);
				System.out.println(ttttt.get("uniqueNo"));
				tosend.put(ttttt.get("uniqueNo"), "Transporter");
			}
			// POST:
			// VDR/application/v1/sctransaction/submitwithal
			String transporter = tr.get(0).get("did");
			String slaughterhouse = sh.get(0).get("did");
			String from = owner.get(0).get("did");
			AccessLevelEntity ale = new AccessLevelEntity();
			ale.setFrom(from);
			ale.setSlaughterhouse(slaughterhouse);
			ale.setTransporter(transporter);
			Map<String, Object> animals = new HashMap<String, Object>();
			//animals.put(ar.get(j).get("animalID"), tosend);
			
			animals.put(animalID, tosend);
			ale.setAnimals(animals);
			commit(ip, vdrport, "/v1/sctransaction/submitwithal", ale);
		}

	}

	public static String downloader(String ip, int port, String resource) {
		try {
			RestClient rc = RestClient.builder().baseUrl(ip).build();
			Representation rep = rc.get(":" + port + "/application" + resource, null);
			System.out.println(rep.getBody().toString());
			return rep.getBody().toString();
		} catch (Exception exp) {
			System.out.println("==========> No Data <==========" + exp.getMessage());
			return null;
		}
	}

	public static String commit(String ip, int port, String resource, Object data) {
		try {
			System.out.println(data);
			RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
			Representation rep = rc.post("/application" + resource, data, null);
			System.out.println(rep.getCode());
			System.out.println(rep.getBody().toString());
			Scanner scanner = new Scanner(System.in);

			// Message to the user
			System.out.println("Press Enter to continue...");

			// Wait for the user to press Enter
			// scanner.nextLine();
			return rep.getBody().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);

		// Message to the user
		System.out.println("Press Enter to continue...");

		// Wait for the user to press Enter
		// scanner.nextLine();
		return null;
	}

}
