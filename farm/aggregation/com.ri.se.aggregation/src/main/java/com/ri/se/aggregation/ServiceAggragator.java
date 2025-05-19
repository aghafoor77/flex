package com.ri.se.aggregation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.DTOAnimalExaminationList;
import com.ri.se.commonentities.DTOGeneralHealthExaminationList;
import com.ri.se.commonentities.DTORegisterAnimal;

public class ServiceAggragator {

	private final String endpointTag = "/application/v1/";

	public String downloader(String serviceID, String address, String identity) throws Exception {
		RestClient rc = RestClient.builder().baseUrl(address).build();
		Representation rep = rc.get("/application/v1/" + serviceID + "/animal/" + identity, null);
		return rep.getBody().toString();
	}

	public String collect(String serviceID, String address, String identity) throws Exception {

		String data = downloader(serviceID, address, identity);
		System.out.println(data);
		switch (serviceID) {
		case "animalderegister":

			break;
		case "animalexamination": {
			System.out.println(data);
			DTOAnimalExaminationList list = new ObjectMapper().readValue(data, DTOAnimalExaminationList.class);
			System.out.println(list.size());
			break;
		}
		case "animalreg":
			DTORegisterAnimal animal= new ObjectMapper().readValue(data,
					DTORegisterAnimal.class);
			System.out.println(animal.getAnimalID());
			break;
		case "centralhostregistration":
			break;
		case "feedpattern":
			break;
		case "generaldata":
			break;
		case "generalhealthexamination": {
			DTOGeneralHealthExaminationList list = new ObjectMapper().readValue(data,
					DTOGeneralHealthExaminationList.class);
			System.out.println(list.size());
			break;
		}
		case "movebullforherd":
			break;
		case "ordersemen":
			break;
		case "reportslaughterhouse":
			break;
		case "semenutilization":
			break;
		case "temporarymovement":
			break;
		case "drugstreatments":
			break;
		default:
			System.out.println("no patch ");
		}
		return null;
	}

}
