package com.ri.se.animalexamination.dto;

import java.util.Date;
import com.ri.se.animalexamination.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class AnimalExaminationEntityConverter{

	public DTOAnimalExamination getDTOAnimalExamination(AnimalExamination _animalExamination)  throws Exception {
		DTOAnimalExamination _dTOAnimalExamination = new DTOAnimalExamination();
		_dTOAnimalExamination.setAeid( _animalExamination.getAeid());
		_dTOAnimalExamination.setNotes( _animalExamination.getNotes());
		_dTOAnimalExamination.setExaminationDate(toString( _animalExamination.getExaminationDate()));
		_dTOAnimalExamination.setRefnumber( _animalExamination.getRefnumber());
		_dTOAnimalExamination.setRefType( _animalExamination.getRefType());
		_dTOAnimalExamination.setSensorData( _animalExamination.getSensorData());
		_dTOAnimalExamination.setEmployeeID( _animalExamination.getEmployeeID());
		_dTOAnimalExamination.setAnimalID( _animalExamination.getAnimalID());
		_dTOAnimalExamination.setExtepctedDate(toString( _animalExamination.getExtepctedDate()));
		_dTOAnimalExamination.setStatus( _animalExamination.getStatus());
		return _dTOAnimalExamination;
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


	public AnimalExamination getAnimalExamination(DTOAnimalExamination __dTOAnimalExamination)  throws Exception {
		AnimalExamination animalExamination = new AnimalExamination();
		animalExamination.setAeid( __dTOAnimalExamination.getAeid());
		animalExamination.setNotes( __dTOAnimalExamination.getNotes());
		animalExamination.setExaminationDate(toDate( __dTOAnimalExamination.getExaminationDate()));
		animalExamination.setRefnumber( __dTOAnimalExamination.getRefnumber());
		animalExamination.setRefType( __dTOAnimalExamination.getRefType());
		animalExamination.setSensorData( __dTOAnimalExamination.getSensorData());
		animalExamination.setEmployeeID( __dTOAnimalExamination.getEmployeeID());
		animalExamination.setAnimalID( __dTOAnimalExamination.getAnimalID());
		animalExamination.setExtepctedDate(toDate( __dTOAnimalExamination.getExtepctedDate()));
		animalExamination.setStatus( __dTOAnimalExamination.getStatus());
		return animalExamination;
	}
}
