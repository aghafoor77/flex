package com.ri.se.assignanimalstatus.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.ri.se.assignanimalstatus.persistance.AssignAnimalStatus;

public class AssignAnimalStatusEntityConverter{

	public DTOAssignAnimalStatus getDTOAssignAnimalStatus(AssignAnimalStatus _assignAnimalStatus)  throws Exception {
		DTOAssignAnimalStatus _dTOAssignAnimalStatus = new DTOAssignAnimalStatus();
		_dTOAssignAnimalStatus.setAasid( _assignAnimalStatus.getAasid());
		_dTOAssignAnimalStatus.setAaid( _assignAnimalStatus.getAaid());
		_dTOAssignAnimalStatus.setSubmissionDate(toString( _assignAnimalStatus.getSubmissionDate()));
		_dTOAssignAnimalStatus.setAnimals( _assignAnimalStatus.getAnimals());
		_dTOAssignAnimalStatus.setReportReference( _assignAnimalStatus.getReportReference());
		_dTOAssignAnimalStatus.setReportSubmitted( _assignAnimalStatus.getReportSubmitted());
		return _dTOAssignAnimalStatus;
	}
	
	public DTOAssignAnimalStatusList getDTOAssignAnimalStatusList(List<AssignAnimalStatus> _assignAnimalStatusList)  throws Exception {
		DTOAssignAnimalStatusList dtoAssignAnimalStatusList = new DTOAssignAnimalStatusList();
		for(AssignAnimalStatus aal : _assignAnimalStatusList) {
			dtoAssignAnimalStatusList.add(getDTOAssignAnimalStatus(aal));
		}		
		return dtoAssignAnimalStatusList ;
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


	public AssignAnimalStatus getAssignAnimalStatus(DTOAssignAnimalStatus __dTOAssignAnimalStatus)  throws Exception {
		AssignAnimalStatus assignAnimalStatus = new AssignAnimalStatus();
		assignAnimalStatus.setAasid( __dTOAssignAnimalStatus.getAasid());
		assignAnimalStatus.setAaid( __dTOAssignAnimalStatus.getAaid());
		assignAnimalStatus.setSubmissionDate(toDate( __dTOAssignAnimalStatus.getSubmissionDate()));
		assignAnimalStatus.setAnimals( __dTOAssignAnimalStatus.getAnimals());
		assignAnimalStatus.setReportReference( __dTOAssignAnimalStatus.getReportReference());
		assignAnimalStatus.setReportSubmitted( __dTOAssignAnimalStatus.getReportSubmitted());
		return assignAnimalStatus;
	}
}
