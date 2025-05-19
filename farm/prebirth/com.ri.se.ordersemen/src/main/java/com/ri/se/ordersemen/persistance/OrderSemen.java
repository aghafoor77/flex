package com.ri.se.ordersemen.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "OrderSemen")
@XmlRootElement
public class OrderSemen{
	@Id
	@Column(name = "osid")
	private String osid;
	private String notes;
	private String contact;
	private String orderedTo;
	private String employeeID;
	private String emailto;
	private String farmID;
	private Date orderDate;
	private String breed;
	private String status;
	public OrderSemen (){

	}
	public OrderSemen ( String osid,String notes,String contact,String orderedTo,String employeeID,String emailto,String farmID,Date orderDate,String breed,String status){
		this.osid = osid; 
		this.notes = notes; 
		this.contact = contact; 
		this.orderedTo = orderedTo; 
		this.employeeID = employeeID; 
		this.emailto = emailto; 
		this.farmID = farmID; 
		this.orderDate = orderDate; 
		this.breed = breed; 
		this.status = status; 
	}

	public String getOsid(){
		return this.osid;
	}
	public void setOsid(String osid) {
		this.osid = osid; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getContact(){
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact; 
	}
	public String getOrderedTo(){
		return this.orderedTo;
	}
	public void setOrderedTo(String orderedTo) {
		this.orderedTo = orderedTo; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getEmailto(){
		return this.emailto;
	}
	public void setEmailto(String emailto) {
		this.emailto = emailto; 
	}
	public String getFarmID(){
		return this.farmID;
	}
	public void setFarmID(String farmID) {
		this.farmID = farmID; 
	}
	public Date getOrderDate(){
		return this.orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate; 
	}
	public String getBreed(){
		return this.breed;
	}
	public void setBreed(String breed) {
		this.breed = breed; 
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status; 
	}
}
