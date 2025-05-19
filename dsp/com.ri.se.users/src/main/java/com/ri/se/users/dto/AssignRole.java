package com.ri.se.users.dto;

public class AssignRole {

	private String veid;
	private String email;
	private String roles;
	private String organization;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoles() {
		return roles;
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}	
}
