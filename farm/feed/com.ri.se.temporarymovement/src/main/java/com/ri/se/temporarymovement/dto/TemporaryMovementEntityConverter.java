package com.ri.se.temporarymovement.dto;

import java.util.Date;
import com.ri.se.temporarymovement.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class TemporaryMovementEntityConverter{

	public DTOTemporaryMovement getDTOTemporaryMovement(TemporaryMovement _temporaryMovement)  throws Exception {
		DTOTemporaryMovement _dTOTemporaryMovement = new DTOTemporaryMovement();
		_dTOTemporaryMovement.setTmid( _temporaryMovement.getTmid());
		_dTOTemporaryMovement.setOutDate(toString( _temporaryMovement.getOutDate()));
		_dTOTemporaryMovement.setNotes( _temporaryMovement.getNotes());
		_dTOTemporaryMovement.setPurpose( _temporaryMovement.getPurpose());
		_dTOTemporaryMovement.setInDate(toString( _temporaryMovement.getInDate()));
		_dTOTemporaryMovement.setTmType( _temporaryMovement.getTmType());
		_dTOTemporaryMovement.setEmployeeID( _temporaryMovement.getEmployeeID());
		return _dTOTemporaryMovement;
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


	public TemporaryMovement getTemporaryMovement(DTOTemporaryMovement __dTOTemporaryMovement)  throws Exception {
		TemporaryMovement temporaryMovement = new TemporaryMovement();
		temporaryMovement.setTmid( __dTOTemporaryMovement.getTmid());
		temporaryMovement.setOutDate(toDate( __dTOTemporaryMovement.getOutDate()));
		temporaryMovement.setNotes( __dTOTemporaryMovement.getNotes());
		temporaryMovement.setPurpose( __dTOTemporaryMovement.getPurpose());
		temporaryMovement.setInDate(toDate( __dTOTemporaryMovement.getInDate()));
		temporaryMovement.setTmType( __dTOTemporaryMovement.getTmType());
		temporaryMovement.setEmployeeID( __dTOTemporaryMovement.getEmployeeID());
		return temporaryMovement;
	}
}
