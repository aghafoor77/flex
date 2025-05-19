package com.rise.se.slaughterhouse.downloader;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.EvidenceData;
import com.ri.se.commonentities.transporter.TransportedToSlaughter;
import com.rise.se.slaughterhouse.persistance.RAnimalSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RCarrierSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RSlaughterhouseData;



public class T2SParser {

	public static void main(String a[]) {
		//new T2SParser().parser(new EvidenceData ());
	}
	
	public RSlaughterhouseData parserRSlaughterhouseData(EvidenceData ed) {
		try {
			String temp = new String(Base64.getDecoder().decode(ed.getContents().toString()));
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> temp2 = new ObjectMapper().readValue(temp, Map.class);
			Object obj1 = temp2 .get("carrierSlaughterhouse");
			return new ObjectMapper().readValue(toJson(obj1) , RSlaughterhouseData.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}
	public RCarrierSlaughterhouseData parserRCarrierSlaughterhouseData(EvidenceData ed) {
		TransportedToSlaughter transportedToSlaughter = new TransportedToSlaughter();
		try {
			String temp = new String(Base64.getDecoder().decode(ed.getContents().toString()));
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> temp2 = new ObjectMapper().readValue(temp, Map.class);
			Object obj2 =temp2 .get("carrier");
			return new ObjectMapper().readValue(toJson(obj2) , RCarrierSlaughterhouseData.class);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}
	public RAnimalSlaughterhouseData parserRAnimalSlaughterhouseData(EvidenceData ed) {
		TransportedToSlaughter transportedToSlaughter = new TransportedToSlaughter();
		try {
			String temp = new String(Base64.getDecoder().decode(ed.getContents().toString()));
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> temp2 = mapper.readValue(temp, Map.class);
			Object obj3 =temp2 .get("transferedAnimal");
			RAnimalSlaughterhouseData transferedAnimal  = new RAnimalSlaughterhouseData();
			Map<String, Object> map = (Map<String, Object>) new ObjectMapper().readValue(toJson(obj3), Map.class);
			transferedAnimal.setAnimalID(map.get("animalID").toString());
			transferedAnimal.setAnimalIDMother(map.get("animalIDMother").toString());
			transferedAnimal.setBirthDate(null);
			transferedAnimal.setBirthPlace(map.get("birthPlace").toString());
			transferedAnimal.setBreed(map.get("breed").toString());
			transferedAnimal.setCurrentStatus(map.get("currentStatus").toString());
			String temp_ = ""+map.get("currentWeight");
			int te= Integer.parseInt(temp_);
			transferedAnimal.setCurrentWeight(te);
			transferedAnimal.setFarmId(map.get("farmId").toString());
			transferedAnimal.setPreviousFarmContact(map.get("previousFarmContact").toString());
			transferedAnimal.setReceivedFarmName(map.get("receivedFarmName").toString());
			transferedAnimal.setSex(map.get("sex").toString());
			
			return transferedAnimal;			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	public String toJson(Object obj1) {
		
		Map<String, Object> claims = (Map<String, Object>)obj1;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		claims.forEach((key, value) -> {
			if(value instanceof Map) {
				Map<String, Object> valueSalted = (Map<String, Object>)value;
				stringBuffer.append("\""+ valueSalted.get("name")+"\" : ");
				stringBuffer.append("\""+ valueSalted.get("value")+"\", ");
			}					
		});
		String contents = stringBuffer.toString();
		contents = contents.substring(0, contents .length()-2);
		contents =contents +"}";
		System.out.println(contents);
		return contents ;
		
	}
}
