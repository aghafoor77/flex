package carrier.dto;

import java.util.Date;
import carrier.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class CarrierEntityConverter{

	public DTOCarrier getDTOCarrier(Carrier _carrier)  throws Exception {
		DTOCarrier _dTOCarrier = new DTOCarrier();
		_dTOCarrier.setCID( _carrier.getCID());
		_dTOCarrier.setCarrierNumber( _carrier.getCarrierNumber());
		_dTOCarrier.setSpecies( _carrier.getSpecies());
		_dTOCarrier.setTransportType( _carrier.getTransportType());
		_dTOCarrier.setTID( _carrier.getTID());
		_dTOCarrier.setLongDistance( _carrier.getLongDistance());
		return _dTOCarrier;
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


	public Carrier getCarrier(DTOCarrier __dTOCarrier)  throws Exception {
		Carrier carrier = new Carrier();
		carrier.setCID( __dTOCarrier.getCID());
		carrier.setCarrierNumber( __dTOCarrier.getCarrierNumber());
		carrier.setSpecies( __dTOCarrier.getSpecies());
		carrier.setTransportType( __dTOCarrier.getTransportType());
		carrier.setTID( __dTOCarrier.getTID());
		carrier.setLongDistance( __dTOCarrier.getLongDistance());
		return carrier;
	}
}
