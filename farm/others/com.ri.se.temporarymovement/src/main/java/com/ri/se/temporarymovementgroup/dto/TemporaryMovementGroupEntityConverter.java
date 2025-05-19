package com.ri.se.temporarymovementgroup.dto;

import java.util.Date;
import com.ri.se.temporarymovementgroup.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class TemporaryMovementGroupEntityConverter{

	public DTOTemporaryMovementGroup getDTOTemporaryMovementGroup(TemporaryMovementGroup _temporaryMovementGroup)  throws Exception {
		DTOTemporaryMovementGroup _dTOTemporaryMovementGroup = new DTOTemporaryMovementGroup();
		_dTOTemporaryMovementGroup.setTmgid( _temporaryMovementGroup.getTmgid());
		_dTOTemporaryMovementGroup.setOutDate(toString( _temporaryMovementGroup.getOutDate()));
		_dTOTemporaryMovementGroup.setTmid( _temporaryMovementGroup.getTmid());
		_dTOTemporaryMovementGroup.setInDate(toString( _temporaryMovementGroup.getInDate()));
		_dTOTemporaryMovementGroup.setEmployeeID( _temporaryMovementGroup.getEmployeeID());
		_dTOTemporaryMovementGroup.setAnimalID( _temporaryMovementGroup.getAnimalID());
		return _dTOTemporaryMovementGroup;
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


	public TemporaryMovementGroup getTemporaryMovementGroup(DTOTemporaryMovementGroup __dTOTemporaryMovementGroup)  throws Exception {
		TemporaryMovementGroup temporaryMovementGroup = new TemporaryMovementGroup();
		temporaryMovementGroup.setTmgid( __dTOTemporaryMovementGroup.getTmgid());
		temporaryMovementGroup.setOutDate(toDate( __dTOTemporaryMovementGroup.getOutDate()));
		temporaryMovementGroup.setTmid( __dTOTemporaryMovementGroup.getTmid());
		temporaryMovementGroup.setInDate(toDate( __dTOTemporaryMovementGroup.getInDate()));
		temporaryMovementGroup.setEmployeeID( __dTOTemporaryMovementGroup.getEmployeeID());
		temporaryMovementGroup.setAnimalID( __dTOTemporaryMovementGroup.getAnimalID());
		return temporaryMovementGroup;
	}
}
