package com.ri.se.commonentities;

public class DTOUsers {
	
	private String veid;
	private String email;
	private String password;
	private String roles;
	public DTOUsers (){

	}
	public DTOUsers (String veid, String email,String password,String roles){
		this.veid = veid;
		this.email = email; 
		this.password = password; 
		this.roles = roles; 
	}

	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email; 
	}
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password; 
	}
	public String getRoles(){
		return this.roles;
	}
	public void setRoles(String roles) {
		this.roles = roles; 
	}
	public String getVeid() {
		return veid;
	}
	public void setVeid(String veid) {
		this.veid = veid;
	}
	
}
