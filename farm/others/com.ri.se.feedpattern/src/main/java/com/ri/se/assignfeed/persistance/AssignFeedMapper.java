package com.ri.se.assignfeed.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AssignFeedMapper implements ResultSetMapper<AssignFeed> {

	private static final String  afid = "afid";
	private static final String  assignedDate = "assignedDate";
	private static final String  assignedBy = "assignedBy";
	private static final String  fpid = "fpid";
	private static final String  assignedTo = "assignedTo";

	public AssignFeed map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new AssignFeed(resultSet.getString(afid), resultSet.getTimestamp(assignedDate), resultSet.getString(assignedBy), resultSet.getString(fpid), resultSet.getString(assignedTo));

	}
}
