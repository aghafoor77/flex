package com.rise.vdr.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.EvidenceData;
import com.ri.se.commonentities.transporter.DTOTransferedAnimal;
import com.rise.vdr.api.aggregation.Representation;
import com.rise.vdr.api.aggregation.RestClient;
import com.rise.vdr.api.persistance.SCEvidence;
import com.rise.vdr.api.persistance.SCEvidenceService;
import com.rise.vdr.api.persistance.SCSecretKeyService;
import com.rise.vdr.api.persistance.SCTransaction;
import com.rise.vdr.api.persistance.SCTransactionService;

public class AnimalTraceability {

	private SCTransactionService scTransactionService;
	private SCEvidenceService scevidenceService;
	private SCSecretKeyService scsecretkeyService;

	public AnimalTraceability(SCTransactionService scTransactionService, SCEvidenceService scevidenceService,
			SCSecretKeyService scsecretkeyService) {
		this.scTransactionService = scTransactionService;
		this.scevidenceService = scevidenceService;
		this.scsecretkeyService = scsecretkeyService;

	}

	public void trace(String animalID, ArrayList<String> animals) {
		List<SCTransaction> trans = scTransactionService.fetchFirstTrasaction(animalID);
		if (Objects.isNull(trans) || trans.size() == 0) {
			// End of chain
			return;
		}

		List<SCEvidence> evdData = scevidenceService.getByTransactionIDData(trans.get(0).getTransactionID());
		if (Objects.isNull(evdData) || evdData.size() == 0) {
			// End
			return;
		}
		String resource = "/v1/scevidence/download/evidence/" + evdData.get(0).getLink();

		String jsonValues = downloader("http://localhost", 9030, resource);
		try {
			EvidenceData eData = new ObjectMapper().readValue(jsonValues, EvidenceData.class);
			System.out.println(eData.getContents());
			ObjectMapper mapper = new ObjectMapper();
			DTOTransferedAnimal ta = new ObjectMapper().readValue(mapper.writeValueAsString(eData.getContents()),
					DTOTransferedAnimal.class);
			System.out.println(ta.getAnimalIDMother());
			System.out.println(Objects.isNull(ta.getAnimalIDMother()));
			System.out.println(ta.getAnimalIDMother()=="null");
			if(!(Objects.isNull(ta.getAnimalIDMother()) || ta.getAnimalIDMother()=="null")){
				animals.add(animalID);
				return;
			}
			animals.add(animalID);
			trace(ta.getAnimalIDMother(), animals);
		} catch (Exception exp) {
			return;
		}

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
