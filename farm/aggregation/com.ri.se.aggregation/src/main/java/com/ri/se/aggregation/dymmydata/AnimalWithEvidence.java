package com.ri.se.aggregation.dymmydata;

import java.util.ArrayList;
import java.util.List;

import com.ri.se.commonentities.DTORegisterAnimal;

public class AnimalWithEvidence {
	private DTORegisterAnimal dtoRegisterAnimal1 = new DTORegisterAnimal();
	private List<Object> evidence = new ArrayList<Object>();
	public DTORegisterAnimal getDtoRegisterAnimal1() {
		return dtoRegisterAnimal1;
	}
	public void setDtoRegisterAnimal1(DTORegisterAnimal dtoRegisterAnimal1) {
		this.dtoRegisterAnimal1 = dtoRegisterAnimal1;
	}
	public List<Object> getEvidence() {
		return evidence;
	}
	public void setEvidence(List<Object> evidence) {
		this.evidence = evidence;
	}
	

}
