package com.rise.se.slaughterhouse.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Slaughterhouse")

public class Slaughterhouse{
	@Id
	@Column(name = "sid")
	private String sid;
	public Slaughterhouse (){

	}
	public Slaughterhouse ( String sid){
		this.sid = sid; 
	}

	public String getSid(){
		return this.sid;
	}
	public void setSid(String sid) {
		this.sid = sid; 
	}
}
