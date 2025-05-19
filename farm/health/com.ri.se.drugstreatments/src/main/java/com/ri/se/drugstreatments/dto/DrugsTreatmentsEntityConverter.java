package com.ri.se.drugstreatments.dto;

import java.util.Date;
import com.ri.se.drugstreatments.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class DrugsTreatmentsEntityConverter{

	public DTODrugsTreatments getDTODrugsTreatments(DrugsTreatments _drugsTreatments)  throws Exception {
		DTODrugsTreatments _dTODrugsTreatments = new DTODrugsTreatments();
		_dTODrugsTreatments.setDtid( _drugsTreatments.getDtid());
		_dTODrugsTreatments.setAssignedDate(toString( _drugsTreatments.getAssignedDate()));
		_dTODrugsTreatments.setReason( _drugsTreatments.getReason());
		_dTODrugsTreatments.setDrungs( _drugsTreatments.getDrungs());
		_dTODrugsTreatments.setRefnumber( _drugsTreatments.getRefnumber());
		_dTODrugsTreatments.setExaminedBy( _drugsTreatments.getExaminedBy());
		_dTODrugsTreatments.setAnimalID( _drugsTreatments.getAnimalID());
		_dTODrugsTreatments.setReftype( _drugsTreatments.getReftype());
		_dTODrugsTreatments.setIsAntibiotic( _drugsTreatments.getIsAntibiotic());
		_dTODrugsTreatments.setEndDate(toString( _drugsTreatments.getEndDate()));
		_dTODrugsTreatments.setQuarantinePeriod( _drugsTreatments.getQuarantinePeriod());
		return _dTODrugsTreatments;
	}
	private Date toDate(String dateStr) throws Exception {
		String full = "yyyy-MM-dd HH:mm:ss";
		String small = "yyyy-MM-dd";			try {
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


	public DrugsTreatments getDrugsTreatments(DTODrugsTreatments __dTODrugsTreatments)  throws Exception {
		DrugsTreatments drugsTreatments = new DrugsTreatments();
		drugsTreatments.setDtid( __dTODrugsTreatments.getDtid());
		drugsTreatments.setAssignedDate(toDate( __dTODrugsTreatments.getAssignedDate()));
		drugsTreatments.setReason( __dTODrugsTreatments.getReason());
		drugsTreatments.setDrungs( __dTODrugsTreatments.getDrungs());
		drugsTreatments.setRefnumber( __dTODrugsTreatments.getRefnumber());
		drugsTreatments.setExaminedBy( __dTODrugsTreatments.getExaminedBy());
		drugsTreatments.setAnimalID( __dTODrugsTreatments.getAnimalID());
		drugsTreatments.setReftype( __dTODrugsTreatments.getReftype());
		drugsTreatments.setIsAntibiotic( __dTODrugsTreatments.getIsAntibiotic());
		drugsTreatments.setEndDate( toDate(__dTODrugsTreatments.getEndDate()));
		drugsTreatments.setQuarantinePeriod( __dTODrugsTreatments.getQuarantinePeriod());
		
		
		
		return drugsTreatments;
	}
}
