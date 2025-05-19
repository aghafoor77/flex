package ri.se.farm.dto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ri.se.farm.persistance.FacilityList;
import ri.se.farm.persistance.Farm;

public class Test {

	public static void main(String[] args) throws Exception {
		
		
		// TODO Auto-generated method stub
		DTOFacilityList dtoFacilityList = new DTOFacilityList();
		DTOFacility dtoFacility = new DTOFacility();
		dtoFacility.setAddress("1. enter address");
		dtoFacility.setAnlaggningsnumber("1. enter anlaggningsnumber");
		dtoFacility .setAreasize("1. enter areasize");
		dtoFacility.setBreedingmaterials("1. enter breedingmaterials");
		dtoFacility.setFacilityNr("1. enter facilityNr"); 
		dtoFacility.setFarmId("1. enter farmId");
		dtoFacility.setFromDate("2022-1-15");
		dtoFacility.setMaxcapacity(5);
		dtoFacility.setMovementacrossEU(true);
		dtoFacility.setSpecieskept("1. enter specieskept");
		dtoFacility.setTemporaryactivity("1. enter temporaryactivity");
		dtoFacility.setType("1. enter type");
		
		dtoFacilityList.add(dtoFacility); 
		
		DTOFacility dtoFacility1 = new DTOFacility();
		dtoFacility1.setAddress("2.enter address");
		dtoFacility1.setAnlaggningsnumber("2.enter anlaggningsnumber");
		dtoFacility1 .setAreasize("2.enter areasize");
		dtoFacility1.setBreedingmaterials("2.enter breedingmaterials");
		dtoFacility1.setFacilityNr("2.enter facilityNr"); 
		dtoFacility1.setFarmId("2.enter farmId");
		dtoFacility1.setFromDate("2022-1-15");
		dtoFacility1.setMaxcapacity(2);
		dtoFacility1.setMovementacrossEU(true);
		dtoFacility1.setSpecieskept("2.enter specieskept");
		dtoFacility1.setTemporaryactivity("2.enter temporaryactivity");
		dtoFacility1.setType("2.enter type");
		
		dtoFacilityList.add(dtoFacility1); 
		
		DTOFarm dtoFarm = new DTOFarm();
		dtoFarm.setCity("city"); 
		dtoFarm.setCountry("country");
		dtoFarm .setDateTime("2022-1-15");
		dtoFarm .setEmail("email");
		dtoFarm.setFarmCompany("farmCompany");
		dtoFarm .setFarmContactNo("farmContactNo");
		dtoFarm.setFarmId("farmId");
		dtoFarm.setFarmName("farmName");
		dtoFarm .setNotes("notes");
		dtoFarm .setOwner("owner");
		dtoFarm .setPostalcode("postalcode");
		dtoFarm.setStreet("street");
		FarmFacility farmFacility = new FarmFacility();
		farmFacility.setDtoFacilityList(dtoFacilityList);
		farmFacility .setFarm(dtoFarm);		
		System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(farmFacility));
		String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(farmFacility);
		 FarmFacility farmFacility_ = new ObjectMapper().readValue(json, FarmFacility.class);
	        System.out.println(farmFacility_.getFarm().getCity()); 
	        FacilityEntityConverter facilityEntityConverter = new FacilityEntityConverter();
	        
	        DTOFacilityList farmfList = farmFacility_.getDtoFacilityList();
	        
	        FacilityList facilityList = new FacilityList();
	        
	        for(DTOFacility dtoFacility_ : farmfList) {
	        	facilityList.add(facilityEntityConverter.getFacility(dtoFacility_));
	        }        
	        Farm farm = new FarmEntityConverter().getFarm(farmFacility_.getFarm());  
		
	}
	
}
