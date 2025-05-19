package com.ri.se.semenutilization.dto;

import java.util.Date;
import com.ri.se.semenutilization.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class SemenUtilizationEntityConverter{

	public DTOSemenUtilization getDTOSemenUtilization(SemenUtilization _semenUtilization)  throws Exception {
		DTOSemenUtilization _dTOSemenUtilization = new DTOSemenUtilization();
		_dTOSemenUtilization.setSuid( _semenUtilization.getSuid());
		_dTOSemenUtilization.setInsemationDate(toString( _semenUtilization.getInsemationDate()));
		_dTOSemenUtilization.setNotes( _semenUtilization.getNotes());
		_dTOSemenUtilization.setEmployeeID( _semenUtilization.getEmployeeID());
		_dTOSemenUtilization.setOsid( _semenUtilization.getOsid());
		_dTOSemenUtilization.setAnimalID( _semenUtilization.getAnimalID());
		return _dTOSemenUtilization;
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


	public SemenUtilization getSemenUtilization(DTOSemenUtilization __dTOSemenUtilization)  throws Exception {
		SemenUtilization semenUtilization = new SemenUtilization();
		semenUtilization.setSuid( __dTOSemenUtilization.getSuid());
		semenUtilization.setInsemationDate(toDate( __dTOSemenUtilization.getInsemationDate()));
		semenUtilization.setNotes( __dTOSemenUtilization.getNotes());
		semenUtilization.setEmployeeID( __dTOSemenUtilization.getEmployeeID());
		semenUtilization.setOsid( __dTOSemenUtilization.getOsid());
		semenUtilization.setAnimalID( __dTOSemenUtilization.getAnimalID());
		return semenUtilization;
	}
}
