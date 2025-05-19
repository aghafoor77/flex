package com.ri.se.assignfeed.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "AssignFeed")
@XmlRootElement
public class AssignFeed{
	@Id
	@Column(name = "afid")
	private String afid;
	private Date assignedDate;
	private String assignedBy;
	private String fpid;
	private String assignedTo;
	public AssignFeed (){

	}
	public AssignFeed ( String afid,Date assignedDate,String assignedBy,String fpid,String assignedTo){
		this.afid = afid; 
		this.assignedDate = assignedDate; 
		this.assignedBy = assignedBy; 
		this.fpid = fpid; 
		this.assignedTo = assignedTo; 
	}

	public String getAfid(){
		return this.afid;
	}
	public void setAfid(String afid) {
		this.afid = afid; 
	}
	public Date getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate; 
	}
	public String getAssignedBy(){
		return this.assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy; 
	}
	public String getFpid(){
		return this.fpid;
	}
	public void setFpid(String fpid) {
		this.fpid = fpid; 
	}
	public String getAssignedTo(){
		return this.assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo; 
	}
}
