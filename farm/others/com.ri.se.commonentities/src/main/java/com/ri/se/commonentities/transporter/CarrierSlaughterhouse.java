package com.ri.se.commonentities.transporter;

public class CarrierSlaughterhouse{
	private String tripNo;
	private String slaughterhousename;
	private String currentStatus;
	private long expectedArrivalDate;
	private String location;
	private long startDate;
	public CarrierSlaughterhouse (){

	}
	public CarrierSlaughterhouse ( String tripNo,String slaughterhousename,String currentStatus,long expectedArrivalDate,String location,long startDate){
		this.tripNo = tripNo; 
		this.slaughterhousename = slaughterhousename; 
		this.currentStatus = currentStatus; 
		this.expectedArrivalDate = expectedArrivalDate; 
		this.location = location; 
		this.startDate = startDate; 
	}

	public String getTripNo(){
		return this.tripNo;
	}
	public void setTripNo(String tripNo) {
		this.tripNo = tripNo; 
	}
	public String getSlaughterhousename(){
		return this.slaughterhousename;
	}
	public void setSlaughterhousename(String slaughterhousename) {
		this.slaughterhousename = slaughterhousename; 
	}
	public String getCurrentStatus(){
		return this.currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus; 
	}
	public long getExpectedArrivalDate(){
		return this.expectedArrivalDate;
	}
	public void setExpectedArrivalDate(long expectedArrivalDate) {
		this.expectedArrivalDate = expectedArrivalDate; 
	}
	public String getLocation(){
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location; 
	}
	public long getStartDate(){
		return this.startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate; 
	}
}
