package ri.se.farm.dto;

public class DTOFacility{

	private String facilityId;
	private String address;
	private int maxcapacity;
	private String temporaryactivity;
	private String anlaggningsnumber;
	private String farmId;
	private String type;
	private String areasize;
	private String fromDate;
	private boolean movementacrossEU;
	private String facilityNr;
	private String specieskept;
	private String breedingmaterials;
	public DTOFacility (){

	}
	public DTOFacility ( String facilityId,String address,int maxcapacity,String temporaryactivity,String anlaggningsnumber,String farmId,String type,String areasize,String fromDate,boolean movementacrossEU,String facilityNr,String specieskept,String breedingmaterials){
		this.facilityId = facilityId; 
		this.address = address; 
		this.maxcapacity = maxcapacity; 
		this.temporaryactivity = temporaryactivity; 
		this.anlaggningsnumber = anlaggningsnumber; 
		this.farmId = farmId; 
		this.type = type; 
		this.areasize = areasize; 
		this.fromDate = fromDate; 
		this.movementacrossEU = movementacrossEU; 
		this.facilityNr = facilityNr; 
		this.specieskept = specieskept; 
		this.breedingmaterials = breedingmaterials; 
	}

	public String getFacilityId(){
		return this.facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId; 
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address; 
	}
	public int getMaxcapacity(){
		return this.maxcapacity;
	}
	public void setMaxcapacity(int maxcapacity) {
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
	public boolean getMovementacrossEU(){
		return this.movementacrossEU;
	}
	public void setMovementacrossEU(boolean movementacrossEU) {
		this.movementacrossEU = movementacrossEU; 
	}
	public String getFacilityNr(){
		return this.facilityNr;
	}
	public void setFacilityNr(String facilityNr) {
		this.facilityNr = facilityNr; 
	}
	public String getSpecieskept(){
		return this.specieskept;
	}
	public void setSpecieskept(String specieskept) {
		this.specieskept = specieskept; 
	}
	public String getBreedingmaterials(){
		return this.breedingmaterials;
	}
	public void setBreedingmaterials(String breedingmaterials) {
		this.breedingmaterials = breedingmaterials; 
	}
}
