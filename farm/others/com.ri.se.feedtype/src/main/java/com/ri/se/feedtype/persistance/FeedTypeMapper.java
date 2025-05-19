package com.ri.se.feedtype.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class FeedTypeMapper implements ResultSetMapper<FeedType> {

	private static final String  feedName = "feedName";
	private static final String  creationDate = "creationDate";

	public FeedType map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new FeedType(resultSet.getString(feedName), resultSet.getTimestamp(creationDate));

	}
}
