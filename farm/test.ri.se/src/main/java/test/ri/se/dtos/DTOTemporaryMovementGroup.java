package test.ri.se.dtos;

import java.util.Date;

public class DTOTemporaryMovementGroup{

	private String tmgid;
	private String outDate;
	private String tmid;
	private String inDate;
	private String employeeID;
	private String animalID;
	public DTOTemporaryMovementGroup (){

	}
	public DTOTemporaryMovementGroup ( String tmgid,String outDate,String tmid,String inDate,String employeeID,String animalID){
		this.tmgid = tmgid; 
		this.outDate = outDate; 
		this.tmid = tmid; 
		this.inDate = inDate; 
		this.employeeID = employeeID; 
		this.animalID = animalID; 
	}

	public String getTmgid(){
		return this.tmgid;
	}
	public void setTmgid(String tmgid) {
		this.tmgid = tmgid; 
	}
	public String getOutDate(){
		return this.outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate; 
	}
	public String getTmid(){
		return this.tmid;
	}
	public void setTmid(String tmid) {
		this.tmid = tmid; 
	}
	public String getInDate(){
		return this.inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
}
