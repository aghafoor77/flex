package com.ri.se.assignanimal.dto;

import java.util.ArrayList;
import java.util.List;

public class DTOAssignAnimalExt{

	private String aaid;
	private String assignedDate;
	private String notes;
	private String examiner;
	private String action;
	private List<String> animals;
	private String assignedBy;
		
	public  DTOAssignAnimalExt (){

	}
	public  DTOAssignAnimalExt ( String aaid,String assignedDate,String notes,String examiner,String action,String animals_, String assignedBy){
		this.aaid = aaid; 
		this.assignedDate = assignedDate; 
		this.notes = notes; 
		this.examiner = examiner; 
		this.action = action; 
	 
		this.animals = new ArrayList<String>();
		String an []= animals_.split(",");
		for(String a: an ) {
			this.animals .add(a);
		}
		this.assignedBy = assignedBy;
	 
	}

	public String getAaid(){
		return this.aaid;
	}
	public void setAaid(String aaid) {
		this.aaid = aaid; 
	}
	public String getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
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
	
	public List<String> getAnimals(){
		return this.animals;
	}
	public void setAnimals(List<String> animals) {
		this.animals = animals; 
	}
	public String getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}
}