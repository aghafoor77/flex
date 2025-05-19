package com.ri.se.assignanimal.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AssignAnimalMapper implements ResultSetMapper<AssignAnimal> {

	private static final String  aaid = "aaid";
	private static final String  assignedDate = "assignedDate";
	private static final String  notes = "notes";
	private static final String  examiner = "examiner";
	private static final String  action = "action";
	private static final String  assignedBy = "assignedBy";
	

	public AssignAnimal map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new AssignAnimal(resultSet.getString(aaid), resultSet.getTimestamp(assignedDate), resultSet.getString(notes), resultSet.getString(examiner), resultSet.getString(action), resultSet.getString(assignedBy));

	}
}
