package org.ri.se.trace.api.utils;

public class SCResource{
	
	private String did;
	private String role;
	private String publickey;
	private String company;
	public SCResource (){

	}
	public SCResource ( String did,String role,String publickey,String company){
		this.did = did; 
		this.role = role; 
		this.publickey = publickey; 
		this.company = company; 
	}

	public String getDid(){
		return this.did;
	}
	public void setDid(String did) {
		this.did = did; 
	}
	public String getRole(){
		return this.role;
	}
	public void setRole(String role) {
		this.role = role; 
	}
	public String getPublickey(){
		return this.publickey;
	}
	public void setPublickey(String publickey) {
		this.publickey = publickey; 
	}
	public String getCompany(){
		return this.company;
	}
	public void setCompany(String company) {
		this.company = company; 
	}
}
