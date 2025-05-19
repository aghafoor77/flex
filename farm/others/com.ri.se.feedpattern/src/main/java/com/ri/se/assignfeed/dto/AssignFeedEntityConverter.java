package com.ri.se.assignfeed.dto;

import java.util.Date;
import com.ri.se.assignfeed.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class AssignFeedEntityConverter{

	public DTOAssignFeed getDTOAssignFeed(AssignFeed _assignFeed)  throws Exception {
		DTOAssignFeed _dTOAssignFeed = new DTOAssignFeed();
		_dTOAssignFeed.setAfid( _assignFeed.getAfid());
		_dTOAssignFeed.setAssignedDate(toString( _assignFeed.getAssignedDate()));
		_dTOAssignFeed.setAssignedBy( _assignFeed.getAssignedBy());
		_dTOAssignFeed.setFpid( _assignFeed.getFpid());
		_dTOAssignFeed.setAssignedTo( _assignFeed.getAssignedTo());
		return _dTOAssignFeed;
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


	public AssignFeed getAssignFeed(DTOAssignFeed __dTOAssignFeed)  throws Exception {
		AssignFeed assignFeed = new AssignFeed();
		assignFeed.setAfid( __dTOAssignFeed.getAfid());
		assignFeed.setAssignedDate(toDate( __dTOAssignFeed.getAssignedDate()));
		assignFeed.setAssignedBy( __dTOAssignFeed.getAssignedBy());
		assignFeed.setFpid( __dTOAssignFeed.getFpid());
		assignFeed.setAssignedTo( __dTOAssignFeed.getAssignedTo());
		return assignFeed;
	}
}
