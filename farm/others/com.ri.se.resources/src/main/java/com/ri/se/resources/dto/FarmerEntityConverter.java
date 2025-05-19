package com.ri.se.resources.dto;

import java.util.Date;
import com.ri.se.resources.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class FarmerEntityConverter{

	public DTOFarmer getDTOFarmer(Farmer _farmer)  throws Exception {
		DTOFarmer _dTOFarmer = new DTOFarmer();
		_dTOFarmer.setResourceId( _farmer.getResourceId());
		_dTOFarmer.setCountry( _farmer.getCountry());
		_dTOFarmer.setRole( _farmer.getRole());
		_dTOFarmer.setStreet( _farmer.getStreet());
		_dTOFarmer.setContact( _farmer.getContact());
		_dTOFarmer.setName( _farmer.getName());
		_dTOFarmer.setPostcode( _farmer.getPostcode());
		_dTOFarmer.setCounty( _farmer.getCounty());
		_dTOFarmer.setMunicipality( _farmer.getMunicipality());
		_dTOFarmer.setFarmId( _farmer.getFarmId());
		_dTOFarmer.setEmail( _farmer.getEmail());
		return _dTOFarmer;
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


	public Farmer getFarmer(DTOFarmer __dTOFarmer)  throws Exception {
		Farmer farmer = new Farmer();
		farmer.setResourceId( __dTOFarmer.getResourceId());
		farmer.setCountry( __dTOFarmer.getCountry());
		farmer.setRole( __dTOFarmer.getRole());
		farmer.setStreet( __dTOFarmer.getStreet());
		farmer.setContact( __dTOFarmer.getContact());
		farmer.setName( __dTOFarmer.getName());
		farmer.setPostcode( __dTOFarmer.getPostcode());
		farmer.setCounty( __dTOFarmer.getCounty());
		farmer.setMunicipality( __dTOFarmer.getMunicipality());
		farmer.setFarmId( __dTOFarmer.getFarmId());
		farmer.setEmail( __dTOFarmer.getEmail());
		return farmer;
	}
}
