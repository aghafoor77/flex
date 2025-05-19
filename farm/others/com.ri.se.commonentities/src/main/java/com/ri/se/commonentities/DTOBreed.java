package com.ri.se.commonentities;

import java.util.Date;

public class DTOBreed{

	private String breedname;
	public DTOBreed (){

	}
	public DTOBreed ( String breedname){
		this.breedname = breedname; 
	}

	public String getBreedname(){
		return this.breedname;
	}
	public void setBreedname(String breedname) {
		this.breedname = breedname; 
	}
}
