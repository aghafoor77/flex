package ri.se.farm.dto;

import java.util.Date;

public class DTOFacility{

	private String facilityNr;
	private String areasize;
	private String fromDate;
	private String movementacrossEU;
	private String address;
	private String maxcapacity;
	private String temporaryactivity;
	private String anlaggningsnumber;
	private String specieskept;
	private String farmId;
	private String type;
	private String breedingmaterials;
	public DTOFacility (){

	}
	public DTOFacility ( String facilityNr,String areasize,String fromDate,String movementacrossEU,String address,String maxcapacity,String temporaryactivity,String anlaggningsnumber,String specieskept,String farmId,String type,String breedingmaterials){
		this.facilityNr = facilityNr; 
		this.areasize = areasize; 
		this.fromDate = fromDate; 
		this.movementacrossEU = movementacrossEU; 
		this.address = address; 
		this.maxcapacity = maxcapacity; 
		this.temporaryactivity = temporaryactivity; 
		this.anlaggningsnumber = anlaggningsnumber; 
		this.specieskept = specieskept; 
		this.farmId = farmId; 
		this.type = type; 
		this.breedingmaterials = breedingmaterials; 
	}

	public String getFacilityNr(){
		return this.facilityNr;
	}
	public void setFacilityNr(String facilityNr) {
		this.facilityNr = facilityNr; 
	}
	public String getAreasize(){
		return this.areasize;
	}
	public void setAreasize(String areasize) {
		this.areasize = areasize; 
	}
	public String getFromDate(){
		return this.fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate; 
	}
	public String getMovementacrossEU(){
		return this.movementacrossEU;
	}
	public void setMovementacrossEU(String movementacrossEU) {
		this.movementacrossEU = movementacrossEU; 
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address; 
	}
	public String getMaxcapacity(){
		return this.maxcapacity;
	}
	public void setMaxcapacity(String maxcapacity) {
		this.maxcapacity = maxcapacity; 
	}
	public String getTemporaryactivity(){
		return this.temporaryactivity;
	}
	public void setTemporaryactivity(String temporaryactivity) {
		this.temporaryactivity = temporaryactivity; 
	}
	public String getAnlaggningsnumber(){
		return this.anlaggningsnumber;
	}
	public void setAnlaggningsnumber(String anlaggningsnumber) {
		this.anlaggningsnumber = anlaggningsnumber; 
	}
	public String getSpecieskept(){
		return this.specieskept;
	}
	public void setSpecieskept(String specieskept) {
		this.specieskept = specieskept; 
	}
	public String getFarmId(){
		return this.farmId;
	}
	public void setFarmId(String farmId) {
		this.farmId = farmId; 
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type) {
		this.type = type; 
	}
	public String getBreedingmaterials(){
		return this.breedingmaterials;
	}
	public void setBreedingmaterials(String breedingmaterials) {
		this.breedingmaterials = breedingmaterials; 
	}
}
