package analytics.dto;

import java.util.Date;
import analytics.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class AnalyticsEntityConverter{

	public DTOAnalytics getDTOAnalytics(Analytics _analytics)  throws Exception {
		DTOAnalytics _dTOAnalytics = new DTOAnalytics();
		_dTOAnalytics.setAID( _analytics.getAID());
		_dTOAnalytics.setADG( _analytics.getADG());
		return _dTOAnalytics;
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


	public Analytics getAnalytics(DTOAnalytics __dTOAnalytics)  throws Exception {
		Analytics analytics = new Analytics();
		analytics.setAID( __dTOAnalytics.getAID());
		analytics.setADG( __dTOAnalytics.getADG());
		return analytics;
	}
}
