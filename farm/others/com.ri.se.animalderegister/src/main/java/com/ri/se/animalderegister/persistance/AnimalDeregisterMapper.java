package com.ri.se.animalderegister.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AnimalDeregisterMapper implements ResultSetMapper<AnimalDeregister> {

	private static final String  animalID = "animalID";
	private static final String  deregisterDate = "deregisterDate";
	private static final String  reportBy = "reportBy";
	private static final String  notes = "notes";
	private static final String  reportTo = "reportTo";
	private static final String  response = "response";
	private static final String  location = "location";
	private static final String  emailTo = "emailTo";
	private static final String  dcode = "dcode";

	public AnimalDeregister map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new AnimalDeregister(resultSet.getString(animalID), resultSet.getTimestamp(deregisterDate), resultSet.getString(reportBy), resultSet.getString(notes), resultSet.getString(reportTo), resultSet.getString(response), resultSet.getString(location), resultSet.getString(emailTo), resultSet.getString(dcode));

	}
}
