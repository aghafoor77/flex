package com.ri.se.reportslaughterhouse.dto;

import java.util.Date;
import com.ri.se.reportslaughterhouse.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class ReportSlaughterhouseEntityConverter{

	public DTOReportSlaughterhouse getDTOReportSlaughterhouse(ReportSlaughterhouse _reportSlaughterhouse)  throws Exception {
		DTOReportSlaughterhouse _dTOReportSlaughterhouse = new DTOReportSlaughterhouse();
		_dTOReportSlaughterhouse.setRid( _reportSlaughterhouse.getRid());
		_dTOReportSlaughterhouse.setNotes( _reportSlaughterhouse.getNotes());
		_dTOReportSlaughterhouse.setSlaughterhousename( _reportSlaughterhouse.getSlaughterhousename());
		_dTOReportSlaughterhouse.setResponse( _reportSlaughterhouse.getResponse());
		_dTOReportSlaughterhouse.setSex( _reportSlaughterhouse.getSex());
		_dTOReportSlaughterhouse.setNumberofanimals( _reportSlaughterhouse.getNumberofanimals());
		_dTOReportSlaughterhouse.setReportingDate(toString( _reportSlaughterhouse.getReportingDate()));
		_dTOReportSlaughterhouse.setEmployeeID( _reportSlaughterhouse.getEmployeeID());
		_dTOReportSlaughterhouse.setSlaughterhousecontact( _reportSlaughterhouse.getSlaughterhousecontact());
		_dTOReportSlaughterhouse.setAge( _reportSlaughterhouse.getAge());
		_dTOReportSlaughterhouse.setBreed( _reportSlaughterhouse.getBreed());
		return _dTOReportSlaughterhouse;
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


	public ReportSlaughterhouse getReportSlaughterhouse(DTOReportSlaughterhouse __dTOReportSlaughterhouse)  throws Exception {
		ReportSlaughterhouse reportSlaughterhouse = new ReportSlaughterhouse();
		reportSlaughterhouse.setRid( __dTOReportSlaughterhouse.getRid());
		reportSlaughterhouse.setNotes( __dTOReportSlaughterhouse.getNotes());
		reportSlaughterhouse.setSlaughterhousename( __dTOReportSlaughterhouse.getSlaughterhousename());
		reportSlaughterhouse.setResponse( __dTOReportSlaughterhouse.getResponse());
		reportSlaughterhouse.setSex( __dTOReportSlaughterhouse.getSex());
		reportSlaughterhouse.setNumberofanimals( __dTOReportSlaughterhouse.getNumberofanimals());
		reportSlaughterhouse.setReportingDate(toDate( __dTOReportSlaughterhouse.getReportingDate()));
		reportSlaughterhouse.setEmployeeID( __dTOReportSlaughterhouse.getEmployeeID());
		reportSlaughterhouse.setSlaughterhousecontact( __dTOReportSlaughterhouse.getSlaughterhousecontact());
		reportSlaughterhouse.setAge( __dTOReportSlaughterhouse.getAge());
		reportSlaughterhouse.setBreed( __dTOReportSlaughterhouse.getBreed());
		return reportSlaughterhouse;
	}
}
