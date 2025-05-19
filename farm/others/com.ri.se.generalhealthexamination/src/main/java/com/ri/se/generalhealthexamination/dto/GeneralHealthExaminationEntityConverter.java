package com.ri.se.generalhealthexamination.dto;

import java.util.Date;
import com.ri.se.generalhealthexamination.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class GeneralHealthExaminationEntityConverter{

	public DTOGeneralHealthExamination getDTOGeneralHealthExamination(GeneralHealthExamination _generalHealthExamination)  throws Exception {
		DTOGeneralHealthExamination _dTOGeneralHealthExamination = new DTOGeneralHealthExamination();
		_dTOGeneralHealthExamination.setGaheid( _generalHealthExamination.getGaheid());
		_dTOGeneralHealthExamination.setObserver( _generalHealthExamination.getObserver());
		_dTOGeneralHealthExamination.setWound( _generalHealthExamination.getWound());
		_dTOGeneralHealthExamination.setNotes( _generalHealthExamination.getNotes());
		_dTOGeneralHealthExamination.setNotation( _generalHealthExamination.getNotation());
		_dTOGeneralHealthExamination.setTemperature( _generalHealthExamination.getTemperature());
		_dTOGeneralHealthExamination.setInfections( _generalHealthExamination.getInfections());
		_dTOGeneralHealthExamination.setLameness( _generalHealthExamination.getLameness());
		_dTOGeneralHealthExamination.setSwelling( _generalHealthExamination.getSwelling());
		_dTOGeneralHealthExamination.setGheDate(toString( _generalHealthExamination.getGheDate()));
		_dTOGeneralHealthExamination.setAnimalID( _generalHealthExamination.getAnimalID());
		_dTOGeneralHealthExamination.setWeak( _generalHealthExamination.getWeak());
		return _dTOGeneralHealthExamination;
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


	public GeneralHealthExamination getGeneralHealthExamination(DTOGeneralHealthExamination __dTOGeneralHealthExamination)  throws Exception {
		GeneralHealthExamination generalHealthExamination = new GeneralHealthExamination();
		generalHealthExamination.setGaheid( __dTOGeneralHealthExamination.getGaheid());
		generalHealthExamination.setObserver( __dTOGeneralHealthExamination.getObserver());
		generalHealthExamination.setWound( __dTOGeneralHealthExamination.getWound());
		generalHealthExamination.setNotes( __dTOGeneralHealthExamination.getNotes());
		generalHealthExamination.setNotation( __dTOGeneralHealthExamination.getNotation());
		generalHealthExamination.setTemperature( __dTOGeneralHealthExamination.getTemperature());
		generalHealthExamination.setInfections( __dTOGeneralHealthExamination.getInfections());
		generalHealthExamination.setLameness( __dTOGeneralHealthExamination.getLameness());
		generalHealthExamination.setSwelling( __dTOGeneralHealthExamination.getSwelling());
		generalHealthExamination.setGheDate(toDate( __dTOGeneralHealthExamination.getGheDate()));
		generalHealthExamination.setAnimalID( __dTOGeneralHealthExamination.getAnimalID());
		generalHealthExamination.setWeak( __dTOGeneralHealthExamination.getWeak());
		return generalHealthExamination;
	}
}
