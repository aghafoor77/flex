package com.ri.se.resources.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "Farm")
@XmlRootElement
public class Farm{
	@Id
	@Column(name = "farmId")
	private String farmId;
	private String farmName;
	private String owner;
	private String dateTime;
	private String country;
	private String notes;
	private String city;
	private String street;
	private String postalcode;
	private String farmContactNo;
	private String farmCompany;
	private String email;
	public Farm (){

	}
	public Farm ( String farmId,String farmName,String owner,String dateTime,String country,String notes,String city,String street,String postalcode,String farmContactNo,String farmCompany,String email){
		this.farmId = farmId; 
		this.farmName = farmName; 
		this.owner = owner; 
		this.dateTime = dateTime; 
		this.country = country; 
		this.notes = notes; 
		this.city = city; 
		this.street = street; 
		this.postalcode = postalcode; 
		this.farmContactNo = farmContactNo; 
		this.farmCompany = farmCompany; 
		this.email = email; 
	}

	public String getFarmId(){
		return this.farmId;
	}
	public void setFarmId(String farmId) {
		this.farmId = farmId; 
	}
	public String getFarmName(){
		return this.farmName;
	}
	public void setFarmName(String farmName) {
		this.farmName = farmName; 
	}
	public String getOwner(){
		return this.owner;
	}
	public void setOwner(String owner) {
		this.owner = owner; 
	}
	public String getDateTime(){
		return this.dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime; 
	}
	public String getCountry(){
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getCity(){
		return this.city;
	}
	public void setCity(String city) {
		this.city = city; 
	}
	public String getStreet(){
		return this.street;
	}
	public void setStreet(String street) {
		this.street = street; 
	}
	public String getPostalcode(){
		return this.postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode; 
	}
	public String getFarmContactNo(){
		return this.farmContactNo;
	}
	public void setFarmContactNo(String farmContactNo) {
		this.farmContactNo = farmContactNo; 
	}
	public String getFarmCompany(){
		return this.farmCompany;
	}
	public void setFarmCompany(String farmCompany) {
		this.farmCompany = farmCompany; 
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email; 
	}
}
