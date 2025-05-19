package com.ri.se.feedpattern.dto;

import java.util.Date;
import com.ri.se.feedpattern.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class FeedPatternEntityConverter {

	public DTOFeedPattern getDTOFeedPattern(FeedPattern _feedPattern) throws Exception {
		DTOFeedPattern _dTOFeedPattern = new DTOFeedPattern();
		_dTOFeedPattern.setFpid(_feedPattern.getFpid());
		_dTOFeedPattern.setCertiOfIngredients(_feedPattern.getCertiOfIngredients());
		_dTOFeedPattern.setNotes(_feedPattern.getNotes());
		_dTOFeedPattern.setFeedName(_feedPattern.getFeedName());
		_dTOFeedPattern.setPrice(_feedPattern.getPrice());
		_dTOFeedPattern.setFeedType(_feedPattern.getFeedType());
		_dTOFeedPattern.setPercentage(_feedPattern.getPercentage());
		_dTOFeedPattern.setCreationDate(toString(_feedPattern.getCreationDate()));
		_dTOFeedPattern.setFoodSource(_feedPattern.getFoodSource());
		_dTOFeedPattern.setVolume(_feedPattern.getVolume());

		return _dTOFeedPattern;
	}

	private Date toDate(String dateStr) throws Exception {
		String full = "yyyy-MM-dd HH:mm:ss";
		String small = "yyyy-MM-dd";
		try {
			if (Objects.isNull(dateStr) || dateStr.length() == 0) {
				return null;
			}
			if (!dateStr.contains("-")) {
				throw new Exception(
						"Invalid date foramt. It should be either '" + small + "' or '" + full + "'format.");
			}
			if (!dateStr.contains(":")) {
				dateStr += " 00:00:00";
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(full);
			return simpleDateFormat.parse(dateStr);
		} catch (Exception exp) {
			throw new Exception("Invalid date foramt. It should be either '" + small + "' or '" + full + "'format.");
		}
	}

	private String toString(Date date) throws Exception {
		String full = "yyyy-MM-dd HH:mm:ss";
		String small = "yyyy-MM-dd";

		String smallTime = "00:00:00";

		if (Objects.isNull(date)) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(full);
			if (!simpleDateFormat.format(date).contains(smallTime))
				return simpleDateFormat.format(date);
			else {
				simpleDateFormat = new SimpleDateFormat(small);
				return simpleDateFormat.format(date);
			}
		} catch (Exception exp) {
			throw new Exception("Invalid date foramt.");
		}
	}

	public FeedPattern getFeedPattern(DTOFeedPattern __dTOFeedPattern) throws Exception {
		FeedPattern feedPattern = new FeedPattern();
		feedPattern.setFpid(__dTOFeedPattern.getFpid());
		feedPattern.setCertiOfIngredients(__dTOFeedPattern.getCertiOfIngredients());
		feedPattern.setNotes(__dTOFeedPattern.getNotes());
		feedPattern.setFeedName(__dTOFeedPattern.getFeedName());
		feedPattern.setPrice(__dTOFeedPattern.getPrice());
		feedPattern.setFeedType(__dTOFeedPattern.getFeedType());
		feedPattern.setPercentage(__dTOFeedPattern.getPercentage());
		feedPattern.setCreationDate(toDate(__dTOFeedPattern.getCreationDate()));
		feedPattern.setFoodSource(__dTOFeedPattern.getFoodSource());
		feedPattern.setVolume(__dTOFeedPattern.getVolume());
		return feedPattern;
	}
}
