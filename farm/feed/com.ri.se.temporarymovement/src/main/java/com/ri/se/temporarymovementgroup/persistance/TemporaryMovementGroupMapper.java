package com.ri.se.temporarymovementgroup.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TemporaryMovementGroupMapper implements ResultSetMapper<TemporaryMovementGroup> {

	private static final String  tmgid = "tmgid";
	private static final String  outDate = "outDate";
	private static final String  tmid = "tmid";
	private static final String  inDate = "inDate";
	private static final String  employeeID = "employeeID";
	private static final String  animalID = "animalID";

	public TemporaryMovementGroup map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new TemporaryMovementGroup(resultSet.getString(tmgid), resultSet.getTimestamp(outDate), resultSet.getString(tmid), resultSet.getTimestamp(inDate), resultSet.getString(employeeID), resultSet.getString(animalID));

	}
}
