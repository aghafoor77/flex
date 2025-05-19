package com.ri.se.commonentities;

public class SCSecretKey{

	private String ssid;
	private String eid;
	private String did;
	private String sharedkey;
	public SCSecretKey (){

	}
	public SCSecretKey ( String ssid,String eid,String did,String sharedkey){
		this.ssid = ssid; 
		this.eid = eid; 
		this.did = did; 
		this.sharedkey = sharedkey; 
	}

	public String getSsid(){
		return this.ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid; 
	}
	public String getEid(){
		return this.eid;
	}
	public void setEid(String eid) {
		this.eid = eid; 
	}
	public String getDid(){
		return this.did;
	}
	public void setDid(String did) {
		this.did = did; 
	}
	public String getSharedkey() {
		return sharedkey;
	}
	public void setSharedkey(String sharedkey) {
		this.sharedkey = sharedkey;
	}
	
	
}
