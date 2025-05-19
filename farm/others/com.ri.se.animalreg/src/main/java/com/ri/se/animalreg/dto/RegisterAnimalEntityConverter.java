package com.ri.se.animalreg.dto;

import java.util.Date;
import com.ri.se.animalreg.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class RegisterAnimalEntityConverter{

	public DTORegisterAnimal getDTORegisterAnimal(RegisterAnimal _registerAnimal)  throws Exception {
		DTORegisterAnimal _dTORegisterAnimal = new DTORegisterAnimal();
		_dTORegisterAnimal.setAnimalID( _registerAnimal.getAnimalID());
		_dTORegisterAnimal.setReceivedFarmID( _registerAnimal.getReceivedFarmID());
		_dTORegisterAnimal.setAnimalIDMother( _registerAnimal.getAnimalIDMother());
		_dTORegisterAnimal.setNotes( _registerAnimal.getNotes());
		_dTORegisterAnimal.setPregnancyExamination( _registerAnimal.getPregnancyExamination());
		_dTORegisterAnimal.setSex( _registerAnimal.getSex());
		_dTORegisterAnimal.setWeight( _registerAnimal.getWeight());
		_dTORegisterAnimal.setDateOfBirth(toString( _registerAnimal.getDateOfBirth()));
		_dTORegisterAnimal.setBreed( _registerAnimal.getBreed());
		_dTORegisterAnimal.setBirthPlace( _registerAnimal.getBirthPlace());
		_dTORegisterAnimal.setEmployerID( _registerAnimal.getEmployerID());
		_dTORegisterAnimal.setUnit( _registerAnimal.getUnit());
		_dTORegisterAnimal.setReceivedFarmName( _registerAnimal.getReceivedFarmName());
		_dTORegisterAnimal.setPreviousAnimalID( _registerAnimal.getPreviousAnimalID());
		_dTORegisterAnimal.setAboutAnimal( _registerAnimal.getAboutAnimal());
		_dTORegisterAnimal.setOthers( _registerAnimal.getOthers());
		_dTORegisterAnimal.setStatus( _registerAnimal.getStatus());
		return _dTORegisterAnimal;
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


	public RegisterAnimal getRegisterAnimal(DTORegisterAnimal __dTORegisterAnimal)  throws Exception {
		RegisterAnimal registerAnimal = new RegisterAnimal();
		registerAnimal.setAnimalID( __dTORegisterAnimal.getAnimalID());
		registerAnimal.setReceivedFarmID( __dTORegisterAnimal.getReceivedFarmID());
		registerAnimal.setAnimalIDMother( __dTORegisterAnimal.getAnimalIDMother());
		registerAnimal.setNotes( __dTORegisterAnimal.getNotes());
		registerAnimal.setPregnancyExamination( __dTORegisterAnimal.getPregnancyExamination());
		registerAnimal.setSex( __dTORegisterAnimal.getSex());
		registerAnimal.setWeight( __dTORegisterAnimal.getWeight());
		registerAnimal.setDateOfBirth(toDate( __dTORegisterAnimal.getDateOfBirth()));
		registerAnimal.setBreed( __dTORegisterAnimal.getBreed());
		registerAnimal.setBirthPlace( __dTORegisterAnimal.getBirthPlace());
		registerAnimal.setEmployerID( __dTORegisterAnimal.getEmployerID());
		registerAnimal.setUnit( __dTORegisterAnimal.getUnit());
		registerAnimal.setReceivedFarmName( __dTORegisterAnimal.getReceivedFarmName());
		registerAnimal.setPreviousAnimalID( __dTORegisterAnimal.getPreviousAnimalID());
		registerAnimal.setAboutAnimal( __dTORegisterAnimal.getAboutAnimal());
		registerAnimal.setOthers( __dTORegisterAnimal.getOthers());
		registerAnimal.setStatus( __dTORegisterAnimal.getStatus());
		return registerAnimal;
	}
}
