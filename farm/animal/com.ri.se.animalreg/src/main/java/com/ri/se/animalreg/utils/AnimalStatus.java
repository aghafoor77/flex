package com.ri.se.animalreg.utils;

public enum AnimalStatus {


	   // enum constants calling the enum constructors 
	   IN("IN"),
	   TRANSFERED("TRANSFERED"),
	   DIED("DIED"),
	   OUT("OUT");

	   private final String status;

	   // private enum constructor
	   private AnimalStatus(String status) {
	      this.status = status;
	   }

	   public String getValue() {
	      return status;
	   }
}
