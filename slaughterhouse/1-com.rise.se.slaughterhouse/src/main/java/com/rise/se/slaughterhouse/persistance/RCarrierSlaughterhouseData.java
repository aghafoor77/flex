package com.rise.se.slaughterhouse.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RCarrierSlaughterhouseData")

public class RCarrierSlaughterhouseData{
	@Id
	@Column(name = "carrierId")
	private String carrierId;
	private String carrierNumber;
	private String tripNo;
	private String CID;
	public RCarrierSlaughterhouseData (){

	}
	public RCarrierSlaughterhouseData ( String carrierId,String carrierNumber,String tripNo,String CID){
		this.carrierId = carrierId; 
		this.carrierNumber = carrierNumber; 
		this.tripNo = tripNo; 
		this.CID = CID; 
	}

	public String getCarrierId(){
		return this.carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId; 
	}
	public String getCarrierNumber(){
		return this.carrierNumber;
	}
	public void setCarrierNumber(String carrierNumber) {
		this.carrierNumber = carrierNumber; 
	}
	public String getTripNo(){
		return this.tripNo;
	}
	public void setTripNo(String tripNo) {
		this.tripNo = tripNo; 
	}
	public String getCID(){
		return this.CID;
	}
	public void setCID(String CID) {
		this.CID = CID; 
	}
}
