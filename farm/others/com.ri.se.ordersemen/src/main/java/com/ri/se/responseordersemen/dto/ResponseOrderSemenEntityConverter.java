package com.ri.se.responseordersemen.dto;

import java.util.Date;
import com.ri.se.responseordersemen.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class ResponseOrderSemenEntityConverter{

	public DTOResponseOrderSemen getDTOResponseOrderSemen(ResponseOrderSemen _responseOrderSemen)  throws Exception {
		DTOResponseOrderSemen _dTOResponseOrderSemen = new DTOResponseOrderSemen();
		_dTOResponseOrderSemen.setOsid( _responseOrderSemen.getOsid());
		_dTOResponseOrderSemen.setNotes( _responseOrderSemen.getNotes());
		_dTOResponseOrderSemen.setResDate(toString( _responseOrderSemen.getResDate()));
		_dTOResponseOrderSemen.setEmployeeID( _responseOrderSemen.getEmployeeID());
		_dTOResponseOrderSemen.setRepliedBy( _responseOrderSemen.getRepliedBy());
		_dTOResponseOrderSemen.setBillingURL( _responseOrderSemen.getBillingURL());
		return _dTOResponseOrderSemen;
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

		if(Objects.isNull(date)) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(full);
			if(!simpleDateFormat.format(date).contains(small))
				return simpleDateFormat.format(date);
			else {
				simpleDateFormat = new SimpleDateFormat(small);
				return simpleDateFormat.format(date);
			}
		}catch(Exception exp) {
			throw new Exception("Invalid date foramt.");
		}
	}


	public ResponseOrderSemen getResponseOrderSemen(DTOResponseOrderSemen __dTOResponseOrderSemen)  throws Exception {
		ResponseOrderSemen responseOrderSemen = new ResponseOrderSemen();
		responseOrderSemen.setOsid( __dTOResponseOrderSemen.getOsid());
		responseOrderSemen.setNotes( __dTOResponseOrderSemen.getNotes());
		responseOrderSemen.setResDate(toDate( __dTOResponseOrderSemen.getResDate()));
		responseOrderSemen.setEmployeeID( __dTOResponseOrderSemen.getEmployeeID());
		responseOrderSemen.setRepliedBy( __dTOResponseOrderSemen.getRepliedBy());
		responseOrderSemen.setBillingURL( __dTOResponseOrderSemen.getBillingURL());
		return responseOrderSemen;
	}
}
