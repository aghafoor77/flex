package com.ri.se.breed.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "Breed")
@XmlRootElement
public class Breed{
	@Id
	@Column(name = "breedname")
	private String breedname;
	public Breed (){

	}
	public Breed ( String breedname){
		this.breedname = breedname; 
	}

	public String getBreedname(){
		return this.breedname;
	}
	public void setBreedname(String breedname) {
		this.breedname = breedname; 
	}
}
