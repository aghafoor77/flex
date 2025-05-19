package com.ri.se.animalderegister.dto;

import java.util.Date;
import com.ri.se.animalderegister.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class AnimalDeregisterEntityConverter{

	public DTOAnimalDeregister getDTOAnimalDeregister(AnimalDeregister _animalDeregister)  throws Exception {
		DTOAnimalDeregister _dTOAnimalDeregister = new DTOAnimalDeregister();
		_dTOAnimalDeregister.setAnimalID( _animalDeregister.getAnimalID());
		_dTOAnimalDeregister.setDeregisterDate(toString( _animalDeregister.getDeregisterDate()));
		_dTOAnimalDeregister.setReportBy( _animalDeregister.getReportBy());
		_dTOAnimalDeregister.setNotes( _animalDeregister.getNotes());
		_dTOAnimalDeregister.setReportTo( _animalDeregister.getReportTo());
		_dTOAnimalDeregister.setResponse( _animalDeregister.getResponse());
		_dTOAnimalDeregister.setLocation( _animalDeregister.getLocation());
		_dTOAnimalDeregister.setEmailTo( _animalDeregister.getEmailTo());
		_dTOAnimalDeregister.setDcode( _animalDeregister.getDcode());
		return _dTOAnimalDeregister;
	}
	private Date toDate(String dateStr) throws Exception {
		String full = "yyyy-MM-dd HH:mm:ss";		String small= "yyyy-MM-dd";			try {
				if(Objects.isNull(dateStr) || dateStr.length() == 0 ) {
					return null;
				}
				if(!dateStr.contains("-")) {
					throw new Exception("Invalid date foramt. It should be either '"+small+"' or '"+full+"'format." );
				}
				if(!dateStr.contains(":")) {
						dateStr+=" 00:00:00";
				}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(full);
				return simpleDateFormat.parse(dateStr);
			}catch(Exception exp) {
				throw new Exception("Invalid date foramt. It should be either '"+small+"' or '"+full+"'format." );
		}
	}
	
	private String toString(Date date) throws Exception {
		String full = "yyyy-MM-dd HH:mm:ss";
		String small= "yyyy-MM-dd";

		String smallTime= "00:00:00";

		if(Objects.isNull(date)) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(full);
			if(!simpleDateFormat.format(date).contains(smallTime))
				return simpleDateFormat.format(date);
			else {
				simpleDateFormat = new SimpleDateFormat(small);
				return simpleDateFormat.format(date);
			}
		}catch(Exception exp) {
			throw new Exception("Invalid date foramt.");
		}
	}


	public AnimalDeregister getAnimalDeregister(DTOAnimalDeregister __dTOAnimalDeregister)  throws Exception {
		AnimalDeregister animalDeregister = new AnimalDeregister();
		animalDeregister.setAnimalID( __dTOAnimalDeregister.getAnimalID());
		animalDeregister.setDeregisterDate(toDate( __dTOAnimalDeregister.getDeregisterDate()));
		animalDeregister.setReportBy( __dTOAnimalDeregister.getReportBy());
		animalDeregister.setNotes( __dTOAnimalDeregister.getNotes());
		animalDeregister.setReportTo( __dTOAnimalDeregister.getReportTo());
		animalDeregister.setResponse( __dTOAnimalDeregister.getResponse());
		animalDeregister.setLocation( __dTOAnimalDeregister.getLocation());
		animalDeregister.setEmailTo( __dTOAnimalDeregister.getEmailTo());
		animalDeregister.setDcode( __dTOAnimalDeregister.getDcode());
		return animalDeregister;
	}
}
