package com.ri.se.generalhealthobservation.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "GeneralHealthObservation")
@XmlRootElement
public class GeneralHealthObservation{
	@Id
	@Column(name = "ghoid")
	private String ghoid;
	private String limping;
	private String observer;
	private String wound;
	private String bcs;
	private String notes;
	private String swelling;
	private Date gheDate;
	private String animalID;
	private String weight;
	public GeneralHealthObservation (){

	}
	public GeneralHealthObservation ( String ghoid,String limping,String observer,String wound,String bcs,String notes,String swelling,Date gheDate,String animalID,String weight){
		this.ghoid = ghoid; 
		this.limping = limping; 
		this.observer = observer; 
		this.wound = wound; 
		this.bcs = bcs; 
		this.notes = notes; 
		this.swelling = swelling; 
		this.gheDate = gheDate; 
		this.animalID = animalID; 
		this.weight = weight; 
	}

	public String getGhoid(){
		return this.ghoid;
	}
	public void setGhoid(String ghoid) {
		this.ghoid = ghoid; 
	}
	public String getLimping(){
		return this.limping;
	}
	public void setLimping(String limping) {
		this.limping = limping; 
	}
	public String getObserver(){
		return this.observer;
	}
	public void setObserver(String observer) {
		this.observer = observer; 
	}
	public String getWound(){
		return this.wound;
	}
	public void setWound(String wound) {
		this.wound = wound; 
	}
	public String getBcs(){
		return this.bcs;
	}
	public void setBcs(String bcs) {
		this.bcs = bcs; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getSwelling(){
		return this.swelling;
	}
	public void setSwelling(String swelling) {
		this.swelling = swelling; 
	}
	public Date getGheDate(){
		return this.gheDate;
	}
	public void setGheDate(Date gheDate) {
		this.gheDate = gheDate; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
	public String getWeight(){
		return this.weight;
	}
	public void setWeight(String weight) {
		this.weight = weight; 
	}
}
