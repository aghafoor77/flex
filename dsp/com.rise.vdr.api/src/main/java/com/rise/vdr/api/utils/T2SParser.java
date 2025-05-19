package com.rise.vdr.api.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.EvidenceData;
import com.ri.se.commonentities.transporter.TransferedAnimal;
import com.ri.se.commonentities.transporter.TransportedToSlaughter;



public class T2SParser {

	public static void main(String a[]) {
		new T2SParser().parser(new EvidenceData ());
	}
	public TransportedToSlaughter parser(EvidenceData ed) {
		//ed.setContents("eyJjYXJyaWVyU2xhdWdodGVyaG91c2UiOnsidHJpcE5vIjoiVFItMTczNzI1NTIzNjc3NyIsInNsYXVnaHRlcmhvdXNlbmFtZSI6ImFzYXMiLCJjdXJyZW50U3RhdHVzIjoiU0xBR1VURVItQk9VTkQiLCJleHBlY3RlZEFycml2YWxEYXRlIjoxNzA1MDE3NjAwMDAwLCJsb2NhdGlvbiI6ImFzYXNhcyIsInN0YXJ0RGF0ZSI6MTcwNTAxNzYwMDAwMH0sImNhcnJpZXIiOnsidHJpcE5vIjoiVFItMTczNzI1NTIzNjc3NyIsImNhcnJpZXJOdW1iZXIiOiJHWk04ODYiLCJjYXJyaWVySWQiOiJTSEMtMTczNzI1NTIzNjgwMCIsImNpZCI6IkNJRDE3MzcyMzIxNDg0NDYifSwidHJhbnNmZXJlZEFuaW1hbCI6eyJhbmltYWxJRCI6IkFSLTE3MzcyMjgzMzE0MjEiLCJhbmltYWxJRE1vdGhlciI6Im51bGwiLCJiaXJ0aFBsYWNlIjoiVXBwc2FsYSBGYXJtIiwicHJldmlvdXNGYXJtQ29udGFjdCI6IkFSLTEyLTEyLTEySU1QIiwiY3VycmVudFdlaWdodCI6MjAwLCJyZWNlaXZlZEZhcm1OYW1lIjoiU3VuZHN2YWwiLCJzZXgiOiJDb3ciLCJmYXJtSWQiOiJBUi0xNzM3MjI4MzMxNDIxIiwidHJhbnNmZXJEYXRlIjoxNzM3MTg4MTI0MDAwLCJiaXJ0aERhdGUiOjE2Mjg1NDY0MDAwMDAsInRyYW5zYWN0aW9uSUQiOiJUMTczNzIzMTMyNTMzMiIsInRyYW5zYWN0aW9uVGltZSI6MTczNzIzMTMyNTMzMiwiYnJlZWQiOiJHZXJtYW4gUmVkIFBpZWQiLCJjdXJyZW50U3RhdHVzIjoiVFJBTlNGRVJFRCJ9LCJ0byI6IjM1Njg1Mzc5NjMyYTZjNTQ1YTY3NDQ0YjNiNDczOTcyN2U1NzU0Mzk2NDQ4NTU0OTc4NDQzNDczNDQzYiIsImZyb20iOiI2YzcxNmI2NzZhNmY0ODQ0M2I1ZjZkNzQ3ZTUzNjI2ZDRjNzQ1NDMzNGE1OTQ4Njc0OTcyMzI2MjMyNGEifQ==");
		
		TransportedToSlaughter transportedToSlaughter = new TransportedToSlaughter();
		try {
			String temp = new String(Base64.getDecoder().decode(ed.getContents().toString()));
			System.out.println(temp);
			
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> temp2 = new ObjectMapper().readValue(temp, Map.class);
			Object obj1 = temp2 .get("carrierSlaughterhouse");
			Object obj2 =temp2 .get("carrier");
			Object obj3 =temp2 .get("transferedAnimal");
			
			com.ri.se.commonentities.transporter.CarrierSlaughterhouse tempy = new ObjectMapper().readValue(mapper .writeValueAsString(obj1) , com.ri.se.commonentities.transporter.CarrierSlaughterhouse.class);
			com.ri.se.commonentities.transporter.SHCarrier carrier = new ObjectMapper().readValue(mapper .writeValueAsString(obj2) , com.ri.se.commonentities.transporter.SHCarrier.class);
			//com.ri.se.commonentities.transporter.TransferedAnimal transferedAnimal = new ObjectMapper().readValue(mapper .writeValueAsString(obj3) , com.ri.se.commonentities.transporter.TransferedAnimal.class);
			transportedToSlaughter.setCarrier(carrier);
			transportedToSlaughter.setCarrierSlaughterhouse(tempy);
			
			TransferedAnimal transferedAnimal  = new TransferedAnimal();
			Map<String, Object> map = (Map<String, Object>)obj3;
			
			transferedAnimal.setAnimalID(map.get("animalID").toString());
			transferedAnimal.setAnimalIDMother(map.get("animalIDMother").toString());
			String birthdate = map.get("birthDate").toString();
			if(!Objects.isNull(birthdate)) {
				try {
					transferedAnimal.setBirthDate(new Date(birthdate));//new SimpleDateFormat("yyyy-MM-dd").parse(birthdate));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			transferedAnimal.setBirthPlace(Objects.isNull(map.get("birthPlace"))?"-":map.get("birthPlace").toString());
			transferedAnimal.setBreed(Objects.isNull(map.get("breed"))?"-":map.get("breed").toString());
			transferedAnimal.setCurrentStatus(Objects.isNull(map.get("currentStatus"))?"-":map.get("currentStatus").toString());
			int te= Objects.isNull(map.get("currentWeight"))?0:(Integer)map.get("currentWeight");
			transferedAnimal.setCurrentWeight(te);
			transferedAnimal.setFarmId(Objects.isNull(map.get("farmId"))?"-":map.get("farmId").toString());
			transferedAnimal.setPreviousFarmContact(Objects.isNull(map.get("previousFarmContact"))?"-":map.get("previousFarmContact").toString());
			transferedAnimal.setReceivedFarmName(Objects.isNull(map.get("receivedFarmName"))?"-":map.get("receivedFarmName").toString());
			transferedAnimal.setSex(Objects.isNull(map.get("sex"))?"-":map.get("sex").toString());
			transportedToSlaughter.setTo(Objects.isNull(temp2 .get("to"))?"-":temp2 .get("to").toString());
			transportedToSlaughter.setFrom(Objects.isNull(temp2 .get("from"))?"-":temp2 .get("from").toString());			
			
			
			transportedToSlaughter.setTransferedAnimal(transferedAnimal);
			
			System.out.println("");
			return transportedToSlaughter;
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}
}
