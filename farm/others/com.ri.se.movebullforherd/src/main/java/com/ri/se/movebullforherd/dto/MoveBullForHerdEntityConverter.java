package com.ri.se.movebullforherd.dto;

import java.util.Date;
import com.ri.se.movebullforherd.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class MoveBullForHerdEntityConverter{

	public DTOMoveBullForHerd getDTOMoveBullForHerd(MoveBullForHerd _moveBullForHerd)  throws Exception {
		DTOMoveBullForHerd _dTOMoveBullForHerd = new DTOMoveBullForHerd();
		_dTOMoveBullForHerd.setMb4hid( _moveBullForHerd.getMb4hid());
		_dTOMoveBullForHerd.setNotes( _moveBullForHerd.getNotes());
		_dTOMoveBullForHerd.setEntryDate(toString( _moveBullForHerd.getEntryDate()));
		_dTOMoveBullForHerd.setGroupFemale( _moveBullForHerd.getGroupFemale());
		_dTOMoveBullForHerd.setEmployeeID( _moveBullForHerd.getEmployeeID());
		_dTOMoveBullForHerd.setLocation( _moveBullForHerd.getLocation());
		_dTOMoveBullForHerd.setAnimalID( _moveBullForHerd.getAnimalID());
		_dTOMoveBullForHerd.setExitDate(toString( _moveBullForHerd.getExitDate()));
		return _dTOMoveBullForHerd;
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


	public MoveBullForHerd getMoveBullForHerd(DTOMoveBullForHerd __dTOMoveBullForHerd)  throws Exception {
		MoveBullForHerd moveBullForHerd = new MoveBullForHerd();
		moveBullForHerd.setMb4hid( __dTOMoveBullForHerd.getMb4hid());
		moveBullForHerd.setNotes( __dTOMoveBullForHerd.getNotes());
		moveBullForHerd.setEntryDate(toDate( __dTOMoveBullForHerd.getEntryDate()));
		moveBullForHerd.setGroupFemale( __dTOMoveBullForHerd.getGroupFemale());
		moveBullForHerd.setEmployeeID( __dTOMoveBullForHerd.getEmployeeID());
		moveBullForHerd.setLocation( __dTOMoveBullForHerd.getLocation());
		moveBullForHerd.setAnimalID( __dTOMoveBullForHerd.getAnimalID());
		moveBullForHerd.setExitDate(toDate( __dTOMoveBullForHerd.getExitDate()));
		return moveBullForHerd;
	}
}
