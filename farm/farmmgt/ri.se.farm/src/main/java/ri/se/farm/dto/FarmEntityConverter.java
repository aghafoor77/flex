package ri.se.farm.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import ri.se.farm.persistance.Farm;

public class FarmEntityConverter{

	public DTOFarm getDTOFarm(Farm _farm)  throws Exception {
		DTOFarm _dTOFarm = new DTOFarm();
		_dTOFarm.setFarmId( _farm.getFarmId());
		_dTOFarm.setFarmName( _farm.getFarmName());
		_dTOFarm.setOwner( _farm.getOwner());
		_dTOFarm.setDateTime(toString( _farm.getDateTime()));
		_dTOFarm.setCountry( _farm.getCountry());
		_dTOFarm.setNotes( _farm.getNotes());
		_dTOFarm.setCity( _farm.getCity());
		_dTOFarm.setStreet( _farm.getStreet());
		_dTOFarm.setPostalcode( _farm.getPostalcode());
		_dTOFarm.setFarmContactNo( _farm.getFarmContactNo());
		_dTOFarm.setFarmCompany( _farm.getFarmCompany());
		_dTOFarm.setEmail( _farm.getEmail());
		return _dTOFarm;
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


	public Farm getFarm(DTOFarm __dTOFarm)  throws Exception {
		Farm farm = new Farm();
		farm.setFarmId( __dTOFarm.getFarmId());
		farm.setFarmName( __dTOFarm.getFarmName());
		farm.setOwner( __dTOFarm.getOwner());
		farm.setDateTime(toDate( __dTOFarm.getDateTime()));
		farm.setCountry( __dTOFarm.getCountry());
		farm.setNotes( __dTOFarm.getNotes());
		farm.setCity( __dTOFarm.getCity());
		farm.setStreet( __dTOFarm.getStreet());
		farm.setPostalcode( __dTOFarm.getPostalcode());
		farm.setFarmContactNo( __dTOFarm.getFarmContactNo());
		farm.setFarmCompany( __dTOFarm.getFarmCompany());
		farm.setEmail( __dTOFarm.getEmail());
		return farm;
	}
}
