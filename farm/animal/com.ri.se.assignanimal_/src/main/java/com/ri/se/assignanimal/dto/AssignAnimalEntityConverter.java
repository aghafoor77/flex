package com.ri.se.assignanimal.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.ri.se.assignanimal.persistance.AssignAnimal;

public class AssignAnimalEntityConverter{

	public DTOAssignAnimal getDTOAssignAnimal(AssignAnimal _assignAnimal)  throws Exception {
		DTOAssignAnimal _dTOAssignAnimal = new DTOAssignAnimal();
		_dTOAssignAnimal.setAaid( _assignAnimal.getAaid());
		_dTOAssignAnimal.setAssignedDate(toString( _assignAnimal.getAssignedDate()));
		_dTOAssignAnimal.setNotes( _assignAnimal.getNotes());
		_dTOAssignAnimal.setExaminer( _assignAnimal.getExaminer());
		_dTOAssignAnimal.setAction( _assignAnimal.getAction());
		_dTOAssignAnimal.setSubmissionDate(toString( _assignAnimal.getSubmissionDate()));
		 String animals[] = _assignAnimal.getAnimals().split(",");
		 List<String > aniList = new ArrayList<String>();
		 for(String an : animals) {
			 if(an.length() != 0) {
				 aniList .add(an);
			 }
		 }
		_dTOAssignAnimal.setAnimals(aniList);
		_dTOAssignAnimal.setReportReference( _assignAnimal.getReportReference());
		_dTOAssignAnimal.setReportSubmitted( _assignAnimal.getReportSubmitted());
		return _dTOAssignAnimal;
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


	public AssignAnimal getAssignAnimal(DTOAssignAnimal __dTOAssignAnimal)  throws Exception {
		AssignAnimal assignAnimal = new AssignAnimal();
		assignAnimal.setAaid( __dTOAssignAnimal.getAaid());
		assignAnimal.setAssignedDate(toDate( __dTOAssignAnimal.getAssignedDate()));
		assignAnimal.setNotes( __dTOAssignAnimal.getNotes());
		assignAnimal.setExaminer( __dTOAssignAnimal.getExaminer());
		assignAnimal.setAction( __dTOAssignAnimal.getAction());
		assignAnimal.setSubmissionDate(toDate( __dTOAssignAnimal.getSubmissionDate()));
		String ani = "";
		for(String an: __dTOAssignAnimal.getAnimals()){
			ani =ani+","+an;
		}
		assignAnimal.setAnimals(ani );
		assignAnimal.setReportReference( __dTOAssignAnimal.getReportReference());
		assignAnimal.setReportSubmitted( __dTOAssignAnimal.getReportSubmitted());
		return assignAnimal;
	}
}
