package transportedcattle.dto;

import java.util.Date;
import transportedcattle.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class TransportedCattleEntityConverter{

	public DTOTransportedCattle getDTOTransportedCattle(TransportedCattle _transportedCattle)  throws Exception {
		DTOTransportedCattle _dTOTransportedCattle = new DTOTransportedCattle();
		_dTOTransportedCattle.setTCID( _transportedCattle.getTCID());
		_dTOTransportedCattle.setEnteranceRecord( _transportedCattle.getEnteranceRecord());
		_dTOTransportedCattle.setNotes( _transportedCattle.getNotes());
		_dTOTransportedCattle.setGHEID( _transportedCattle.getGHEID());
		_dTOTransportedCattle.setOtherData( _transportedCattle.getOtherData());
		_dTOTransportedCattle.setDestination( _transportedCattle.getDestination());
		_dTOTransportedCattle.setTemperature( _transportedCattle.getTemperature());
		_dTOTransportedCattle.setDepartDate(toString( _transportedCattle.getDepartDate()));
		_dTOTransportedCattle.setTransportType( _transportedCattle.getTransportType());
		_dTOTransportedCattle.setSource( _transportedCattle.getSource());
		_dTOTransportedCattle.setAnimalID( _transportedCattle.getAnimalID());
		_dTOTransportedCattle.setCarriernumber( _transportedCattle.getCarriernumber());
		return _dTOTransportedCattle;
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


	public TransportedCattle getTransportedCattle(DTOTransportedCattle __dTOTransportedCattle)  throws Exception {
		TransportedCattle transportedCattle = new TransportedCattle();
		transportedCattle.setTCID( __dTOTransportedCattle.getTCID());
		transportedCattle.setEnteranceRecord( __dTOTransportedCattle.getEnteranceRecord());
		transportedCattle.setNotes( __dTOTransportedCattle.getNotes());
		transportedCattle.setGHEID( __dTOTransportedCattle.getGHEID());
		transportedCattle.setOtherData( __dTOTransportedCattle.getOtherData());
		transportedCattle.setDestination( __dTOTransportedCattle.getDestination());
		transportedCattle.setTemperature( __dTOTransportedCattle.getTemperature());
		transportedCattle.setDepartDate(toDate( __dTOTransportedCattle.getDepartDate()));
		transportedCattle.setTransportType( __dTOTransportedCattle.getTransportType());
		transportedCattle.setSource( __dTOTransportedCattle.getSource());
		transportedCattle.setAnimalID( __dTOTransportedCattle.getAnimalID());
		transportedCattle.setCarriernumber( __dTOTransportedCattle.getCarriernumber());
		return transportedCattle;
	}
}
