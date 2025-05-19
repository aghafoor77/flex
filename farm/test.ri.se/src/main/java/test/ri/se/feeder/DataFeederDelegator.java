package test.ri.se.feeder;

import java.util.HashMap;
import java.util.Random;

import test.ri.se.dtos.DTOAnimalExamination;
import test.ri.se.dtos.DTOAssignFeed;
import test.ri.se.dtos.DTODrugsTreatments;
import test.ri.se.dtos.DTOFeedPattern;
import test.ri.se.dtos.DTOGeneralHealthExamination;
import test.ri.se.dtos.DTOGeneralHealthObservation;
import test.ri.se.dtos.DTOMoveBullForHerd;
import test.ri.se.dtos.DTOOrderSemen;
import test.ri.se.dtos.DTORegisterAnimal;
import test.ri.se.dtos.DTOSemenUtilization;
import test.ri.se.dtos.DTOTemporaryMovement;
import test.ri.se.dtos.DTOTemporaryMovementGroup;
import test.ri.se.dtos.StringList;

public class DataFeederDelegator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String get(String head) {
		HashMap<String, String> hash = new HashMap();
		hash.put("A", "v1/analytics");
		hash.put("DR", ":9002/application/v1/animalderegister");
		hash.put("EX", ":9011/application/v1/animalexamination");
		hash.put("AR", ":9002/application/v1/registeranimal");
		hash.put("AAS", ":9002/application/v1/assignanimalstatus");
		hash.put("DT", ":9011/application/v1/drugstreatments");
		hash.put("AF", ":9013/application/v1/assignfeed");
		hash.put("FP", ":9013/application/v1/feedpattern");
		hash.put("GHE", ":9011/application/v1/generalhealthexamination");
		hash.put("GHO", ":9011/application/v1/generalhealthobservation");
		hash.put("MBH", ":9007/v1/movebullforherd");
		hash.put("OS", ":9007/application/v1/ordersemen");
		hash.put("ROS", ":9007/application/v1/responseordersemen");
		hash.put("SU", ":9007/application/v1/semenutilization");
		hash.put("TM", ":9013/application/v1/temporarymovement");
		hash.put("TMG", ":9013/application/v1/temporarymovementgroup");
		System.out.println(hash.get(head));
		return hash.get(head);
	}

	public String employee = "employee@lsc.se";

	// Register first animal
	public DTORegisterAnimal addFirstDTORegisterAnimal() {
		DTORegisterAnimal dtoRegisterAnimal = new DTORegisterAnimal();
		dtoRegisterAnimal.setAboutAnimal("A cow recived from another farm.");
		dtoRegisterAnimal.setAnimalID("AR-13-11");
		dtoRegisterAnimal.setBirthPlace("Uppsala");
		dtoRegisterAnimal.setBreed("Swedish Polled");
		dtoRegisterAnimal.setDateOfBirth("2015-10-" + new Random().nextInt(30));
		dtoRegisterAnimal.setEmployerID(employee);
		dtoRegisterAnimal.setNotes("This is Swedish Polled breed cow first came to the farm.");
		dtoRegisterAnimal.setOthers(null);
		dtoRegisterAnimal.setAnimalIDMother("None");
		dtoRegisterAnimal.setPregnancyExamination(null);
		dtoRegisterAnimal.setPreviousAnimalID("AH-0-0");
		dtoRegisterAnimal.setReceivedFarmID("FF-20-20");
		dtoRegisterAnimal.setReceivedFarmName("Uppsala gord");
		dtoRegisterAnimal.setRegistrationDate("2023-11-" + new Random().nextInt(30));
		dtoRegisterAnimal.setSex("Cow");
		dtoRegisterAnimal.setStatus("Active");
		dtoRegisterAnimal.setUnit("kg");
		dtoRegisterAnimal.setWeight(42);
		dtoRegisterAnimal.setPreviousAnimalID("AH-0-0");
		return dtoRegisterAnimal;

	}
	// Prebirth data feed

	public DTOOrderSemen addDTOOrderSemen(int i) {
		DTOOrderSemen dtoOrderSemen = new DTOOrderSemen();
		dtoOrderSemen.setBreed("Swedish Polled");
		dtoOrderSemen.setContact("0725454785");
		dtoOrderSemen.setEmailto("abc@genetic.se");
		dtoOrderSemen.setEmployeeID(employee);
		dtoOrderSemen.setFarmID("5445-4545-78");
		dtoOrderSemen.setNotes("");
		dtoOrderSemen.setOrderDate("2023-10-20");
		dtoOrderSemen.setOrderedTo("abc@genetic.com");
		dtoOrderSemen.setOsid("OS-1-" + i);
		dtoOrderSemen.setStatus("Done");
		return dtoOrderSemen;
	}

	public DTOSemenUtilization  addDTOSemenUtilization(int i, String animalID, DTOOrderSemen dtoOrderSemen) {
		DTOSemenUtilization dtoSemenUtilization = new DTOSemenUtilization();
		dtoSemenUtilization.setAnimalID(animalID);
		dtoSemenUtilization.setEmployeeID(employee);
		dtoSemenUtilization.setInsemationDate("2022-12-10");
		dtoSemenUtilization.setNotes(employee);
		dtoSemenUtilization.setOsid("OS-1-" + i);
		dtoSemenUtilization.setSuid(dtoOrderSemen.getOsid());
		return dtoSemenUtilization ;
	}

	public DTOMoveBullForHerd addDTOMoveBullForHerd() {
		DTOMoveBullForHerd dtoMoveBullForHerd = new DTOMoveBullForHerd();
		return dtoMoveBullForHerd;

	}

	public DTOAnimalExamination addDTOAnimalExamination(String anoimalID) {
		DTOAnimalExamination dtoAnimalExamination = new DTOAnimalExamination();
		dtoAnimalExamination.setAeid("AE-110-111");
		dtoAnimalExamination.setAnimalID(anoimalID);
		dtoAnimalExamination.setExaminationDate("2023-10-12");
		dtoAnimalExamination.setExtepctedDate("2023-10-20");
		dtoAnimalExamination.setNotes("No notes");
		dtoAnimalExamination.setRefnumber("No");
		dtoAnimalExamination.setRefType("No");
		dtoAnimalExamination.setSensorData("No data");
		dtoAnimalExamination.setStatus("Conceived");
		return dtoAnimalExamination; 
	}

	public DTORegisterAnimal addDTORegisterAnimal(int i) {
		DTORegisterAnimal dtoRegisterAnimal = new DTORegisterAnimal();
		dtoRegisterAnimal.setAboutAnimal("A cow recived from another farm.");
		dtoRegisterAnimal.setAnimalID("AR-1-" + i);
		dtoRegisterAnimal.setBirthPlace("Uppsala");
		dtoRegisterAnimal.setBreed("Swedish Polled");
		dtoRegisterAnimal.setDateOfBirth("2015-10-" + new Random().nextInt(30));
		dtoRegisterAnimal.setEmployerID(employee);
		dtoRegisterAnimal.setNotes("This is Swedish Polled breed cow first came to the farm.");
		dtoRegisterAnimal.setOthers(null);
		dtoRegisterAnimal.setPregnancyExamination(null);
		dtoRegisterAnimal.setPreviousAnimalID("AH-0-0");
		dtoRegisterAnimal.setReceivedFarmID("FF-20-20");
		dtoRegisterAnimal.setReceivedFarmName("Uppsala gord");
		dtoRegisterAnimal.setRegistrationDate("2023-11-" + new Random().nextInt(30));
		dtoRegisterAnimal.setSex("Cow");
		dtoRegisterAnimal.setStatus("Active");
		dtoRegisterAnimal.setUnit("kg");
		dtoRegisterAnimal.setWeight(42);
		if (i > 3) {
			dtoRegisterAnimal.setAnimalIDMother("AR-1-" + i);
		} else {
			dtoRegisterAnimal.setPreviousAnimalID("AH-0-0");
		}
		return dtoRegisterAnimal; 

	}

	public DTOFeedPattern addDTOFeedPattern(int i) {
		DTOFeedPattern dtoFeedPattern = new DTOFeedPattern();
		dtoFeedPattern.setCertiOfIngredients("Organic Cert");
		dtoFeedPattern.setCreationDate("2023-10-23");
		dtoFeedPattern.setFeedName("OrganicStuff");
		dtoFeedPattern.setFeedType("Eco");
		dtoFeedPattern.setFoodSource("Grains");
		dtoFeedPattern.setFpid("FP-00-" + 1);
		dtoFeedPattern.setNotes("No notes");
		dtoFeedPattern.setPercentage("100");
		dtoFeedPattern.setPricae("500");
		dtoFeedPattern.setVolume(150);
		return dtoFeedPattern;
	}

	public DTOAssignFeed  addDTOAssignFeed(int i, String fpid, String animalID) {
		DTOAssignFeed dtoAssignFeed = new DTOAssignFeed();
		dtoAssignFeed.setAfid("AF-06-" + i);
		dtoAssignFeed.setAssignedBy(employee);
		dtoAssignFeed.setAssignedDate("2013-10-25");
		dtoAssignFeed.setAssignedTo(animalID);
		dtoAssignFeed.setExpectedFinishDate("2023-11-" + new Random().nextInt(5));
		dtoAssignFeed.setFpid(fpid);
		return dtoAssignFeed;
	}

	public DTOGeneralHealthExamination addDTOGeneralHealthExamination(String animalid, int i) {
		DTOGeneralHealthExamination dtoGeneralHealthExamination = new DTOGeneralHealthExamination();
		dtoGeneralHealthExamination.setAnimalID(animalid);
		dtoGeneralHealthExamination.setAssignedStatusId(animalid);
		dtoGeneralHealthExamination.setGaheid("GAHE-34-" + i);
		return dtoGeneralHealthExamination;
	}

	public DTOGeneralHealthObservation DTOGeneralHealthObservation(int i, String animalid) {
		DTOGeneralHealthObservation dtoGeneralHealthObservation = new DTOGeneralHealthObservation();
		dtoGeneralHealthObservation.setAnimalID(animalid);
		dtoGeneralHealthObservation.setBcs("5-4-5");
		dtoGeneralHealthObservation.setGheDate("2023-11-" + i);
		dtoGeneralHealthObservation.setGhoid("GHO-99-" + i);
		dtoGeneralHealthObservation.setLimping("No Limping");
		dtoGeneralHealthObservation.setNotes("None");
		dtoGeneralHealthObservation.setObserver(employee);
		dtoGeneralHealthObservation.setSwelling("No swelling");
		dtoGeneralHealthObservation.setWeight("" + (60 + (i * 5)));
		dtoGeneralHealthObservation.setWound("No wound");
		return dtoGeneralHealthObservation ;
	}

	public DTODrugsTreatments DTODrugsTreatments(int i, String animalID, String refNumber, String refType) {
		DTODrugsTreatments dtoDrugsTreatments = new DTODrugsTreatments();
		dtoDrugsTreatments.setAnimalID(animalID);
		dtoDrugsTreatments.setAssignedDate("2023-11-17");
		dtoDrugsTreatments.setDrungs("Alvadon, Antibiotic");
		dtoDrugsTreatments.setDtid("DT-56-" + i);
		dtoDrugsTreatments.setExaminedBy(employee);
		dtoDrugsTreatments.setReason("Temperature");
		dtoDrugsTreatments.setRefnumber(refNumber);
		dtoDrugsTreatments.setReftype(refType);
		return dtoDrugsTreatments;
	}

	public DTOTemporaryMovement addDTOTemporaryMovement(int i, StringList animals) {
		DTOTemporaryMovement dtoTemporaryMovement = new DTOTemporaryMovement();
		dtoTemporaryMovement.setEmployeeID(employee);
		dtoTemporaryMovement.setInDate("2023-10-15");
		dtoTemporaryMovement.setNotes(employee);
		dtoTemporaryMovement.setOutDate("2023-10-10");
		dtoTemporaryMovement.setPurpose("Grazing");
		dtoTemporaryMovement.setTmid("TM-90-" + i);
		dtoTemporaryMovement.setTmType(employee);
		dtoTemporaryMovement.setAnimals(animals);
		return dtoTemporaryMovement ;
	}

	/*
	 * public DTOTemporaryMovementGroup addDTOTemporaryMovementGroup(int i, String
	 * tmid) { DTOTemporaryMovementGroup dtoTemporaryMovementGroup = new
	 * DTOTemporaryMovementGroup(); dtoTemporaryMovementGroup.setTmgid(tmid);
	 * dtoTemporaryMovementGroup.setEmployeeID(employee);
	 * dtoTemporaryMovementGroup.setInDate("2023-10-15");
	 * dtoTemporaryMovementGroup.setOutDate("2023-10-10");
	 * dtoTemporaryMovementGroup.setTmgid("TMG-34-" + i); return
	 * dtoTemporaryMovementGroup; }
	 */

}
