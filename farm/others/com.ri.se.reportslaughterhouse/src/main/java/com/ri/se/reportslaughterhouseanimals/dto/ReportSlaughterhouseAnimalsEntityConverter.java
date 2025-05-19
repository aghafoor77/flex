package com.ri.se.reportslaughterhouseanimals.dto;

import java.util.Date;
import com.ri.se.reportslaughterhouseanimals.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class ReportSlaughterhouseAnimalsEntityConverter{

	public DTOReportSlaughterhouseAnimals getDTOReportSlaughterhouseAnimals(ReportSlaughterhouseAnimals _reportSlaughterhouseAnimals)  throws Exception {
		DTOReportSlaughterhouseAnimals _dTOReportSlaughterhouseAnimals = new DTOReportSlaughterhouseAnimals();
		_dTOReportSlaughterhouseAnimals.setRsaid( _reportSlaughterhouseAnimals.getRsaid());
		_dTOReportSlaughterhouseAnimals.setSelectionDate(toString( _reportSlaughterhouseAnimals.getSelectionDate()));
		_dTOReportSlaughterhouseAnimals.setEmployeeID( _reportSlaughterhouseAnimals.getEmployeeID());
		_dTOReportSlaughterhouseAnimals.setRid( _reportSlaughterhouseAnimals.getRid());
		_dTOReportSlaughterhouseAnimals.setAnimalID( _reportSlaughterhouseAnimals.getAnimalID());
		return _dTOReportSlaughterhouseAnimals;
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


	public ReportSlaughterhouseAnimals getReportSlaughterhouseAnimals(DTOReportSlaughterhouseAnimals __dTOReportSlaughterhouseAnimals)  throws Exception {
		ReportSlaughterhouseAnimals reportSlaughterhouseAnimals = new ReportSlaughterhouseAnimals();
		reportSlaughterhouseAnimals.setRsaid( __dTOReportSlaughterhouseAnimals.getRsaid());
		reportSlaughterhouseAnimals.setSelectionDate(toDate( __dTOReportSlaughterhouseAnimals.getSelectionDate()));
		reportSlaughterhouseAnimals.setEmployeeID( __dTOReportSlaughterhouseAnimals.getEmployeeID());
		reportSlaughterhouseAnimals.setRid( __dTOReportSlaughterhouseAnimals.getRid());
		reportSlaughterhouseAnimals.setAnimalID( __dTOReportSlaughterhouseAnimals.getAnimalID());
		return reportSlaughterhouseAnimals;
	}
}
