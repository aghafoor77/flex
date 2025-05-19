package com.ri.se.feedpattern.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class FeedPatternMapper implements ResultSetMapper<FeedPattern> {

	private static final String  fpid = "fpid";
	private static final String  certiOfIngredients = "certiOfIngredients";
	private static final String  notes = "notes";
	private static final String  feedName = "feedName";
	private static final String  price = "price";
	private static final String  feedType = "feedType";
	private static final String  percentage = "percentage";
	private static final String  creationDate = "creationDate";
	private static final String  foodSource = "foodSource";
	private static final String  volume = "volume";

	public FeedPattern map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new FeedPattern(resultSet.getString(fpid), resultSet.getString(certiOfIngredients), resultSet.getString(notes), resultSet.getString(feedName), resultSet.getString(price), resultSet.getString(feedType), resultSet.getString(percentage), resultSet.getTimestamp(creationDate), resultSet.getString(foodSource), resultSet.getDouble(volume ));

	}
}
