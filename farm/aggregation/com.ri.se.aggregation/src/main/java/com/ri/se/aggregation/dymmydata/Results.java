package com.ri.se.aggregation.dymmydata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.crypto.tink.subtle.Base64;
import com.ri.se.aggregation.Representation;
import com.ri.se.aggregation.RestClient;
import com.ri.se.aggregation.acctmanegement.AccountHandling;
import com.ri.se.commonentities.DTOAnimalExamination;
import com.ri.se.commonentities.DTOAssignFeed;
import com.ri.se.commonentities.DTODrugsTreatments;
import com.ri.se.commonentities.DTOFeedPattern;
import com.ri.se.commonentities.DTOGeneralHealthExamination;
import com.ri.se.commonentities.DTOGeneralHealthObservation;
import com.ri.se.commonentities.DTOOrderSemen;
import com.ri.se.commonentities.DTORegisterAnimal;
import com.ri.se.commonentities.DTOResponseOrderSemen;
import com.ri.se.commonentities.DTOSemenUtilization;
import com.ri.se.commonentities.DTOTemporaryMovement;
import com.ri.se.commonentities.DTOUsers;
import com.ri.se.commonentities.StringList;

public class Results {

	public static String ip = "http://localhost";
	public static String owner = "";

	public static void main(String[] args) throws Exception {

		int port = 9000;

		String resource = "/tapi/idms/v1/auth";
		DTOUsers userDTO = new AccountHandling().authenticate(ip, port, resource,
				"basic " + Base64.encode("farmowner@gmail.com:abdul1234".getBytes()));
		if (Objects.isNull(userDTO)) {

		} else {
			System.out.println(userDTO.getVeid());
		}
		submitRecords(userDTO.getVeid(), 9);
	}

	public static String execRecordInsertion(int evd) throws Exception {

		int port = 9000;

		String resource = "/tapi/idms/v1/auth";
		DTOUsers userDTO = new AccountHandling().authenticate(ip, port, resource,
				"basic " + Base64.encode("farmowner@gmail.com:abdul1234".getBytes()));
		if (Objects.isNull(userDTO)) {

		} else {
			System.out.println(userDTO.getVeid());
		}
		return submitRecords(userDTO.getVeid(),evd);

	}

	public String updateAnimalStatus(String ip, int port, String resource) {
		try {
			RestClient rc = RestClient.builder().baseUrl(ip).build();
			Representation rep = rc.put(":" + port + "/application" + resource, "TRANSFERED", null);
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

	public static String submitRecords(String employeeID, int totlaEvidence) throws Exception {

		String animalIDMother1 = "AR-" + (System.currentTimeMillis() - 100);
		// Register mothers

		DTORegisterAnimal dtoRegisterAnimalM11 = new DTORegisterAnimal();
		dtoRegisterAnimalM11.setAboutAnimal("Transfered from another farm!");
		dtoRegisterAnimalM11.setAnimalID(animalIDMother1);
		dtoRegisterAnimalM11.setAnimalIDMother("");
		dtoRegisterAnimalM11.setBirthPlace("Uppsala Farm");
		dtoRegisterAnimalM11.setBreed("German Red Pied");
		dtoRegisterAnimalM11.setDateOfBirth("2019-08-10");
		dtoRegisterAnimalM11.setEmployerID(employeeID);
		dtoRegisterAnimalM11.setNotes("Natural birth");
		dtoRegisterAnimalM11.setOthers(null);
		// dtoRegisterAnimalM11.setPregnancyExamination(dtoAnimalExaminationMother11.getAeid());
		dtoRegisterAnimalM11.setPreviousAnimalID("AR-12-12-12IMP");
		dtoRegisterAnimalM11.setReceivedFarmID("F-23-23-23");
		dtoRegisterAnimalM11.setReceivedFarmName("Sundsval");
		dtoRegisterAnimalM11.setSex("Cow");
		dtoRegisterAnimalM11.setStatus("Active");
		dtoRegisterAnimalM11.setUnit("kg");
		dtoRegisterAnimalM11.setWeight(150);

		DTORegisterAnimal dtoRegisterAnimalRep1 = null;
		int port = 9002;
		String resource = "/v1/registeranimal";
		String str = commit(ip, port, resource, dtoRegisterAnimalM11);
		if (!Objects.isNull(str)) {
			dtoRegisterAnimalRep1 = new ObjectMapper().readValue(str, DTORegisterAnimal.class);
			animalIDMother1 = dtoRegisterAnimalRep1.getAnimalID();
		}
	
		Random rand = new Random();
		for (int i = 0; i < totlaEvidence; i++) {
			int random = rand.nextInt(6);
			if (random % 6 == 0) {
				// First Evidence
				DTOAnimalExamination dtoAnimalExaminationMother10 = new DTOAnimalExamination();
				dtoAnimalExaminationMother10.setAeid("AEID-3" + System.currentTimeMillis());
				dtoAnimalExaminationMother10.setAnimalID(animalIDMother1);
				dtoAnimalExaminationMother10.setEmployeeID(employeeID);
				dtoAnimalExaminationMother10.setExaminationDate("2020-08-30");
				dtoAnimalExaminationMother10.setExtepctedDate(null);
				dtoAnimalExaminationMother10.setNotes("This is early examination through pregnancy test.");
				dtoAnimalExaminationMother10.setRefnumber("exteranl ocument");
				dtoAnimalExaminationMother10.setRefType("medicine");
				dtoAnimalExaminationMother10.setSensorData("sensordataid");
				dtoAnimalExaminationMother10.setStatus("false");
				port = 9011;
				resource = "/v1/animalexamination";
				commit(ip, port, resource, dtoAnimalExaminationMother10);
			}
			if (random % 6 == 1) {
				// May be good for animal health
				String vetID = "HR-29-276-761";
				DTOGeneralHealthExamination dtoGeneralHealthExaminationNB10 = new DTOGeneralHealthExamination();
				dtoGeneralHealthExaminationNB10.setAnimalID(animalIDMother1);
				dtoGeneralHealthExaminationNB10.setGaheid("GHE" + "7" + System.currentTimeMillis());
				dtoGeneralHealthExaminationNB10.setGheDate("2021-08-30");
				dtoGeneralHealthExaminationNB10.setInfections("None");
				dtoGeneralHealthExaminationNB10.setLameness("None");
				dtoGeneralHealthExaminationNB10.setNotation("None");
				dtoGeneralHealthExaminationNB10.setObserver(vetID);
				dtoGeneralHealthExaminationNB10.setSwelling("on leg");
				dtoGeneralHealthExaminationNB10.setTemperature("97");
				dtoGeneralHealthExaminationNB10.setNotes("Overall is ok ");
				dtoGeneralHealthExaminationNB10.setWeak("No");
				dtoGeneralHealthExaminationNB10.setWound("No");
				port = 9011;
				resource = "/v1/generalhealthexamination";
				commit(ip, port, resource, dtoGeneralHealthExaminationNB10);
			}
			//////////////////////////
			if (random % 6 == 2) {
				String obsID = "HR-73-982-291";
				DTOGeneralHealthObservation dtoGeneralHealthObservation10 = new DTOGeneralHealthObservation();
				dtoGeneralHealthObservation10.setAnimalID(animalIDMother1);
				dtoGeneralHealthObservation10.setBcs("No");
				dtoGeneralHealthObservation10.setGheDate("2021-08-15");
				dtoGeneralHealthObservation10.setGhoid("GHO" + "16" + System.currentTimeMillis());
				dtoGeneralHealthObservation10.setLimping("No");
				dtoGeneralHealthObservation10.setNotes("General observation is OK");
				dtoGeneralHealthObservation10.setObserver(obsID);
				dtoGeneralHealthObservation10.setSwelling("No");
				dtoGeneralHealthObservation10.setWeight("13");
				dtoGeneralHealthObservation10.setWound("No");
				port = 9011;
				resource = "/v1/generalhealthobservation";
				commit(ip, port, resource, dtoGeneralHealthObservation10);
			}
			if (random % 6 == 3) {
				DTODrugsTreatments dtoDrugsTreatments2 = new DTODrugsTreatments();
				dtoDrugsTreatments2.setAnimalID(animalIDMother1);
				dtoDrugsTreatments2.setDtid("DT" + "27" + System.currentTimeMillis());
				dtoDrugsTreatments2.setAssignedDate("2021-09-19");
				dtoDrugsTreatments2.setDrungs("Saccharomyces Boulardii Probiotic Powder");
				dtoDrugsTreatments2.setReason("Feeling tempa and coughing");
				dtoDrugsTreatments2.setExaminedBy("HR-343-567-983");
				dtoDrugsTreatments2.setReftype("Medical");
				dtoDrugsTreatments2.setEndDate("2025-01-24");
				dtoDrugsTreatments2.setQuarantinePeriod(10);
				dtoDrugsTreatments2.setIsAntibiotic("Yes");
				// dtoDrugsTreatments2.setRefnumber(dtoGeneralHealthExaminationNB10.getGaheid());
				port = 9011;
				resource = "/v1/drugstreatments";
				commit(ip, port, resource, dtoDrugsTreatments2);
			}
			if (random % 6 == 4) {
				DTOFeedPattern dtoFeedPattern = new DTOFeedPattern();
				dtoFeedPattern.setCertiOfIngredients("");
				dtoFeedPattern.setCreationDate("2021-09-11");
				dtoFeedPattern.setFeedName("GreenLeaves");
				dtoFeedPattern.setFeedType("concentrates");
				dtoFeedPattern.setFoodSource("corn, rice, wheat, soybean, canola, sugarcane");
				dtoFeedPattern.setFpid("FP" + "28" + System.currentTimeMillis());
				dtoFeedPattern.setNotes("");
				dtoFeedPattern.setPercentage("100");
				dtoFeedPattern.setPrice("1000");
				port = 9013;
				resource = "/v1/feedpattern";
				String strJSON = commit(ip, port, resource, dtoFeedPattern);
				if (!Objects.isNull(strJSON)) {
					DTOFeedPattern fp = new ObjectMapper().readValue(strJSON, DTOFeedPattern.class);
					DTOAssignFeed dtoAssignFeed1 = new DTOAssignFeed();
					dtoAssignFeed1.setAfid("AF" + "29" + System.currentTimeMillis());
					dtoAssignFeed1.setAssignedBy(employeeID);
					dtoAssignFeed1.setAssignedDate("2021-09-11");
					dtoAssignFeed1.setAssignedTo(animalIDMother1);
					dtoAssignFeed1.setFpid(fp.getFpid());
					port = 9013;
					resource = "/v1/assignfeed";
					commit(ip, port, resource, dtoAssignFeed1);
				}
			}
			if (random % 6 == 5) {

				DTOTemporaryMovement dtoTemporaryMovement1 = new DTOTemporaryMovement();
				StringList animals = new StringList();
				animals.getData().add(animalIDMother1);
				dtoTemporaryMovement1.setAnimals(animals);
				dtoTemporaryMovement1.setEmployeeID(employeeID);
				dtoTemporaryMovement1.setInDate("2021-10-17");
				dtoTemporaryMovement1.setNotes("No");
				dtoTemporaryMovement1.setOutDate("2021-10-13");
				dtoTemporaryMovement1.setPurpose("Grazing");
				dtoTemporaryMovement1.setTmid("TM" + "24" + System.currentTimeMillis());
				dtoTemporaryMovement1.setTmType("General");
				port = 9013;
				resource = "/v1/temporarymovement";
				commit(ip, port, resource, dtoTemporaryMovement1);
			}
		}
		return animalIDMother1;
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

	// ========================================================
	//// For results
	public static String fetchData() {
		String employeeID = "HR-00-11-22";
		String farmID = "FR-11-22-66";

		DTOOrderSemen dtoOrderSemen = new DTOOrderSemen();
		dtoOrderSemen.setBreed("Svensk Fjällras");
		dtoOrderSemen.setContact("+46727894934");
		dtoOrderSemen.setEmailto("genetics@gen.se");

		dtoOrderSemen.setEmployeeID(employeeID);
		dtoOrderSemen.setFarmID(farmID);
		dtoOrderSemen.setNotes("Ordered for traditional Swedish breed of polled mountain cattle.");
		dtoOrderSemen.setOrderDate("2020/05/23");
		dtoOrderSemen.setOrderedTo("Peter Anderson");
		dtoOrderSemen.setOsid("os-1001");
		try {
			return new ObjectMapper().writeValueAsString(dtoOrderSemen);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static DTORegisterAnimal getAnimal(String employeeID, String animalIDMother, String breed,
			String motherExaminationId) {
		String newBornAnimalID1 = "AR-33-567-40";
		DTORegisterAnimal dtoRegisterAnimal1 = new DTORegisterAnimal();
		dtoRegisterAnimal1.setAboutAnimal("This is newly born animal");
		dtoRegisterAnimal1.setAnimalID(newBornAnimalID1);
		dtoRegisterAnimal1.setAnimalIDMother(animalIDMother);
		dtoRegisterAnimal1.setBirthPlace("Uppsala Farm");
		dtoRegisterAnimal1.setBreed(breed);
		dtoRegisterAnimal1.setDateOfBirth("2021-08-10");
		dtoRegisterAnimal1.setEmployerID(employeeID);
		dtoRegisterAnimal1.setNotes("Natural birth");
		dtoRegisterAnimal1.setOthers(null);
		dtoRegisterAnimal1.setPregnancyExamination(motherExaminationId);
		dtoRegisterAnimal1.setPreviousAnimalID(null);
		dtoRegisterAnimal1.setReceivedFarmID(null);
		dtoRegisterAnimal1.setReceivedFarmName(null);
		dtoRegisterAnimal1.setSex("Cow");
		dtoRegisterAnimal1.setStatus("Active");
		dtoRegisterAnimal1.setUnit("kg");
		dtoRegisterAnimal1.setWeight(10);
		return dtoRegisterAnimal1;
	}

	public static List<Object> anAnimalReocrd(String newBornAnimalID1) {

		List<Object> animalRecord = new ArrayList<Object>();
		String employeeID = "HR-00-11-22";
		String farmID = "FR-11-22-66";
		String osid = "OS-00-12-38";
		String animalIDMother = "AR-34-567-37";
		String suid = "SU-90-23-34";
		DTOOrderSemen dtoOrderSemen = new DTOOrderSemen();
		dtoOrderSemen.setBreed("Svensk Fjällras");
		dtoOrderSemen.setContact("+46727894934");
		dtoOrderSemen.setEmailto("genetics@gen.se");
		dtoOrderSemen.setEmployeeID(employeeID);
		dtoOrderSemen.setFarmID(farmID);
		dtoOrderSemen.setNotes("Ordered for traditional Swedish breed of polled mountain cattle.");
		dtoOrderSemen.setOrderDate("2020/05/23");
		dtoOrderSemen.setOrderedTo("Peter Anderson");
		dtoOrderSemen.setOsid(osid);
		animalRecord.add(dtoOrderSemen);

		DTOResponseOrderSemen dtoResponseOrderSemen = new DTOResponseOrderSemen();
		dtoResponseOrderSemen.setBillingURL("http://www.invoices.se/" + osid);
		dtoResponseOrderSemen.setEmployeeID(employeeID);
		dtoResponseOrderSemen.setNotes("Received ordered and will be used for two cows.");
		dtoResponseOrderSemen.setOsid(osid);
		dtoResponseOrderSemen.setRepliedBy("genetics@gen.se");
		dtoResponseOrderSemen.setResDate("2020-05-24");
		animalRecord.add(dtoResponseOrderSemen);

		DTOSemenUtilization dtoSemenUtilization1 = new DTOSemenUtilization();
		dtoSemenUtilization1.setAnimalID(animalIDMother);
		dtoSemenUtilization1.setEmployeeID(employeeID);
		dtoSemenUtilization1.setInsemationDate("2020-08-12");
		dtoSemenUtilization1.setNotes("Artificial insemination");
		dtoSemenUtilization1.setSuid(suid);
		dtoSemenUtilization1.setOsid(osid);
		animalRecord.add(dtoSemenUtilization1);

		DTOAnimalExamination dtoAnimalExaminationMother10 = new DTOAnimalExamination();
		dtoAnimalExaminationMother10.setAeid("AE-11-53-56");
		dtoAnimalExaminationMother10.setAnimalID(animalIDMother);
		dtoAnimalExaminationMother10.setEmployeeID(employeeID);
		dtoAnimalExaminationMother10.setExaminationDate("2020-08-30");
		dtoAnimalExaminationMother10.setExtepctedDate(null);
		dtoAnimalExaminationMother10.setNotes("This is early examination through pregnancy test.");
		dtoAnimalExaminationMother10.setRefnumber("exteranl ocument");
		dtoAnimalExaminationMother10.setRefType("medicine");
		dtoAnimalExaminationMother10.setSensorData("sensordataid");
		dtoAnimalExaminationMother10.setStatus("false");

		animalRecord.add(dtoAnimalExaminationMother10);

		String vetID = "HR-29-276-76";
		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB10 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB10.setAnimalID(newBornAnimalID1);
		dtoGeneralHealthExaminationNB10.setGaheid("GHE-11-11-120");
		dtoGeneralHealthExaminationNB10.setGheDate("2021-08-30");
		dtoGeneralHealthExaminationNB10.setInfections("None");
		dtoGeneralHealthExaminationNB10.setLameness("None");
		dtoGeneralHealthExaminationNB10.setNotation("None");
		dtoGeneralHealthExaminationNB10.setObserver(vetID);
		dtoGeneralHealthExaminationNB10.setSwelling("on leg");
		dtoGeneralHealthExaminationNB10.setTemperature("97");
		dtoGeneralHealthExaminationNB10.setNotes("Overall is ok ");
		dtoGeneralHealthExaminationNB10.setWeak("No");
		dtoGeneralHealthExaminationNB10.setWound("No");
		animalRecord.add(dtoGeneralHealthExaminationNB10);

		String obsID = "OB-29-276-76";
		DTOGeneralHealthObservation dtoGeneralHealthObservation11 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation11.setAnimalID(newBornAnimalID1);
		dtoGeneralHealthObservation11.setBcs("No");
		dtoGeneralHealthObservation11.setGheDate("2021-09-15");
		dtoGeneralHealthObservation11.setGhoid("GHO-33-11-302");
		dtoGeneralHealthObservation11.setLimping("No");
		dtoGeneralHealthObservation11.setNotes("OK");
		dtoGeneralHealthObservation11.setObserver(obsID);
		dtoGeneralHealthObservation11.setSwelling("No");
		dtoGeneralHealthObservation11.setWeight("17");
		dtoGeneralHealthObservation11.setWound("No");
		animalRecord.add(dtoGeneralHealthObservation11);

		DTOTemporaryMovement dtoTemporaryMovement1 = new DTOTemporaryMovement();
		StringList animals = new StringList();
		animals.getData().add(newBornAnimalID1);
		dtoTemporaryMovement1.setAnimals(animals);
		dtoTemporaryMovement1.setEmployeeID(employeeID);
		dtoTemporaryMovement1.setInDate("2021-10-17");
		dtoTemporaryMovement1.setNotes("No");
		dtoTemporaryMovement1.setOutDate("2021-10-13");
		dtoTemporaryMovement1.setPurpose("Grazing");
		dtoTemporaryMovement1.setTmid("TM-34-56-36");
		dtoTemporaryMovement1.setTmType("General");
		animalRecord.add(dtoTemporaryMovement1);

		DTODrugsTreatments dtoDrugsTreatments = new DTODrugsTreatments();
		dtoDrugsTreatments.setAnimalID(newBornAnimalID1);
		dtoDrugsTreatments.setDtid("DT-333-454-333");
		dtoDrugsTreatments.setAssignedDate("2021/09/19");
		dtoDrugsTreatments.setDrungs("Paracetamole");
		dtoDrugsTreatments.setReason("Feeling tempa and coughing");
		dtoDrugsTreatments.setExaminedBy("HR-343-567-983");
		dtoDrugsTreatments.setReftype("Medical");
		dtoDrugsTreatments.setRefnumber(dtoGeneralHealthExaminationNB10.getGaheid());
		animalRecord.add(dtoDrugsTreatments);

		DTOFeedPattern dtoFeedPattern = new DTOFeedPattern();
		dtoFeedPattern.setCertiOfIngredients("");
		dtoFeedPattern.setCreationDate("2021-09-11");
		dtoFeedPattern.setFeedName("GreenLeaves");
		dtoFeedPattern.setFeedType("concentrates");
		dtoFeedPattern.setFoodSource("corn, rice, wheat, soybean, canola, sugarcane");
		dtoFeedPattern.setFpid("FP-11-34-484");
		dtoFeedPattern.setNotes("");
		dtoFeedPattern.setPercentage("100");
		dtoFeedPattern.setPrice("1000");
		animalRecord.add(dtoFeedPattern);

		DTOAssignFeed dtoAssignFeed1 = new DTOAssignFeed();
		dtoAssignFeed1.setAfid("AF-232-45-72");
		dtoAssignFeed1.setAssignedBy(employeeID);
		dtoAssignFeed1.setAssignedDate("2021-09-11");
		dtoAssignFeed1.setAssignedTo(newBornAnimalID1);
		dtoAssignFeed1.setFpid(dtoFeedPattern.getFpid());
		animalRecord.add(dtoFeedPattern);
		animalRecord.add(dtoAssignFeed1);

		return animalRecord;

	}
}
