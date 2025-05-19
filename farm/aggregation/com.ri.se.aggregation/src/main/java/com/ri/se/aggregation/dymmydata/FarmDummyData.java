package com.ri.se.aggregation.dymmydata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

public class FarmDummyData {

	public static String ip = "http://localhost";
	public static String owner = "";
	public static void main(String[] args) throws Exception {

		int port = 9000;
		
		String resource = "/tapi/idms/v1/auth";
		DTOUsers userDTO = new AccountHandling().authenticate(ip, port, resource, "basic "+Base64.encode("farmowner@gmail.com:abdul1234".getBytes()));
		if(Objects.isNull(userDTO)) {
					
		} else {
			System.out.println(userDTO.getVeid());
		}		submitRecords(userDTO.getVeid());
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

	public static void submitRecords(String employeeID) throws Exception {

		String animalIDMother1 = "AR-" + (System.currentTimeMillis() - 100);
		String animalIDMother2 = "AR-" + (System.currentTimeMillis() - 200);
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

		DTORegisterAnimal dtoRegisterAnimalM12 = new DTORegisterAnimal();
		dtoRegisterAnimalM12.setAboutAnimal("Transfered from another form");
		dtoRegisterAnimalM12.setAnimalID(animalIDMother2);
		dtoRegisterAnimalM12.setAnimalIDMother("");
		dtoRegisterAnimalM12.setBirthPlace("Uppsala Farm");
		dtoRegisterAnimalM12.setBreed("German Red Pied");
		dtoRegisterAnimalM12.setDateOfBirth("2019-12-25");
		dtoRegisterAnimalM12.setEmployerID(employeeID);
		dtoRegisterAnimalM12.setNotes("Natural birth");
		dtoRegisterAnimalM12.setOthers(null);
		dtoRegisterAnimalM12.setPreviousAnimalID("AR-12-12-13IMP");
		dtoRegisterAnimalM12.setReceivedFarmID("F-23-23-23");
		dtoRegisterAnimalM12.setReceivedFarmName("Sundsval");
		dtoRegisterAnimalM12.setSex("Cow");
		dtoRegisterAnimalM12.setStatus("Active");
		dtoRegisterAnimalM12.setUnit("kg");
		dtoRegisterAnimalM12.setWeight(138);

		DTORegisterAnimal dtoRegisterAnimalRep2 = null;
		port = 9002;
		resource = "/v1/registeranimal";
		str = commit(ip, port, resource, dtoRegisterAnimalM12);
		if (!Objects.isNull(str)) {
			dtoRegisterAnimalRep2 = new ObjectMapper().readValue(str, DTORegisterAnimal.class);
			animalIDMother2 = dtoRegisterAnimalRep2.getAnimalID();
		}
		// ====================================================

		DTOOrderSemen dtoOrderSemen = new DTOOrderSemen();
		dtoOrderSemen.setBreed("Svensk Fjällras");
		dtoOrderSemen.setContact("+46727894934");
		dtoOrderSemen.setEmailto("genetics@gen.se");
		dtoOrderSemen.setEmployeeID(employeeID);
		dtoOrderSemen.setFarmID("FID-340234302");
		dtoOrderSemen.setNotes("Ordered for traditional Swedish breed.");
		dtoOrderSemen.setOrderDate("2020-05-23");
		dtoOrderSemen.setOrderedTo("Peter Anderson");
		dtoOrderSemen.setOsid("OS-" + System.currentTimeMillis());
		// dtoOrderSemen.setStatus("Pending");

		port = 9007;
		resource = "/v1/ordersemen";

		str = commit(ip, port, resource, dtoOrderSemen);
		DTOOrderSemen dtoOrderSemenRep = null;
		if (!Objects.isNull(str)) {
			dtoOrderSemenRep = new ObjectMapper().readValue(str, DTOOrderSemen.class);
		}

		DTOResponseOrderSemen dtoResponseOrderSemen = new DTOResponseOrderSemen();
		dtoResponseOrderSemen.setBillingURL("http://www.invoices.se/" + dtoOrderSemenRep.getOsid());
		dtoResponseOrderSemen.setEmployeeID(employeeID);
		dtoResponseOrderSemen.setNotes("Received ordered and will be used for three cows.");
		dtoResponseOrderSemen.setOsid(dtoOrderSemenRep.getOsid());
		dtoResponseOrderSemen.setRepliedBy("genetics@gen.se");
		dtoResponseOrderSemen.setResDate("2020-05-24");
		port = 9007;
		resource = "/v1/responseordersemen";

		DTOResponseOrderSemen dtoResponseOrderSemenRep = null;
		str = commit(ip, port, resource, dtoResponseOrderSemen);
		if (!Objects.isNull(str)) {
			dtoResponseOrderSemenRep = new ObjectMapper().readValue(str, DTOResponseOrderSemen.class);
		}

		DTOSemenUtilization dtoSemenUtilization1 = new DTOSemenUtilization();
		dtoSemenUtilization1.setAnimalID(animalIDMother1);
		dtoSemenUtilization1.setEmployeeID(employeeID);
		dtoSemenUtilization1.setInsemationDate("2020-08-12");
		dtoSemenUtilization1.setNotes("Artificial insemination");
		dtoSemenUtilization1.setSuid("SU-1" + System.currentTimeMillis());
		dtoSemenUtilization1.setOsid(dtoOrderSemenRep.getOsid());

		port = 9007;
		resource = "/v1/semenutilization";

		DTOSemenUtilization dtoSemenUtilizationRep1 = null;
		str = commit(ip, port, resource, dtoSemenUtilization1);
		if (!Objects.isNull(str)) {
			dtoSemenUtilizationRep1 = new ObjectMapper().readValue(str, DTOSemenUtilization.class);
		}

		DTOSemenUtilization dtoSemenUtilization2 = new DTOSemenUtilization();
		dtoSemenUtilization2.setAnimalID(animalIDMother2);
		dtoSemenUtilization2.setEmployeeID(employeeID);
		dtoSemenUtilization2.setInsemationDate("2020-08-15");
		dtoSemenUtilization2.setNotes("Artificial insemination");
		dtoSemenUtilization2.setSuid("SU-2" + System.currentTimeMillis());
		dtoSemenUtilization2.setOsid(dtoOrderSemenRep.getOsid());
		port = 9007;
		resource = "/v1/semenutilization";

		DTOSemenUtilization dtoSemenUtilizationRep2 = null;
		str = commit(ip, port, resource, dtoSemenUtilization2);
		if (!Objects.isNull(str)) {
			dtoSemenUtilizationRep2 = new ObjectMapper().readValue(str, DTOSemenUtilization.class);
		}
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

		DTOAnimalExamination dtoAnimalExaminationMother11 = new DTOAnimalExamination();
		dtoAnimalExaminationMother11.setAeid("AEID-4" + System.currentTimeMillis());
		dtoAnimalExaminationMother11.setAnimalID(animalIDMother1);
		dtoAnimalExaminationMother11.setEmployeeID(employeeID);
		dtoAnimalExaminationMother11.setExaminationDate("2020-09-25");
		dtoAnimalExaminationMother11.setExtepctedDate(null);
		dtoAnimalExaminationMother11.setNotes("Examination through pregnancy test.");
		dtoAnimalExaminationMother11.setRefnumber("exteranl ocument");
		dtoAnimalExaminationMother11.setRefType("medicine");
		dtoAnimalExaminationMother11.setSensorData("sensordataid");
		dtoAnimalExaminationMother11.setStatus("true");
		port = 9011;
		resource = "/v1/animalexamination";
		commit(ip, port, resource, dtoAnimalExaminationMother11);

		DTOAnimalExamination dtoAnimalExaminationMother20 = new DTOAnimalExamination();
		dtoAnimalExaminationMother20.setAeid("AEID-5" + System.currentTimeMillis());
		dtoAnimalExaminationMother20.setAnimalID(animalIDMother2);
		dtoAnimalExaminationMother20.setEmployeeID(employeeID);
		dtoAnimalExaminationMother20.setExaminationDate("2020-08-30");
		dtoAnimalExaminationMother20.setExtepctedDate(null);
		dtoAnimalExaminationMother20.setNotes("This is early examination through pregnancy test.");
		dtoAnimalExaminationMother20.setRefnumber("exteranl ocument");
		dtoAnimalExaminationMother20.setRefType("medicine");
		dtoAnimalExaminationMother20.setSensorData("sensordataid");
		dtoAnimalExaminationMother20.setStatus("false");
		port = 9011;
		resource = "/v1/animalexamination";
		commit(ip, port, resource, dtoAnimalExaminationMother20);

		DTOAnimalExamination dtoAnimalExaminationMother21 = new DTOAnimalExamination();
		dtoAnimalExaminationMother21.setAeid("AE-6" + System.currentTimeMillis());
		dtoAnimalExaminationMother21.setAnimalID(animalIDMother2);
		dtoAnimalExaminationMother21.setEmployeeID(employeeID);
		dtoAnimalExaminationMother21.setExaminationDate("2020-09-30");
		dtoAnimalExaminationMother21.setExtepctedDate(null);
		dtoAnimalExaminationMother21.setNotes("Examination through pregnancy test.");
		dtoAnimalExaminationMother21.setRefnumber(null);
		dtoAnimalExaminationMother21.setRefType(null);
		dtoAnimalExaminationMother21.setSensorData("sensordataid");
		dtoAnimalExaminationMother21.setStatus("true");
		port = 9011;
		resource = "/v1/animalexamination";
		commit(ip, port, resource, dtoAnimalExaminationMother21);

		String newBornAnimalID1 = "AR-33-567-401";
		DTORegisterAnimal dtoRegisterAnimal1 = new DTORegisterAnimal();
		dtoRegisterAnimal1.setAboutAnimal("This is newly born animal");
		dtoRegisterAnimal1.setAnimalID(newBornAnimalID1);
		dtoRegisterAnimal1.setAnimalIDMother(animalIDMother1);
		dtoRegisterAnimal1.setBirthPlace("Uppsala Farm");
		dtoRegisterAnimal1.setBreed(dtoOrderSemen.getBreed());
		dtoRegisterAnimal1.setDateOfBirth("2021-08-10");
		dtoRegisterAnimal1.setEmployerID(employeeID);
		dtoRegisterAnimal1.setNotes("Natural birth");
		dtoRegisterAnimal1.setOthers(null);
		dtoRegisterAnimal1.setPregnancyExamination(dtoAnimalExaminationMother11.getAeid());
		dtoRegisterAnimal1.setPreviousAnimalID(null);
		dtoRegisterAnimal1.setReceivedFarmID(null);
		dtoRegisterAnimal1.setReceivedFarmName(null);
		dtoRegisterAnimal1.setSex("Cow");
		dtoRegisterAnimal1.setStatus("Active");
		dtoRegisterAnimal1.setUnit("kg");
		dtoRegisterAnimal1.setWeight(10);

		DTORegisterAnimal dtoRegisterAnimalRepNB1 = null;
		port = 9002;
		resource = "/v1/registeranimal";
		str = commit(ip, port, resource, dtoRegisterAnimal1);
		if (!Objects.isNull(str)) {
			dtoRegisterAnimalRepNB1 = new ObjectMapper().readValue(str, DTORegisterAnimal.class);
			newBornAnimalID1 = dtoRegisterAnimalRepNB1.getAnimalID();
			System.out.println("========================>       " + newBornAnimalID1);

		}

		// May be good for animal health
		String vetID = "HR-29-276-761";
		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB10 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB10.setAnimalID(newBornAnimalID1);
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

		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB11 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB11.setAnimalID(animalIDMother1);
		dtoGeneralHealthExaminationNB11.setGaheid("GHE" + "8" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB11.setGheDate("2021-09-30");
		dtoGeneralHealthExaminationNB11.setInfections("No");
		dtoGeneralHealthExaminationNB11.setLameness("No");
		dtoGeneralHealthExaminationNB11.setNotation("No");
		dtoGeneralHealthExaminationNB11.setObserver(vetID);
		dtoGeneralHealthExaminationNB11.setSwelling("No");
		dtoGeneralHealthExaminationNB11.setTemperature("97");
		dtoGeneralHealthExaminationNB11.setNotes("Overall is ok ");
		dtoGeneralHealthExaminationNB11.setWeak("No");
		dtoGeneralHealthExaminationNB11.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthexamination";
		commit(ip, port, resource, dtoGeneralHealthExaminationNB11);

		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB12 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB12.setAnimalID(animalIDMother2);
		dtoGeneralHealthExaminationNB12.setGaheid("GHE" + "9" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB12.setGheDate("2021-10-30");
		dtoGeneralHealthExaminationNB12.setInfections("No");
		dtoGeneralHealthExaminationNB12.setLameness("No");
		dtoGeneralHealthExaminationNB12.setNotation("No");
		dtoGeneralHealthExaminationNB12.setObserver(vetID);
		dtoGeneralHealthExaminationNB12.setSwelling("No");
		dtoGeneralHealthExaminationNB12.setTemperature("97");
		dtoGeneralHealthExaminationNB12.setNotes("Ingood health");
		dtoGeneralHealthExaminationNB12.setWeak("No");
		dtoGeneralHealthExaminationNB12.setWound("No");

		port = 9011;
		resource = "/v1/generalhealthexamination";
		commit(ip, port, resource, dtoGeneralHealthExaminationNB12);

		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB13 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB13.setAnimalID(newBornAnimalID1);
		dtoGeneralHealthExaminationNB13.setGaheid("GHE" + "10" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB13.setGheDate("2021-12-30");
		dtoGeneralHealthExaminationNB13.setInfections("No");
		dtoGeneralHealthExaminationNB13.setLameness("No");
		dtoGeneralHealthExaminationNB13.setNotation("No");
		dtoGeneralHealthExaminationNB13.setObserver(vetID);
		dtoGeneralHealthExaminationNB13.setSwelling("No");
		dtoGeneralHealthExaminationNB13.setTemperature("97");
		dtoGeneralHealthExaminationNB13.setNotes("Ingood health");
		dtoGeneralHealthExaminationNB13.setWeak("No");
		dtoGeneralHealthExaminationNB13.setWound("No");

		port = 9011;
		resource = "/v1/generalhealthexamination";
		commit(ip, port, resource, dtoGeneralHealthExaminationNB13);

		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB14 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB14.setAnimalID(animalIDMother1);
		dtoGeneralHealthExaminationNB14.setGaheid("GHE" + "11" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB14.setGheDate("2022-01-10");
		dtoGeneralHealthExaminationNB14.setInfections("Yes");
		dtoGeneralHealthExaminationNB14.setLameness("No");
		dtoGeneralHealthExaminationNB14.setNotation("No");
		dtoGeneralHealthExaminationNB14.setObserver(vetID);
		dtoGeneralHealthExaminationNB14.setSwelling("No");
		dtoGeneralHealthExaminationNB14.setTemperature("97");
		dtoGeneralHealthExaminationNB14.setNotes("Infected and need care for coughing !");
		dtoGeneralHealthExaminationNB14.setWeak("No");
		dtoGeneralHealthExaminationNB14.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthexamination";
		commit(ip, port, resource, dtoGeneralHealthExaminationNB14);

		// --------------------------------------------------------------
		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB20 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB20.setAnimalID(animalIDMother2);
		dtoGeneralHealthExaminationNB20.setGaheid("GHE" + "12" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB20.setGheDate("2021-08-30");
		dtoGeneralHealthExaminationNB20.setInfections("None");
		dtoGeneralHealthExaminationNB20.setLameness("None");
		dtoGeneralHealthExaminationNB20.setNotation("None");
		dtoGeneralHealthExaminationNB20.setObserver(vetID);
		dtoGeneralHealthExaminationNB20.setSwelling("on leg");
		dtoGeneralHealthExaminationNB20.setTemperature("97");
		dtoGeneralHealthExaminationNB20.setNotes("Overall is ok ");
		dtoGeneralHealthExaminationNB20.setWeak("No");
		dtoGeneralHealthExaminationNB20.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthexamination";
		commit(ip, port, resource, dtoGeneralHealthExaminationNB20);

		//////////////////////////

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

		DTOGeneralHealthObservation dtoGeneralHealthObservation11 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation11.setAnimalID(newBornAnimalID1);
		dtoGeneralHealthObservation11.setBcs("No");
		dtoGeneralHealthObservation11.setGheDate("2021-09-15");
		dtoGeneralHealthObservation11.setGhoid("GHO" + "17" + System.currentTimeMillis());
		dtoGeneralHealthObservation11.setLimping("No");
		dtoGeneralHealthObservation11.setNotes("OK");
		dtoGeneralHealthObservation11.setObserver(obsID);
		dtoGeneralHealthObservation11.setSwelling("No");
		dtoGeneralHealthObservation11.setWeight("17");
		dtoGeneralHealthObservation11.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthobservation";
		commit(ip, port, resource, dtoGeneralHealthObservation11);

		DTOGeneralHealthObservation dtoGeneralHealthObservation13 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation13.setAnimalID(animalIDMother1);
		dtoGeneralHealthObservation13.setBcs("No");
		dtoGeneralHealthObservation13.setGheDate("2021-12-15");
		dtoGeneralHealthObservation13.setGhoid("GHO" + "19" + System.currentTimeMillis());
		dtoGeneralHealthObservation13.setLimping("No");
		dtoGeneralHealthObservation13.setNotes("Generally OK");
		dtoGeneralHealthObservation13.setObserver(obsID);
		dtoGeneralHealthObservation13.setSwelling("No");
		dtoGeneralHealthObservation13.setWeight("33");
		dtoGeneralHealthObservation13.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthobservation";
		commit(ip, port, resource, dtoGeneralHealthObservation13);

		DTOGeneralHealthObservation dtoGeneralHealthObservation23 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation23.setAnimalID(animalIDMother1);
		dtoGeneralHealthObservation23.setBcs("No");
		dtoGeneralHealthObservation23.setGheDate("2021-11-13");
		dtoGeneralHealthObservation23.setGhoid("GHO" + "23" + System.currentTimeMillis());
		dtoGeneralHealthObservation23.setLimping("No");
		dtoGeneralHealthObservation23.setNotes("Generally OK");
		dtoGeneralHealthObservation23.setObserver(obsID);
		dtoGeneralHealthObservation23.setSwelling("No");
		dtoGeneralHealthObservation23.setWeight("36");
		dtoGeneralHealthObservation23.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthobservation";
		commit(ip, port, resource, dtoGeneralHealthObservation23);

		DTODrugsTreatments dtoDrugsTreatments1 = new DTODrugsTreatments();
		dtoDrugsTreatments1.setAnimalID(newBornAnimalID1);
		dtoDrugsTreatments1.setDtid("DT" + "27" + System.currentTimeMillis());
		dtoDrugsTreatments1.setAssignedDate("2021-09-19");
		dtoDrugsTreatments1.setDrungs("Saccharomyces Boulardii Probiotic Powder");
		dtoDrugsTreatments1.setReason("Feeling tempa and coughing");
		dtoDrugsTreatments1.setExaminedBy("HR-343-567-983");
		dtoDrugsTreatments1.setReftype("Medical");
		dtoDrugsTreatments1.setEndDate("2021-09-24");
		dtoDrugsTreatments1.setQuarantinePeriod(10);
		dtoDrugsTreatments1.setIsAntibiotic("Yes");
		
		dtoDrugsTreatments1.setRefnumber(dtoGeneralHealthExaminationNB14.getGaheid());
		port = 9011;
		resource = "/v1/drugstreatments";
		commit(ip, port, resource, dtoDrugsTreatments1);
		
		DTODrugsTreatments dtoDrugsTreatments2 = new DTODrugsTreatments();
		dtoDrugsTreatments2.setAnimalID(newBornAnimalID1);
		dtoDrugsTreatments2.setDtid("DT" + "27" + System.currentTimeMillis());
		dtoDrugsTreatments2.setAssignedDate("2021-09-19");
		dtoDrugsTreatments2.setDrungs("Saccharomyces Boulardii Probiotic Powder");
		dtoDrugsTreatments2.setReason("Feeling tempa and coughing");
		dtoDrugsTreatments2.setExaminedBy("HR-343-567-983");
		dtoDrugsTreatments2.setReftype("Medical");
		dtoDrugsTreatments2.setEndDate("2025-01-24");
		dtoDrugsTreatments2.setQuarantinePeriod(10);
		dtoDrugsTreatments2.setIsAntibiotic("Yes");
		dtoDrugsTreatments2.setRefnumber(dtoGeneralHealthExaminationNB14.getGaheid());
		port = 9011;
		resource = "/v1/drugstreatments";
		commit(ip, port, resource, dtoDrugsTreatments2);
		

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
			dtoAssignFeed1.setAssignedTo(newBornAnimalID1);
			dtoAssignFeed1.setFpid(fp.getFpid());
			port = 9013;
			resource = "/v1/assignfeed";
			commit(ip, port, resource, dtoAssignFeed1);

			DTOAssignFeed dtoAssignFee3 = new DTOAssignFeed();
			dtoAssignFee3.setAfid("AF" + "30" + System.currentTimeMillis());
			dtoAssignFee3.setAssignedBy(employeeID);
			dtoAssignFee3.setAssignedDate("2021-09-11");
			dtoAssignFee3.setAssignedTo(animalIDMother1);
			dtoAssignFee3.setFpid(fp.getFpid());
			port = 9013;
			resource = "/v1/assignfeed";
			commit(ip, port, resource, dtoAssignFee3);
			DTOAssignFeed dtoAssignFeed2 = new DTOAssignFeed();
			dtoAssignFeed2.setAfid("AF" + "31" + System.currentTimeMillis());
			dtoAssignFeed2.setAssignedBy(employeeID);
			dtoAssignFeed2.setAssignedDate("2021-09-11");
			dtoAssignFeed2.setAssignedTo(animalIDMother2);
			dtoAssignFeed2.setFpid(fp.getFpid());
			port = 9013;
			resource = "/v1/assignfeed";
			commit(ip, port, resource, dtoAssignFeed2);

		}

		//////////////////////////////// 2@nd Animal////////////////////////
		String newBornAnimalID2 = "AR-33-567-411";
		DTORegisterAnimal dtoRegisterAnimal2 = new DTORegisterAnimal();
		dtoRegisterAnimal2.setAboutAnimal("This is newly born animal");
		dtoRegisterAnimal2.setAnimalID(newBornAnimalID2);
		dtoRegisterAnimal2.setAnimalIDMother(animalIDMother2);
		dtoRegisterAnimal2.setBirthPlace("Uppsala Farm");
		dtoRegisterAnimal2.setBreed(dtoOrderSemen.getBreed());
		dtoRegisterAnimal2.setDateOfBirth("2021-08-12");
		dtoRegisterAnimal2.setEmployerID(employeeID);
		dtoRegisterAnimal2.setNotes("Natural birth");
		dtoRegisterAnimal2.setOthers(null);
		dtoRegisterAnimal2.setPregnancyExamination(dtoAnimalExaminationMother11.getAeid());
		dtoRegisterAnimal2.setPreviousAnimalID(null);
		dtoRegisterAnimal2.setReceivedFarmID(null);
		dtoRegisterAnimal2.setReceivedFarmName(null);
		dtoRegisterAnimal2.setSex("Cow");
		dtoRegisterAnimal2.setStatus("Active");
		dtoRegisterAnimal2.setUnit("kg");
		dtoRegisterAnimal2.setWeight(12);
		port = 9002;
		resource = "/v1/registeranimal";

		DTORegisterAnimal dtoRegisterAnimalRepNB2 = null;
		str = commit(ip, port, resource, dtoRegisterAnimal2);
		if (!Objects.isNull(str)) {
			dtoRegisterAnimalRepNB2 = new ObjectMapper().readValue(str, DTORegisterAnimal.class);
			newBornAnimalID2 = dtoRegisterAnimalRepNB2.getAnimalID();
		}
		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB21 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB21.setAnimalID(newBornAnimalID2);
		dtoGeneralHealthExaminationNB21.setGaheid("GHE" + "13" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB21.setGheDate("2021-09-30");
		dtoGeneralHealthExaminationNB21.setInfections("No");
		dtoGeneralHealthExaminationNB21.setLameness("No");
		dtoGeneralHealthExaminationNB21.setNotation("No");
		dtoGeneralHealthExaminationNB21.setObserver(vetID);
		dtoGeneralHealthExaminationNB21.setSwelling("No");
		dtoGeneralHealthExaminationNB21.setTemperature("97");
		dtoGeneralHealthExaminationNB21.setNotes("Overall is ok ");
		dtoGeneralHealthExaminationNB21.setWeak("No");
		dtoGeneralHealthExaminationNB21.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthexamination";

		commit(ip, port, resource, dtoGeneralHealthExaminationNB21);

		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB22 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB22.setAnimalID(newBornAnimalID2);
		dtoGeneralHealthExaminationNB22.setGaheid("GHE" + "14" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB22.setGheDate("2021-10-30");
		dtoGeneralHealthExaminationNB22.setInfections("No");
		dtoGeneralHealthExaminationNB22.setLameness("No");
		dtoGeneralHealthExaminationNB22.setNotation("No");
		dtoGeneralHealthExaminationNB22.setObserver(vetID);
		dtoGeneralHealthExaminationNB22.setSwelling("No");
		dtoGeneralHealthExaminationNB22.setTemperature("97");
		dtoGeneralHealthExaminationNB22.setNotes("Ingood health");
		dtoGeneralHealthExaminationNB22.setWeak("No");
		dtoGeneralHealthExaminationNB22.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthexamination";

		commit(ip, port, resource, dtoGeneralHealthExaminationNB22);

		DTOGeneralHealthExamination dtoGeneralHealthExaminationNB23 = new DTOGeneralHealthExamination();
		dtoGeneralHealthExaminationNB23.setAnimalID(newBornAnimalID2);
		dtoGeneralHealthExaminationNB23.setGaheid("GHE" + "15" + System.currentTimeMillis());
		dtoGeneralHealthExaminationNB23.setGheDate("2021-12-30");
		dtoGeneralHealthExaminationNB23.setInfections("No");
		dtoGeneralHealthExaminationNB23.setLameness("No");
		dtoGeneralHealthExaminationNB23.setNotation("No");
		dtoGeneralHealthExaminationNB23.setObserver(vetID);
		dtoGeneralHealthExaminationNB23.setSwelling("No");
		dtoGeneralHealthExaminationNB23.setTemperature("97");
		dtoGeneralHealthExaminationNB23.setNotes("Ingood health");
		dtoGeneralHealthExaminationNB23.setWeak("No");
		dtoGeneralHealthExaminationNB23.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthexamination";

		commit(ip, port, resource, dtoGeneralHealthExaminationNB23);

		DTOGeneralHealthObservation dtoGeneralHealthObservation12 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation12.setAnimalID(newBornAnimalID2);
		dtoGeneralHealthObservation12.setBcs("No");
		dtoGeneralHealthObservation12.setGheDate("2021-10-15");
		dtoGeneralHealthObservation12.setGhoid("GHO" + "18" + System.currentTimeMillis());
		dtoGeneralHealthObservation12.setLimping("No");
		dtoGeneralHealthObservation12.setNotes("General observation is OK");
		dtoGeneralHealthObservation12.setObserver(obsID);
		dtoGeneralHealthObservation12.setSwelling("No");
		dtoGeneralHealthObservation12.setWeight("22");
		dtoGeneralHealthObservation12.setWound("No");

		port = 9011;
		resource = "/v1/generalhealthobservation";

		commit(ip, port, resource, dtoGeneralHealthObservation12);

		// for newborn 2
		DTOGeneralHealthObservation dtoGeneralHealthObservation20 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation20.setAnimalID(newBornAnimalID2);
		dtoGeneralHealthObservation20.setBcs("No");
		dtoGeneralHealthObservation20.setGheDate("2021-08-13");
		dtoGeneralHealthObservation20.setGhoid("GHO" + "20" + System.currentTimeMillis());
		dtoGeneralHealthObservation20.setLimping("No");
		dtoGeneralHealthObservation20.setNotes("General observation is OK");
		dtoGeneralHealthObservation20.setObserver(obsID);
		dtoGeneralHealthObservation20.setSwelling("No");
		dtoGeneralHealthObservation20.setWeight("13");
		dtoGeneralHealthObservation20.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthobservation";

		commit(ip, port, resource, dtoGeneralHealthObservation20);

		DTOGeneralHealthObservation dtoGeneralHealthObservation21 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation21.setAnimalID(newBornAnimalID2);
		dtoGeneralHealthObservation21.setBcs("No");
		dtoGeneralHealthObservation21.setGheDate("2021-09-13");
		dtoGeneralHealthObservation21.setGhoid("GHO" + "21" + System.currentTimeMillis());
		dtoGeneralHealthObservation21.setLimping("No");
		dtoGeneralHealthObservation21.setNotes("OK");
		dtoGeneralHealthObservation21.setObserver(obsID);
		dtoGeneralHealthObservation21.setSwelling("No");
		dtoGeneralHealthObservation21.setWeight("17");
		dtoGeneralHealthObservation21.setWound("No");

		port = 9011;
		resource = "/v1/generalhealthobservation";

		commit(ip, port, resource, dtoGeneralHealthObservation21);

		DTOTemporaryMovement dtoTemporaryMovement1 = new DTOTemporaryMovement();
		StringList animals = new StringList();
		animals.getData().add(newBornAnimalID1);
		animals.getData().add(newBornAnimalID2);
		animals.getData().add(animalIDMother1);
		animals.getData().add(animalIDMother2);
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

		DTOTemporaryMovement dtoTemporaryMovement2 = new DTOTemporaryMovement();
		dtoTemporaryMovement2.setAnimals(animals);
		dtoTemporaryMovement2.setEmployeeID(employeeID);
		dtoTemporaryMovement2.setInDate("2021-11-11");
		dtoTemporaryMovement2.setNotes("No");
		dtoTemporaryMovement2.setOutDate("2021-10-28");
		dtoTemporaryMovement2.setPurpose("Grazing");
		dtoTemporaryMovement2.setTmid("TM" + "25" + System.currentTimeMillis());
		dtoTemporaryMovement2.setTmType("General");
		port = 9013;
		resource = "/v1/temporarymovement";
		commit(ip, port, resource, dtoTemporaryMovement2);

		DTOTemporaryMovement dtoTemporaryMovement3 = new DTOTemporaryMovement();
		dtoTemporaryMovement3.setAnimals(animals);
		dtoTemporaryMovement3.setEmployeeID(employeeID);
		dtoTemporaryMovement3.setInDate("2021-11-14");
		dtoTemporaryMovement3.setNotes("No");
		dtoTemporaryMovement3.setOutDate("2021-11-17");
		dtoTemporaryMovement3.setPurpose("Grazing");
		dtoTemporaryMovement3.setTmid("TM" + "26" + System.currentTimeMillis());
		dtoTemporaryMovement3.setTmType("General");
		port = 9013;
		resource = "/v1/temporarymovement";
		commit(ip, port, resource, dtoTemporaryMovement3);
		DTOGeneralHealthObservation dtoGeneralHealthObservation22 = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation22.setAnimalID(newBornAnimalID2);
		dtoGeneralHealthObservation22.setBcs("No");
		dtoGeneralHealthObservation22.setGheDate("2021-10-13");
		dtoGeneralHealthObservation22.setGhoid("GHO" + "22" + System.currentTimeMillis());
		dtoGeneralHealthObservation22.setLimping("No");
		dtoGeneralHealthObservation22.setNotes("OK");
		dtoGeneralHealthObservation22.setObserver(obsID);
		dtoGeneralHealthObservation22.setSwelling("No");
		dtoGeneralHealthObservation22.setWeight("24");
		dtoGeneralHealthObservation22.setWound("No");
		port = 9011;
		resource = "/v1/generalhealthobservation";
		commit(ip, port, resource, dtoGeneralHealthObservation22);
		
		String newBornAnimalID3 = "AR-33-567-411";
		//=======================================================
		{
			
			dtoRegisterAnimal2 = new DTORegisterAnimal();
			dtoRegisterAnimal2.setAboutAnimal("This is newly born animal");
			dtoRegisterAnimal2.setAnimalID(newBornAnimalID3);
			dtoRegisterAnimal2.setAnimalIDMother(newBornAnimalID2);
			dtoRegisterAnimal2.setBirthPlace("Uppsala Farm");
			dtoRegisterAnimal2.setBreed(dtoOrderSemen.getBreed());
			dtoRegisterAnimal2.setDateOfBirth("2022-08-12");
			dtoRegisterAnimal2.setEmployerID(employeeID);
			dtoRegisterAnimal2.setNotes("Natural birth");
			dtoRegisterAnimal2.setOthers(null);
			dtoRegisterAnimal2.setPregnancyExamination(dtoAnimalExaminationMother11.getAeid());
			dtoRegisterAnimal2.setPreviousAnimalID(null);
			dtoRegisterAnimal2.setReceivedFarmID(null);
			dtoRegisterAnimal2.setReceivedFarmName(null);
			dtoRegisterAnimal2.setSex("Cow");
			dtoRegisterAnimal2.setStatus("Active");
			dtoRegisterAnimal2.setUnit("kg");
			dtoRegisterAnimal2.setWeight(12);
			port = 9002;
			resource = "/v1/registeranimal";

			dtoRegisterAnimalRepNB2 = null;
			str = commit(ip, port, resource, dtoRegisterAnimal2);
			if (!Objects.isNull(str)) {
				dtoRegisterAnimalRepNB2 = new ObjectMapper().readValue(str, DTORegisterAnimal.class);
				newBornAnimalID3 = dtoRegisterAnimalRepNB2.getAnimalID();
			}
			dtoGeneralHealthExaminationNB21 = new DTOGeneralHealthExamination();
			dtoGeneralHealthExaminationNB21.setAnimalID(newBornAnimalID3);
			dtoGeneralHealthExaminationNB21.setGaheid("GHE" + "13" + System.currentTimeMillis());
			dtoGeneralHealthExaminationNB21.setGheDate("2022-09-30");
			dtoGeneralHealthExaminationNB21.setInfections("No");
			dtoGeneralHealthExaminationNB21.setLameness("No");
			dtoGeneralHealthExaminationNB21.setNotation("No");
			dtoGeneralHealthExaminationNB21.setObserver(vetID);
			dtoGeneralHealthExaminationNB21.setSwelling("No");
			dtoGeneralHealthExaminationNB21.setTemperature("97");
			dtoGeneralHealthExaminationNB21.setNotes("Overall is ok ");
			dtoGeneralHealthExaminationNB21.setWeak("No");
			dtoGeneralHealthExaminationNB21.setWound("No");
			port = 9011;
			resource = "/v1/generalhealthexamination";

			commit(ip, port, resource, dtoGeneralHealthExaminationNB21);

			dtoGeneralHealthExaminationNB22 = new DTOGeneralHealthExamination();
			dtoGeneralHealthExaminationNB22.setAnimalID(newBornAnimalID3);
			dtoGeneralHealthExaminationNB22.setGaheid("GHE" + "14" + System.currentTimeMillis());
			dtoGeneralHealthExaminationNB22.setGheDate("2022-10-30");
			dtoGeneralHealthExaminationNB22.setInfections("No");
			dtoGeneralHealthExaminationNB22.setLameness("No");
			dtoGeneralHealthExaminationNB22.setNotation("No");
			dtoGeneralHealthExaminationNB22.setObserver(vetID);
			dtoGeneralHealthExaminationNB22.setSwelling("No");
			dtoGeneralHealthExaminationNB22.setTemperature("97");
			dtoGeneralHealthExaminationNB22.setNotes("Ingood health");
			dtoGeneralHealthExaminationNB22.setWeak("No");
			dtoGeneralHealthExaminationNB22.setWound("No");
			port = 9011;
			resource = "/v1/generalhealthexamination";

			commit(ip, port, resource, dtoGeneralHealthExaminationNB22);

		}
		
		//================================== 3RD aNIMAL======================
		{
			String newBornAnimalID4 = "AR-33-567-411";
			dtoRegisterAnimal2 = new DTORegisterAnimal();
			dtoRegisterAnimal2.setAboutAnimal("This is newly born animal");
			dtoRegisterAnimal2.setAnimalID(newBornAnimalID4);
			dtoRegisterAnimal2.setAnimalIDMother(newBornAnimalID3);
			dtoRegisterAnimal2.setBirthPlace("Uppsala Farm");
			dtoRegisterAnimal2.setBreed(dtoOrderSemen.getBreed());
			dtoRegisterAnimal2.setDateOfBirth("2024-08-12");
			dtoRegisterAnimal2.setEmployerID(employeeID);
			dtoRegisterAnimal2.setNotes("Natural birth");
			dtoRegisterAnimal2.setOthers(null);
			dtoRegisterAnimal2.setPregnancyExamination(dtoAnimalExaminationMother11.getAeid());
			dtoRegisterAnimal2.setPreviousAnimalID(null);
			dtoRegisterAnimal2.setReceivedFarmID(null);
			dtoRegisterAnimal2.setReceivedFarmName(null);
			dtoRegisterAnimal2.setSex("Cow");
			dtoRegisterAnimal2.setStatus("Active");
			dtoRegisterAnimal2.setUnit("kg");
			dtoRegisterAnimal2.setWeight(12);
			port = 9002;
			resource = "/v1/registeranimal";

			dtoRegisterAnimalRepNB2 = null;
			str = commit(ip, port, resource, dtoRegisterAnimal2);
			if (!Objects.isNull(str)) {
				dtoRegisterAnimalRepNB2 = new ObjectMapper().readValue(str, DTORegisterAnimal.class);
				newBornAnimalID3 = dtoRegisterAnimalRepNB2.getAnimalID();
			}
			dtoGeneralHealthExaminationNB21 = new DTOGeneralHealthExamination();
			dtoGeneralHealthExaminationNB21.setAnimalID(newBornAnimalID3);
			dtoGeneralHealthExaminationNB21.setGaheid("GHE" + "13" + System.currentTimeMillis());
			dtoGeneralHealthExaminationNB21.setGheDate("2024-09-30");
			dtoGeneralHealthExaminationNB21.setInfections("No");
			dtoGeneralHealthExaminationNB21.setLameness("No");
			dtoGeneralHealthExaminationNB21.setNotation("No");
			dtoGeneralHealthExaminationNB21.setObserver(vetID);
			dtoGeneralHealthExaminationNB21.setSwelling("No");
			dtoGeneralHealthExaminationNB21.setTemperature("97");
			dtoGeneralHealthExaminationNB21.setNotes("Overall is ok ");
			dtoGeneralHealthExaminationNB21.setWeak("No");
			dtoGeneralHealthExaminationNB21.setWound("No");
			port = 9011;
			resource = "/v1/generalhealthexamination";

			commit(ip, port, resource, dtoGeneralHealthExaminationNB21);

			dtoGeneralHealthExaminationNB22 = new DTOGeneralHealthExamination();
			dtoGeneralHealthExaminationNB22.setAnimalID(newBornAnimalID3);
			dtoGeneralHealthExaminationNB22.setGaheid("GHE" + "14" + System.currentTimeMillis());
			dtoGeneralHealthExaminationNB22.setGheDate("2024-10-30");
			dtoGeneralHealthExaminationNB22.setInfections("No");
			dtoGeneralHealthExaminationNB22.setLameness("No");
			dtoGeneralHealthExaminationNB22.setNotation("No");
			dtoGeneralHealthExaminationNB22.setObserver(vetID);
			dtoGeneralHealthExaminationNB22.setSwelling("No");
			dtoGeneralHealthExaminationNB22.setTemperature("97");
			dtoGeneralHealthExaminationNB22.setNotes("Ingood health");
			dtoGeneralHealthExaminationNB22.setWeak("No");
			dtoGeneralHealthExaminationNB22.setWound("No");
			port = 9011;
			resource = "/v1/generalhealthexamination";

			commit(ip, port, resource, dtoGeneralHealthExaminationNB22);

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
