package com.ri.se.transferedanimal.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.ri.se.commonentities.transporter.DTOTransferedAnimal;
import com.ri.se.commonentities.transporter.DTOTransferedAnimalList;
import com.ri.se.transferedanimal.persistance.TransferedAnimal;

public class TransferedAnimalEntityConverter{

	public DTOTransferedAnimal getDTOTransferedAnimal(TransferedAnimal _transferedAnimal)  throws Exception {
		DTOTransferedAnimal _dTOTransferedAnimal = new DTOTransferedAnimal();
		_dTOTransferedAnimal.setAnimalID( _transferedAnimal.getAnimalID());
		_dTOTransferedAnimal.setAnimalIDMother( _transferedAnimal.getAnimalIDMother());
		_dTOTransferedAnimal.setBirthPlace( _transferedAnimal.getBirthPlace());
		_dTOTransferedAnimal.setPreviousFarmContact( _transferedAnimal.getPreviousFarmContact());
		_dTOTransferedAnimal.setCurrentWeight( _transferedAnimal.getCurrentWeight());
		_dTOTransferedAnimal.setReceivedFarmName( _transferedAnimal.getReceivedFarmName());
		_dTOTransferedAnimal.setSex( _transferedAnimal.getSex());
		_dTOTransferedAnimal.setFarmId( _transferedAnimal.getFarmId());
		_dTOTransferedAnimal.setTransferDate(toString( _transferedAnimal.getTransferDate()));
		_dTOTransferedAnimal.setBirthDate(toString( _transferedAnimal.getBirthDate()));
		_dTOTransferedAnimal.setBreed( _transferedAnimal.getBreed());
		_dTOTransferedAnimal.setTransactionID( _transferedAnimal.getTransactionID());
		_dTOTransferedAnimal.setTransactionTime( _transferedAnimal.getTransactionTime());
		_dTOTransferedAnimal.setCurrentStatus( _transferedAnimal.getCurrentStatus());
		
		return _dTOTransferedAnimal;
	}
	
	public DTOTransferedAnimalList toDTOTransferedAnimalList(List<TransferedAnimal> list) throws Exception {

		DTOTransferedAnimalList r = new DTOTransferedAnimalList();
		for(TransferedAnimal l: list) {
			r.add(getDTOTransferedAnimal(l));
		}
		return r;
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

	public TransferedAnimal getTransferedAnimal(DTOTransferedAnimal __dTOTransferedAnimal)  throws Exception {
		TransferedAnimal transferedAnimal = new TransferedAnimal();
		transferedAnimal.setAnimalID( __dTOTransferedAnimal.getAnimalID());
		transferedAnimal.setAnimalIDMother( __dTOTransferedAnimal.getAnimalIDMother());
		transferedAnimal.setBirthPlace( __dTOTransferedAnimal.getBirthPlace());
		transferedAnimal.setPreviousFarmContact( __dTOTransferedAnimal.getPreviousFarmContact());
		transferedAnimal.setCurrentWeight( __dTOTransferedAnimal.getCurrentWeight());
		transferedAnimal.setReceivedFarmName( __dTOTransferedAnimal.getReceivedFarmName());
		transferedAnimal.setSex( __dTOTransferedAnimal.getSex());
		transferedAnimal.setFarmId( __dTOTransferedAnimal.getFarmId());
		transferedAnimal.setTransferDate(toDate( __dTOTransferedAnimal.getTransferDate()));
		transferedAnimal.setBirthDate(toDate( __dTOTransferedAnimal.getBirthDate()));
		transferedAnimal.setBreed( __dTOTransferedAnimal.getBreed());
		transferedAnimal.setTransactionID( __dTOTransferedAnimal.getTransactionID());
		transferedAnimal.setTransactionTime( __dTOTransferedAnimal.getTransactionTime());
		transferedAnimal.setCurrentStatus( __dTOTransferedAnimal.getCurrentStatus());
		return transferedAnimal;
	}
}
