package com.ri.se.ordersemen.dto;

import java.util.Date;
import com.ri.se.ordersemen.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class OrderSemenEntityConverter{

	public DTOOrderSemen getDTOOrderSemen(OrderSemen _orderSemen)  throws Exception {
		DTOOrderSemen _dTOOrderSemen = new DTOOrderSemen();
		_dTOOrderSemen.setOsid( _orderSemen.getOsid());
		_dTOOrderSemen.setNotes( _orderSemen.getNotes());
		_dTOOrderSemen.setContact( _orderSemen.getContact());
		_dTOOrderSemen.setOrderedTo( _orderSemen.getOrderedTo());
		_dTOOrderSemen.setEmployeeID( _orderSemen.getEmployeeID());
		_dTOOrderSemen.setEmailto( _orderSemen.getEmailto());
		_dTOOrderSemen.setFarmID( _orderSemen.getFarmID());
		String tempDate = toString(_orderSemen.getOrderDate());
		_dTOOrderSemen.setOrderDate((Objects.isNull(tempDate)?"-":tempDate));
		_dTOOrderSemen.setBreed( _orderSemen.getBreed());
		_dTOOrderSemen.setStatus( _orderSemen.getStatus());
		return _dTOOrderSemen;
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


	public OrderSemen getOrderSemen(DTOOrderSemen __dTOOrderSemen)  throws Exception {
		OrderSemen orderSemen = new OrderSemen();
		orderSemen.setOsid( __dTOOrderSemen.getOsid());
		orderSemen.setNotes( __dTOOrderSemen.getNotes());
		orderSemen.setContact( __dTOOrderSemen.getContact());
		orderSemen.setOrderedTo( __dTOOrderSemen.getOrderedTo());
		orderSemen.setEmployeeID( __dTOOrderSemen.getEmployeeID());
		orderSemen.setEmailto( __dTOOrderSemen.getEmailto());
		orderSemen.setFarmID( __dTOOrderSemen.getFarmID());
		orderSemen.setOrderDate(toDate( __dTOOrderSemen.getOrderDate()));
		orderSemen.setBreed( __dTOOrderSemen.getBreed());
		orderSemen.setStatus( __dTOOrderSemen.getStatus());
		return orderSemen;
	}
}
