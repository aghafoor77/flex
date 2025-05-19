package com.ri.se.resources.dto;

import java.util.Date;
import com.ri.se.resources.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class FacilityEntityConverter{

	public DTOFacility getDTOFacility(Facility _facility)  throws Exception {
		DTOFacility _dTOFacility = new DTOFacility();
		_dTOFacility.setFacilityNr( _facility.getFacilityNr());
		_dTOFacility.setAreasize( _facility.getAreasize());
		_dTOFacility.setFromDate( _facility.getFromDate());
		_dTOFacility.setMovementacrossEU( _facility.getMovementacrossEU());
		_dTOFacility.setAddress( _facility.getAddress());
		_dTOFacility.setMaxcapacity( _facility.getMaxcapacity());
		_dTOFacility.setTemporaryactivity( _facility.getTemporaryactivity());
		_dTOFacility.setAnlaggningsnumber( _facility.getAnlaggningsnumber());
		_dTOFacility.setSpecieskept( _facility.getSpecieskept());
		_dTOFacility.setFarmId( _facility.getFarmId());
		_dTOFacility.setType( _facility.getType());
		_dTOFacility.setBreedingmaterials( _facility.getBreedingmaterials());
		return _dTOFacility;
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


	public Facility getFacility(DTOFacility __dTOFacility)  throws Exception {
		Facility facility = new Facility();
		facility.setFacilityNr( __dTOFacility.getFacilityNr());
		facility.setAreasize( __dTOFacility.getAreasize());
		facility.setFromDate( __dTOFacility.getFromDate());
		facility.setMovementacrossEU( __dTOFacility.getMovementacrossEU());
		facility.setAddress( __dTOFacility.getAddress());
		facility.setMaxcapacity( __dTOFacility.getMaxcapacity());
		facility.setTemporaryactivity( __dTOFacility.getTemporaryactivity());
		facility.setAnlaggningsnumber( __dTOFacility.getAnlaggningsnumber());
		facility.setSpecieskept( __dTOFacility.getSpecieskept());
		facility.setFarmId( __dTOFacility.getFarmId());
		facility.setType( __dTOFacility.getType());
		facility.setBreedingmaterials( __dTOFacility.getBreedingmaterials());
		return facility;
	}
}
