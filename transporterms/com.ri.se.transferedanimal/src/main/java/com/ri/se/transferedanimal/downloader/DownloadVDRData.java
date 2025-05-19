package com.ri.se.transferedanimal.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.EvidenceData;
import com.ri.se.commonentities.SCEvidenceList;
import com.ri.se.commonentities.SCTransaction;
import com.ri.se.commonentities.SCTransactionList;
import com.ri.se.commonentities.transporter.DTOTransferedAnimal;
import com.ri.se.commonentities.transporter.DTOTransferedAnimalList;

public class DownloadVDRData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			String ip = "http://localhost";
			int port = 9030;

			String resource = "/v1/sctransaction//updated/byreceiver/1736992621929/6a77646c68597a6361684d3b6d2a7e576b557665504342534e4959377447";
			String jsonStr = new DownloadVDRData().fetchRecord(ip, port, resource);
			SCTransactionList scTransactionList = new ObjectMapper().readValue(jsonStr, SCTransactionList.class);
			System.out.println(scTransactionList.size());
			resource = "/v1/scevidence/fetchby/transaction/data/" + scTransactionList.get(1).getTransactionID();
			jsonStr = new DownloadVDRData().fetchRecord(ip, port, resource);
			SCEvidenceList scEvidenceList = new ObjectMapper().readValue(jsonStr, SCEvidenceList.class);
			System.out.println(scEvidenceList.size());
			if (scEvidenceList.size() < 0) {
				System.out.println(scEvidenceList.get(0).getLink());
			} else {

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DTOTransferedAnimalList downloadFromVDRData(String ip, int port, String resource) throws Exception {

		DTOTransferedAnimalList animalList = new DTOTransferedAnimalList();
		String jsonStr = new DownloadVDRData().fetchRecord(ip, port, resource);
		SCTransactionList scTransactionList = new ObjectMapper().readValue(jsonStr, SCTransactionList.class);
		for (SCTransaction sct : scTransactionList) {
			System.out.println(scTransactionList.size());
			resource = "/v1/scevidence/fetchby/transaction/data/" + sct.getTransactionID();
			jsonStr = new DownloadVDRData().fetchRecord(ip, port, resource);
			SCEvidenceList scEvidenceList = new ObjectMapper().readValue(jsonStr, SCEvidenceList.class);
			System.out.println(scEvidenceList.size());
			System.out.println(scEvidenceList.size());
			if (scEvidenceList.size() > 0) {

				resource = "/v1/scevidence/download/evidence/" + scEvidenceList.get(0).getLink();
				System.out.println(downloader(ip, port, resource));
				String jsonValues = downloader(ip, port, resource);

				EvidenceData eData = new ObjectMapper().readValue(jsonValues, EvidenceData.class);
				System.out.println(eData.getContents());
				ObjectMapper mapper = new ObjectMapper();
				DTOTransferedAnimal ta = new ObjectMapper().readValue(mapper.writeValueAsString(eData.getContents()),
						DTOTransferedAnimal.class);
				ta.setTransactionID(sct.getTransactionID());
				ta.setTransactionTime(sct.getTransactiondDate());
				ta.setCurrentStatus("RECEIVED");
				animalList.add(ta);
			}
		}
		return animalList;

	}

	public String fetchRecord(String ip, int port, String resource) throws Exception {
		return downloader(ip, port, resource);

	}

	public String downloader(String ip, int port, String resource) {
		try {
			RestClient rc = RestClient.builder().baseUrl(ip).build();
			Representation rep = rc.get(":" + port + "/application" + resource, null);
			System.out.println(rep.getBody().toString());
			if (rep.getCode() != 200) {
				return null;
			}
			return rep.getBody().toString();
		} catch (Exception exp) {
			System.out.println("==========> No Data <==========" + exp.getMessage());
			return null;
		}
	}
}
