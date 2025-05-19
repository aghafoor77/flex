package transporter.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "Transporter")
@XmlRootElement
public class Transporter{
	@Id
	@Column(name = "TID")
	private String TID;
	private String notes;
	private String animalPeryear;
	private String ownerId;
	private boolean isOrganization;
	public Transporter (){

	}
	public Transporter ( String TID,String notes,String animalPeryear,String ownerId,boolean isOrganization){
		this.TID = TID; 
		this.notes = notes; 
		this.animalPeryear = animalPeryear; 
		this.ownerId = ownerId; 
		this.isOrganization = isOrganization; 
	}

	public String getTID(){
		return this.TID;
	}
	public void setTID(String TID) {
		this.TID = TID; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getAnimalPeryear(){
		return this.animalPeryear;
	}
	public void setAnimalPeryear(String animalPeryear) {
		this.animalPeryear = animalPeryear; 
	}
	public String getOwnerId(){
		return this.ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId; 
	}
	public boolean getIsOrganization(){
		return this.isOrganization;
	}
	public void setIsOrganization(boolean isOrganization) {
		this.isOrganization = isOrganization; 
	}
}
