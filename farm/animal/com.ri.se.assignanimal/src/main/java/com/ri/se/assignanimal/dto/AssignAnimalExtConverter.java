package com.ri.se.assignanimal.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import com.ri.se.assignanimal.persistance.AssignAnimal;

public class AssignAnimalExtConverter {
	
	public AssignAnimal toAssignAnimal(DTOAssignAnimalExt dtoAssignAnimalExt) throws Exception {
		
		AssignAnimal assignanimal = new AssignAnimal();
		assignanimal.setAaid("AAID-" + System.currentTimeMillis());
		assignanimal.setAction(dtoAssignAnimalExt.getAction());
		assignanimal.setAssignedDate(toDate(dtoAssignAnimalExt.getAssignedDate()));
		assignanimal.setExaminer(dtoAssignAnimalExt.getExaminer());
		assignanimal.setNotes(dtoAssignAnimalExt.getNotes());
		assignanimal.setAssignedBy(dtoAssignAnimalExt.getAssignedBy());
		return assignanimal;
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

}
