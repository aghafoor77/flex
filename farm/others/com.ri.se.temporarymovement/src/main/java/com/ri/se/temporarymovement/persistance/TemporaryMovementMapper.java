package com.ri.se.temporarymovement.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TemporaryMovementMapper implements ResultSetMapper<TemporaryMovement> {

	private static final String  tmid = "tmid";
	private static final String  outDate = "outDate";
	private static final String  notes = "notes";
	private static final String  purpose = "purpose";
	private static final String  inDate = "inDate";
	private static final String  tmType = "tmType";
	private static final String  employeeID = "employeeID";

	public TemporaryMovement map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new TemporaryMovement(resultSet.getString(tmid), resultSet.getTimestamp(outDate), resultSet.getString(notes), resultSet.getString(purpose), resultSet.getTimestamp(inDate), resultSet.getString(tmType), resultSet.getString(employeeID));

	}
}
