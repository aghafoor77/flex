package carrier.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "Carrier")
@XmlRootElement
public class Carrier{
	@Id
	@Column(name = "CID")
	private String CID;
	private String carrierNumber;
	private String notes;
	private String species;
	private String transportType;
	private String TID;
	private boolean longDistance;
	public Carrier (){

	}
	public Carrier ( String CID,String carrierNumber,String notes,String species,String transportType,String TID,boolean longDistance){
		this.CID = CID; 
		this.carrierNumber = carrierNumber; 
		this.notes = notes; 
		this.species = species; 
		this.transportType = transportType; 
		this.TID = TID; 
		this.longDistance = longDistance; 
	}

	public String getCID(){
		return this.CID;
	}
	public void setCID(String CID) {
		this.CID = CID; 
	}
	public String getCarrierNumber(){
		return this.carrierNumber;
	}
	public void setCarrierNumber(String carrierNumber) {
		this.carrierNumber = carrierNumber; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getSpecies(){
		return this.species;
	}
	public void setSpecies(String species) {
		this.species = species; 
	}
	public String getTransportType(){
		return this.transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType; 
	}
	public String getTID(){
		return this.TID;
	}
	public void setTID(String TID) {
		this.TID = TID; 
	}
	public boolean getLongDistance(){
		return this.longDistance;
	}
	public void setLongDistance(boolean longDistance) {
		this.longDistance = longDistance; 
	}
}
