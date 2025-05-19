package com.rise.se.slaughterhouse.downloader;

import java.util.Base64;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.EvidenceData;
import com.ri.se.commonentities.SCEvidenceList;
import com.ri.se.commonentities.SCTransaction;
import com.ri.se.commonentities.SCTransactionList;
import com.ri.se.commonentities.transporter.TransportedToSlaughterList;
import com.rise.se.slaughterhouse.persistance.RAnimalSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RAnimalSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.RCarrierSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RCarrierSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.RSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.SlaughterhouseService;

public class DownloadVDRData {

	private RSlaughterhouseDataService rslaughterhousedataService;
	private RCarrierSlaughterhouseDataService rcarrierslaughterhousedataService;
	private RAnimalSlaughterhouseDataService ranimalslaughterhousedataService;

	public DownloadVDRData(RSlaughterhouseDataService rslaughterhousedataService,
			RCarrierSlaughterhouseDataService rcarrierslaughterhousedataService,
			RAnimalSlaughterhouseDataService ranimalslaughterhousedataService) {
		this.rslaughterhousedataService = rslaughterhousedataService;
		this.rcarrierslaughterhousedataService = rcarrierslaughterhousedataService;
		this.ranimalslaughterhousedataService = ranimalslaughterhousedataService;
	}

	public boolean downloadFromVDRData(String ip, int port, String resource) throws Exception {
		String jsonStr = fetchRecord(ip, port, resource);
		SCTransactionList scTransactionList = new ObjectMapper().readValue(jsonStr, SCTransactionList.class);
		System.out.println("========================== Transaction Size = " + scTransactionList.size());
		for (SCTransaction sct : scTransactionList) {
			System.out.println(scTransactionList.size());
			resource = "/v1/scevidence/fetchby/transaction/data/" + sct.getTransactionID();
			jsonStr = fetchRecord(ip, port, resource);
			SCEvidenceList scEvidenceList = new ObjectMapper().readValue(jsonStr, SCEvidenceList.class);
			System.out.println(scEvidenceList.size());
			System.out.println(scEvidenceList.size());
			if (scEvidenceList.size() > 0) {
				System.out.println(scEvidenceList.get(0).getLink());

				resource = "/v1/scevidence/download/evidence/slaughter/" + scEvidenceList.get(0).getLink();
				System.out.println(downloader(ip, port, resource));
				String jsonValues = downloader(ip, port, resource);
				System.out.println("************************************************ " + jsonValues);
				EvidenceData eData = new ObjectMapper().readValue(jsonValues, EvidenceData.class);
				System.out.println(eData.getContents());
				ObjectMapper mapper = new ObjectMapper();
				byte[] aactualContents = Base64.getDecoder().decode(eData.getContents().toString().getBytes());
				T2SParser t2sParser = new T2SParser();
				RSlaughterhouseData shd = t2sParser.parserRSlaughterhouseData(eData);
				RCarrierSlaughterhouseData cshd = t2sParser.parserRCarrierSlaughterhouseData(eData);
				RAnimalSlaughterhouseData ta = t2sParser.parserRAnimalSlaughterhouseData(eData);
				ta.setTransactionID(sct.getTransactionID());
				ta.setTransactionDate(sct.getTransactiondDate());
				ta.setUpdatedOn(System.currentTimeMillis());
				ta.setTripNo(cshd.getTripNo());
				try {
					if (Objects.isNull(rcarrierslaughterhousedataService.get(cshd.getCarrierId()))) {
						rcarrierslaughterhousedataService.post(cshd);
					}
				} catch (Exception exp) {
					rcarrierslaughterhousedataService.post(cshd);
				}
				try {
					if (Objects.isNull(rslaughterhousedataService.get(shd.getTripNo()))) {
						rslaughterhousedataService.post(shd);
					}
				} catch (Exception exp) {
					rslaughterhousedataService.post(shd);
				}
				try {
					if (Objects.isNull(ranimalslaughterhousedataService.get(ta.getAnimalID()))) {
						ranimalslaughterhousedataService.post(ta);
					}
				} catch (Exception exp) {
					ranimalslaughterhousedataService.post(ta);
				}

			}

		}
		return true;

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
