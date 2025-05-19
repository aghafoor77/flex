package com.ri.se.assignanimalstatus.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AssignAnimalStatusMapper implements ResultSetMapper<AssignAnimalStatus> {

	private static final String  aasid = "aasid";
	private static final String  aaid = "aaid";
	private static final String  submissionDate = "submissionDate";
	private static final String  animals = "animals";
	private static final String  reportReference = "reportReference";
	private static final String  reportSubmitted = "reportSubmitted";

	public AssignAnimalStatus map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new AssignAnimalStatus(resultSet.getString(aasid), resultSet.getString(aaid), resultSet.getTimestamp(submissionDate), resultSet.getString(animals), resultSet.getString(reportReference), resultSet.getString(reportSubmitted));

	}
}
