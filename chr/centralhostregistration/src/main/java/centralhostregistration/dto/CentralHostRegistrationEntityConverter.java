package centralhostregistration.dto;

import java.util.Date;
import centralhostregistration.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class CentralHostRegistrationEntityConverter{

	public DTOCentralHostRegistration getDTOCentralHostRegistration(CentralHostRegistration _centralHostRegistration)  throws Exception {
		DTOCentralHostRegistration _dTOCentralHostRegistration = new DTOCentralHostRegistration();
		_dTOCentralHostRegistration.setName( _centralHostRegistration.getName());
		_dTOCentralHostRegistration.setAddress( _centralHostRegistration.getAddress());
		_dTOCentralHostRegistration.setPort( _centralHostRegistration.getPort());
		_dTOCentralHostRegistration.setHealthCheck( _centralHostRegistration.getHealthCheck());
		_dTOCentralHostRegistration.setRegistrationDate(toString( _centralHostRegistration.getRegistrationDate()));
		_dTOCentralHostRegistration.setStatus( _centralHostRegistration.getStatus());
		_dTOCentralHostRegistration.setType( _centralHostRegistration.getType());
		return _dTOCentralHostRegistration;
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


	public CentralHostRegistration getCentralHostRegistration(DTOCentralHostRegistration __dTOCentralHostRegistration)  throws Exception {
		CentralHostRegistration centralHostRegistration = new CentralHostRegistration();
		centralHostRegistration.setName( __dTOCentralHostRegistration.getName());
		centralHostRegistration.setAddress( __dTOCentralHostRegistration.getAddress());
		centralHostRegistration.setPort( __dTOCentralHostRegistration.getPort());
		centralHostRegistration.setHealthCheck( __dTOCentralHostRegistration.getHealthCheck());
		centralHostRegistration.setRegistrationDate(toDate( __dTOCentralHostRegistration.getRegistrationDate()));
		centralHostRegistration.setStatus( __dTOCentralHostRegistration.getStatus());
		centralHostRegistration.setType( __dTOCentralHostRegistration.getType());
		return centralHostRegistration;
	}
}
