package com.ri.se.assignanimal.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "AssignAnimal")
@XmlRootElement
public class AssignAnimal{
	@Id
	@Column(name = "aaid")
	private String aaid;
	private Date assignedDate;
	private String notes;
	private String examiner;
	private String action;
	private String assignedBy;
	public AssignAnimal (){

	}
	public AssignAnimal ( String aaid,Date assignedDate,String notes,String examiner,String action, String assignedBy){
		this.aaid = aaid; 
		this.assignedDate = assignedDate; 
		this.notes = notes; 
		this.examiner = examiner; 
		this.action = action; 
		this.assignedBy = assignedBy;
	}

	public String getAaid(){
		return this.aaid;
	}
	public void setAaid(String aaid) {
		this.aaid = aaid; 
	}
	public Date getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getExaminer(){
		return this.examiner;
	}
	public void setExaminer(String examiner) {
		this.examiner = examiner; 
	}
	public String getAction(){
		return this.action;
	}
	public void setAction(String action) {
		this.action = action; 
	}
	public String getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}
	
}
