package transporter.dto;

import java.util.Date;
import transporter.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class TransporterEntityConverter{

	public DTOTransporter getDTOTransporter(Transporter _transporter)  throws Exception {
		DTOTransporter _dTOTransporter = new DTOTransporter();
		_dTOTransporter.setTID( _transporter.getTID());
		_dTOTransporter.setNotes( _transporter.getNotes());
		_dTOTransporter.setAnimalPeryear( _transporter.getAnimalPeryear());
		_dTOTransporter.setOwnerId( _transporter.getOwnerId());
		_dTOTransporter.setIsOrganization( _transporter.getIsOrganization());
		return _dTOTransporter;
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


	public Transporter getTransporter(DTOTransporter __dTOTransporter)  throws Exception {
		Transporter transporter = new Transporter();
		transporter.setTID( __dTOTransporter.getTID());
		transporter.setNotes( __dTOTransporter.getNotes());
		transporter.setAnimalPeryear( __dTOTransporter.getAnimalPeryear());
		transporter.setOwnerId( __dTOTransporter.getOwnerId());
		transporter.setIsOrganization( __dTOTransporter.getIsOrganization());
		return transporter;
	}
}
