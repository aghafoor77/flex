package com.ri.se.commonentities.transporter;

public class SHCarrier{
	private String tripNo;
	private String carrierNumber;
	private String carrierId;
	private String CID;
	public SHCarrier (){

	}
	public SHCarrier ( String tripNo,String carrierNumber,String carrierId,String CID){
		this.tripNo = tripNo; 
		this.carrierNumber = carrierNumber; 
		this.carrierId = carrierId; 
		this.CID = CID; 
	}

	public String getTripNo(){
		return this.tripNo;
	}
	public void setTripNo(String tripNo) {
		this.tripNo = tripNo; 
	}
	public String getCarrierNumber(){
		return this.carrierNumber;
	}
	public void setCarrierNumber(String carrierNumber) {
		this.carrierNumber = carrierNumber; 
	}
	public String getCarrierId(){
		return this.carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId; 
	}
	public String getCID(){
		return this.CID;
	}
	public void setCID(String CID) {
		this.CID = CID; 
	}
}
