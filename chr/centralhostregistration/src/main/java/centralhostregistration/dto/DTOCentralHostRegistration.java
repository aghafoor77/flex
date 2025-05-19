package centralhostregistration.dto;

import java.util.Date;

public class DTOCentralHostRegistration{

	private String name;
	private String address;
	private int port;
	private String healthCheck;
	private String registrationDate;
	private String status;
	private String type;
	public DTOCentralHostRegistration (){

	}
	public DTOCentralHostRegistration ( String name,String address,int port,String healthCheck,String registrationDate,String status, String type){
		this.name = name; 
		this.address = address; 
		this.port = port; 
		this.healthCheck = healthCheck; 
		this.registrationDate = registrationDate; 
		this.status = status; 
		this.type = type;;
	}

	public String getName(){
		return this.name;
	}
	public void setName(String name) {
		this.name = name; 
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address; 
	}
	public int getPort(){
		return this.port;
	}
	public void setPort(int port) {
		this.port = port; 
	}
	public String getHealthCheck(){
		return this.healthCheck;
	}
	public void setHealthCheck(String healthCheck) {
		this.healthCheck = healthCheck; 
	}
	public String getRegistrationDate(){
		return this.registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate; 
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status; 
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
