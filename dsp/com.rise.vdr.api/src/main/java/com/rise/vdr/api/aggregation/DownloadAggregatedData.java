package com.rise.vdr.api.aggregation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.DTOAnimalExamination;
import com.ri.se.commonentities.DTOAnimalExaminationList;
import com.ri.se.commonentities.DTOAssignFeed;
import com.ri.se.commonentities.DTOAssignFeedList;
import com.ri.se.commonentities.DTODrugsTreatments;
import com.ri.se.commonentities.DTODrugsTreatmentsList;
import com.ri.se.commonentities.DTOFarmList;
import com.ri.se.commonentities.DTOFeedPattern;
import com.ri.se.commonentities.DTOGeneralHealthExamination;
import com.ri.se.commonentities.DTOGeneralHealthExaminationList;
import com.ri.se.commonentities.DTOGeneralHealthObservation;
import com.ri.se.commonentities.DTOGeneralHealthObservationList;
import com.ri.se.commonentities.DTOOrderSemen;
import com.ri.se.commonentities.DTORegisterAnimal;
import com.ri.se.commonentities.DTOResponseOrderSemen;
import com.ri.se.commonentities.DTOSemenUtilization;
import com.ri.se.commonentities.DTOSemenUtilizationList;
import com.ri.se.commonentities.DTOTemporaryMovement;
import com.ri.se.commonentities.DTOTemporaryMovementGroup;
import com.ri.se.commonentities.DTOTemporaryMovementGroupList;
import com.rise.vdr.api.transaction.EvidenceContainer;
import com.rise.vdr.api.transaction.EvidenceObject;
import com.rise.vdr.api.utils.DTOTransferedAnimal;

public class DownloadAggregatedData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String ip = "http://localhost";
			String animalID = "AR-34-567-37";
			new DownloadAggregatedData().fetchRecord(ip, animalID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public EvidenceContainer fetchRecord(String ip, String animalID) throws Exception {
		DTOTransferedAnimal dtoTransferedAnimal = new DTOTransferedAnimal();
		EvidenceContainer ec = new EvidenceContainer();
		{
			int port = 9020;
			String resource = "/v1/farm" ;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTOFarmList dtoFarmList = new ObjectMapper().readValue(jsonStr, DTOFarmList.class);
				if(Objects.isNull(dtoFarmList) || dtoFarmList.size() == 0) {
					dtoTransferedAnimal.setFarmId(dtoFarmList.get(0).getFarmId());
				}
			}
		}
		{
			int port = 9007;
			String resource = "/v1/ordersemen/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTOOrderSemen dtoOrderSemen = new ObjectMapper().readValue(jsonStr, DTOOrderSemen.class);
				EvidenceObject temp = new EvidenceObject();
				temp.setEvidence(dtoOrderSemen);
				temp.setEvidenceType("OrderSemen");
				temp.setUniqueNo(dtoOrderSemen.getOsid());
				ec.add(temp);
			}
		}
		// dtoOrderSemen
		{

			int port = 9007;
			String resource = "/v1/responseordersemen/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTOResponseOrderSemen obj = new ObjectMapper().readValue(jsonStr, DTOResponseOrderSemen.class);
				EvidenceObject temp = new EvidenceObject();
				temp.setEvidence(obj);
				temp.setEvidenceType("ResponseOrderSemen");
				temp.setUniqueNo(obj.getOsid());
				ec.add(temp);
			}
			// dtoResponseOrderSemen
		}
		{
			// DTOSemenUtilization dtoSemenUtilization1 = new DTOSemenUtilization();
			int port = 9007;
			String resource = "/v1/semenutilization/animal/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTOSemenUtilizationList objList = new ObjectMapper().readValue(jsonStr, DTOSemenUtilizationList.class);
				for(DTOSemenUtilization obj:objList) {
				EvidenceObject temp = new EvidenceObject();
				temp.setEvidence(obj);
				temp.setEvidenceType("SemenUtilization");
				temp.setUniqueNo(obj.getSuid());
				ec.add(temp);
				}
			}
			// dtoSemenUtilization1
		}
		{
			// DTOAnimalExamination dtoAnimalExaminationMother10 = new
			// DTOAnimalExamination();
			int port = 9011;
			String resource = "/v1/animalexamination/animal/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTOAnimalExaminationList objList = new ObjectMapper().readValue(jsonStr,
						DTOAnimalExaminationList.class);
				for (DTOAnimalExamination obj : objList) {
					EvidenceObject temp = new EvidenceObject();
					temp.setEvidence(obj);
					temp.setEvidenceType("AnimalExamination");
					temp.setUniqueNo(obj.getAeid());
					ec.add(temp);
				}
			}
			// dtoAnimalExaminationMother10

			// dtoAnimalExaminationMother11
		}
		{
			DTORegisterAnimal dtoRegisterAnimal1 = new DTORegisterAnimal();
			int port = 9002;
			String resource = "/v1/registeranimal/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTORegisterAnimal obj = new ObjectMapper().readValue(jsonStr, DTORegisterAnimal.class);
				EvidenceObject temp = new EvidenceObject();
				temp.setEvidence(obj);
				temp.setEvidenceType("RegisterAnimal");
				temp.setUniqueNo(obj.getAnimalID());
				ec.add(temp);
				dtoTransferedAnimal.setAnimalID(obj.getAnimalID());
				dtoTransferedAnimal.setAnimalIDMother(obj.getAnimalIDMother());
				dtoTransferedAnimal.setBirthDate(obj.getDateOfBirth());
				dtoTransferedAnimal.setBirthPlace(obj.getBirthPlace());
				dtoTransferedAnimal.setBreed(obj.getBreed());
				dtoTransferedAnimal.setPreviousFarmContact(obj.getPreviousAnimalID());
				dtoTransferedAnimal.setReceivedFarmName(obj.getReceivedFarmName());
				dtoTransferedAnimal.setSex(obj.getSex());
				dtoTransferedAnimal.setTransferDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
				dtoTransferedAnimal.setFarmId(animalID);
				dtoTransferedAnimal.setCurrentWeight(200);
				
				EvidenceObject data = new EvidenceObject();
				data.setEvidence(dtoTransferedAnimal);
				data.setEvidenceType("DATA");
				data.setUniqueNo("DATA");
				ec.add(data);
				
				
				
				
			}
			// dtoRegisterAnimal1

			// May be good for animal health
		}
		{

			int port = 9011;
			String resource = "/v1/generalhealthexamination/animal/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTOGeneralHealthExaminationList objList = new ObjectMapper().readValue(jsonStr,
						DTOGeneralHealthExaminationList.class);
				for (DTOGeneralHealthExamination obj : objList) {
					EvidenceObject temp = new EvidenceObject();
					temp.setEvidence(obj);
					temp.setEvidenceType("GeneralHealthExamination");
					temp.setUniqueNo(obj.getGaheid());
					ec.add(temp);
				}
			}
			// dtoGeneralHealthExaminationNB10
			//////////////////////////
		}
		{
			// String obsID = "HR-73-982-29/" + animalID;
			DTOGeneralHealthObservation dtoGeneralHealthObservation10 = new DTOGeneralHealthObservation();
			int port = 9011;
			String resource = "/v1/generalhealthobservation/animal/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTOGeneralHealthObservationList objList = new ObjectMapper().readValue(jsonStr,
						DTOGeneralHealthObservationList.class);
				for (DTOGeneralHealthObservation obj : objList) {
					EvidenceObject temp = new EvidenceObject();
					temp.setEvidence(obj);
					temp.setEvidenceType("GeneralHealthObservation");
					temp.setUniqueNo(obj.getGhoid());
					ec.add(temp);
				}
			}
		}
		// dtoGeneralHealthObservation10
		// dtoTemporaryMovement2
		{
			DTOTemporaryMovement dtoTemporaryMovementGroup3 = new DTOTemporaryMovement();
			int port = 9013;
			String resource = "/v1/temporarymovementgroup/animal/" + animalID;
			String tmJSON = downloader(ip, port, resource);
			if (!Objects.isNull(tmJSON)) {
				// COnvert o list group and get tm against each entry
				DTOTemporaryMovementGroupList dtoTemporaryMovementGroupList = new ObjectMapper().readValue(tmJSON,
						DTOTemporaryMovementGroupList.class);
				for (DTOTemporaryMovementGroup dtg : dtoTemporaryMovementGroupList) {
					port = 9013;
					resource = "/v1/temporarymovement/" + dtg.getTmid();
					String jsonStr = downloader(ip, port, resource);
					if (!Objects.isNull(jsonStr)) {
						DTOTemporaryMovement obj = new ObjectMapper().readValue(jsonStr, DTOTemporaryMovement.class);
						EvidenceObject temp = new EvidenceObject();
						temp.setUniqueNo(obj.getTmid());
						TM tm = new TM();
						tm.setTm(obj);
						tm.setTmg(dtg);
						temp.setEvidence(tm);
						temp.setEvidenceType("TemporaryMovement");
						ec.add(temp);
					}
				}
				// dtoTemporaryMovement3
			}
		}

		{

			int port = 9011;
			String resource = "/v1/drugstreatments/animal/" + animalID;
			String jsonStr = downloader(ip, port, resource);
			if (!Objects.isNull(jsonStr)) {
				DTODrugsTreatmentsList objList = new ObjectMapper().readValue(jsonStr, DTODrugsTreatmentsList.class);
				for (DTODrugsTreatments obj : objList) {
					EvidenceObject temp = new EvidenceObject();
					temp.setEvidence(obj);
					temp.setEvidenceType("DrugsTreatments");
					temp.setUniqueNo(obj.getDtid());
					ec.add(temp);
				}
			}
			// dtoDrugsTreatments
		}

		{

			DTOAssignFeed dtoAssignFeed1 = new DTOAssignFeed();
			int port = 9013;
			String resource = "/v1/assignfeed/animal/" + animalID;// +animalID;
			String tmJSON = downloader(ip, port, resource);
			// dtoAssignFeed1
			if (!Objects.isNull(tmJSON)) {
				// COnvert o list group and get tm against each entry
				DTOAssignFeedList dtoAssignFeedList = new ObjectMapper().readValue(tmJSON, DTOAssignFeedList.class);
				for (DTOAssignFeed af : dtoAssignFeedList) {
					port = 9013;
					resource = "/v1/feedpattern/" + af.getFpid();
					String jsonStr = downloader(ip, port, resource);
					if (!Objects.isNull(jsonStr)) {
						DTOFeedPattern obj = new ObjectMapper().readValue(jsonStr, DTOFeedPattern.class);
						AF af1 = new AF();
						af1.setAf(af);
						af1.setFp(obj);
						EvidenceObject temp = new EvidenceObject();
						temp.setEvidence(af1);
						temp.setEvidenceType("FEED");
						temp.setUniqueNo(obj.getFpid());
						ec.add(temp);
					}
				}
				// dtoTemporaryMovement3
			}
			// dtoFeedPattern
		}
		
		return ec;
		// dtoAssignFeed2
	}

	public String downloader(String ip, int port, String resource) {
		try {
			RestClient rc = RestClient.builder().baseUrl(ip).build();
			Representation rep = rc.get(":" + port + "/application" + resource, null);
			//System.out.println(rep.getBody().toString());
			if(rep.getCode() !=200) {
				return null;
			}
			return rep.getBody().toString();
		} catch (Exception exp) {
			System.out.println("==========> No Data <==========" + exp.getMessage());
			return null;
		}
	}
	
	public String updateAnimalStatus(String ip, int port, String resource) {
		try {
			RestClient rc = RestClient.builder().baseUrl(ip).build();
			Representation rep = rc.put(":" + port + "/application" + resource,"TRANSFERED", null);
			System.out.println(rep.getBody().toString());
			if(rep.getCode() !=200) {
				return null;
			}
			return rep.getBody().toString();
		} catch (Exception exp) {
			System.out.println("==========> No Data <==========" + exp.getMessage());
			return null;
		}
	}
	
	
}
