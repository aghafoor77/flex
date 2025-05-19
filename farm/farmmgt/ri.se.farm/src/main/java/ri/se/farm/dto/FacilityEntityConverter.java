package ri.se.farm.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ri.se.farm.persistance.Facility;

public class FacilityEntityConverter{

	public DTOFacility getDTOFacility(Facility _facility)  throws Exception {
		DTOFacility _dTOFacility = new DTOFacility();
		_dTOFacility.setFacilityId(_facility.getFacilityId());
		_dTOFacility.setAddress( _facility.getAddress());
		_dTOFacility.setMaxcapacity( _facility.getMaxcapacity());
		_dTOFacility.setTemporaryactivity( _facility.getTemporaryactivity());
		_dTOFacility.setAnlaggningsnumber( _facility.getAnlaggningsnumber());
		_dTOFacility.setFarmId( _facility.getFarmId());
		_dTOFacility.setType( _facility.getType());
		_dTOFacility.setAreasize( _facility.getAreasize());
		_dTOFacility.setFromDate(toString( _facility.getFromDate()));
		_dTOFacility.setMovementacrossEU( _facility.getMovementacrossEU());
		_dTOFacility.setFacilityNr( _facility.getFacilityNr());
		_dTOFacility.setSpecieskept( _facility.getSpecieskept());
		_dTOFacility.setBreedingmaterials( _facility.getBreedingmaterials());
		return _dTOFacility;
	}
	
	public List<DTOFacility> getDTOFacility(List<Facility> _facility)  throws Exception {
		List<DTOFacility> listDTOFacility = new ArrayList();
		for(Facility  fac: _facility) {
			listDTOFacility.add(getDTOFacility(fac));
		}
		return listDTOFacility;
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


	public Facility getFacility(DTOFacility __dTOFacility)  throws Exception {
		Facility facility = new Facility();
		facility.setFacilityId( __dTOFacility.getFacilityId());
		facility.setAddress( __dTOFacility.getAddress());
		facility.setMaxcapacity( __dTOFacility.getMaxcapacity());
		facility.setTemporaryactivity( __dTOFacility.getTemporaryactivity());
		facility.setAnlaggningsnumber( __dTOFacility.getAnlaggningsnumber());
		facility.setFarmId( __dTOFacility.getFarmId());
		facility.setType( __dTOFacility.getType());
		facility.setAreasize( __dTOFacility.getAreasize());
		facility.setFromDate(toDate( __dTOFacility.getFromDate()));
		facility.setMovementacrossEU( __dTOFacility.getMovementacrossEU());
		facility.setFacilityNr( __dTOFacility.getFacilityNr());
		facility.setSpecieskept( __dTOFacility.getSpecieskept());
		facility.setBreedingmaterials( __dTOFacility.getBreedingmaterials());
		return facility;
	}
}
