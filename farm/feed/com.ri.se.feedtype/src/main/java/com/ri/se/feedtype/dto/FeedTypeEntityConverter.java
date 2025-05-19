package com.ri.se.feedtype.dto;

import java.util.Date;
import com.ri.se.feedtype.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class FeedTypeEntityConverter{

	public DTOFeedType getDTOFeedType(FeedType _feedType)  throws Exception {
		DTOFeedType _dTOFeedType = new DTOFeedType();
		_dTOFeedType.setFeedName( _feedType.getFeedName());
		_dTOFeedType.setCreationDate(toString( _feedType.getCreationDate()));
		return _dTOFeedType;
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


	public FeedType getFeedType(DTOFeedType __dTOFeedType)  throws Exception {
		FeedType feedType = new FeedType();
		feedType.setFeedName( __dTOFeedType.getFeedName());
		feedType.setCreationDate(toDate( __dTOFeedType.getCreationDate()));
		return feedType;
	}
}
