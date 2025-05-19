package com.ri.se.aggregation;

import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.DTOAnimalExamination;
import com.ri.se.commonentities.DTOAssignFeed;
import com.ri.se.commonentities.DTOAssignFeedList;
import com.ri.se.commonentities.DTODrugsTreatments;
import com.ri.se.commonentities.DTOGeneralHealthExamination;
import com.ri.se.commonentities.DTOGeneralHealthObservation;
import com.ri.se.commonentities.DTOOrderSemen;
import com.ri.se.commonentities.DTORegisterAnimal;
import com.ri.se.commonentities.DTOResponseOrderSemen;
import com.ri.se.commonentities.DTOSemenUtilization;
import com.ri.se.commonentities.DTOTemporaryMovement;
import com.ri.se.commonentities.DTOTemporaryMovementGroup;
import com.ri.se.commonentities.DTOTemporaryMovementGroupList;

public class DownloadAggregatedData {

	public static String ip = "http://localhost";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			fetchRecord("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void fetchRecord(String employeeID) throws Exception {
		String osid = "osid-00-11-22";
		// String employeeID = "HR-00-11-22";
		String farmID = "FR-11-22-66";
		String suid1 = "SU-11-22-11";
		String suid2 = "SU-11-22-12";

		String animalIDMother1 = "AR-34-567-37";
		String animalIDMother2 = "AR-34-567-38";

		DTOOrderSemen dtoOrderSemen = new DTOOrderSemen();
		int port = 9007;
		String resource = "/v1/ordersemen/" + animalIDMother1;
		// downloader(ip, port, resource);
		// dtoOrderSemen

		DTOResponseOrderSemen dtoResponseOrderSemen = new DTOResponseOrderSemen();
		port = 9007;
		resource = "/v1/responseordersemen/" + animalIDMother1;
		// downloader(ip, port, resource);
		// dtoResponseOrderSemen

		DTOSemenUtilization dtoSemenUtilization1 = new DTOSemenUtilization();
		port = 9007;
		resource = "/v1/semenutilization/animal/AR-34-567-37";// + animalIDMother1;
		String jsonStr = downloader(ip, port, resource);
		if (!Objects.isNull(jsonStr)) {
			DTOSemenUtilization obj = new ObjectMapper().readValue(jsonStr, DTOSemenUtilization.class);
			System.out.println("=======================");
		}
		// dtoSemenUtilization1

		DTOAnimalExamination dtoAnimalExaminationMother10 = new DTOAnimalExamination();
		port = 9011;
		resource = "/v1/animalexamination/animal/" + animalIDMother1;
		downloader(ip, port, resource);
		// dtoAnimalExaminationMother10

		// dtoAnimalExaminationMother11

		DTORegisterAnimal dtoRegisterAnimal1 = new DTORegisterAnimal();
		port = 9002;
		resource = "/v1/registeranimal/" + animalIDMother1;
		downloader(ip, port, resource);
		// dtoRegisterAnimal1

		// May be good for animal health

		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB10 = new DTOGeneralHealthExamination();
		port = 9011;
		resource = "/v1/generalhealthexamination/animal/" + animalIDMother1;
		downloader(ip, port, resource);
		// dtoGeneralHealthExaminationNB10
		//////////////////////////

		String obsID = "HR-73-982-29/" + animalIDMother1;
		DTOGeneralHealthObservation dtoGeneralHealthObservation10 = new DTOGeneralHealthObservation();
		port = 9011;
		resource = "/v1/generalhealthobservation/animal/" + animalIDMother1;
		downloader(ip, port, resource);
		// dtoGeneralHealthObservation10

		// Check this again
		/*
		 * DTOTemporaryMovement dtoTemporaryMovement1 = new DTOTemporaryMovement(); port
		 * = 9013; resource = "/v1/temporarymovement/"+animalIDMother1; downloader(ip,
		 * port, resource);
		 */// dtoTemporaryMovement1

		/*
		 * DTOTemporaryMovement dtoTemporaryMovement2 = new DTOTemporaryMovement(); port
		 * = 9013; resource = "/v1/temporarymovement/"+animalIDMother1; downloader(ip,
		 * port, resource);
		 */
		// dtoTemporaryMovement2
		{
			DTOTemporaryMovement dtoTemporaryMovementGroup3 = new DTOTemporaryMovement();
			port = 9013;
			resource = "/v1/temporarymovementgroup/animal/AR-33-567-40";
			String tmJSON = downloader(ip, port, resource);
			if (!Objects.isNull(tmJSON)) {
				// COnvert o list group and get tm against each entry
				DTOTemporaryMovementGroupList dtoTemporaryMovementGroupList = new ObjectMapper().readValue(tmJSON,
						DTOTemporaryMovementGroupList.class);
				for (DTOTemporaryMovementGroup dtg : dtoTemporaryMovementGroupList) {
					port = 9013;
					resource = "/v1/temporarymovement/" + dtg.getTmid();
					downloader(ip, port, resource);
				}
				// dtoTemporaryMovement3
			}
		}
		DTODrugsTreatments dtoDrugsTreatments = new DTODrugsTreatments();
		port = 9011;
		resource = "/v1/drugstreatments/animal/AR-33-567-40";
		downloader(ip, port, resource);
		// dtoDrugsTreatments

		{

			DTOAssignFeed dtoAssignFeed1 = new DTOAssignFeed();
			port = 9013;
			resource = "/v1/assignfeed/animal/AR-33-567-40";// +animalIDMother1;
			String tmJSON = downloader(ip, port, resource);
			// dtoAssignFeed1
			if (!Objects.isNull(tmJSON)) {
				// COnvert o list group and get tm against each entry
				DTOAssignFeedList dtoAssignFeedList = new ObjectMapper().readValue(tmJSON, DTOAssignFeedList.class);
				for (DTOAssignFeed af : dtoAssignFeedList) {
					port = 9013;
					resource = "/v1/feedpattern/" + af.getFpid();
					downloader(ip, port, resource);
				}
				// dtoTemporaryMovement3
			}
			// dtoFeedPattern
		}
		// dtoAssignFeed2
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
}
