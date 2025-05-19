package com.ri.se.animalreg.utils;

public enum Status{ 
    
	IN("In"),
    OUT("out"),
    FEEDING("Feeding"),
    SELL("Sell"),
    SLAUGHTER("Slaughter"),
    DEREGISTER("Deregister"),
    ACTIVE("Active");

    public final String value;

    private Status(String value) {
        this.value = value;
    }
   
}