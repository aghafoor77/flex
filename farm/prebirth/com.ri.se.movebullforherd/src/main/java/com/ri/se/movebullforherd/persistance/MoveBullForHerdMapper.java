package com.ri.se.movebullforherd.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class MoveBullForHerdMapper implements ResultSetMapper<MoveBullForHerd> {

	private static final String  mb4hid = "mb4hid";
	private static final String  notes = "notes";
	private static final String  entryDate = "entryDate";
	private static final String  groupFemale = "groupFemale";
	private static final String  employeeID = "employeeID";
	private static final String  location = "location";
	private static final String  animalID = "animalID";
	private static final String  exitDate = "exitDate";

	public MoveBullForHerd map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new MoveBullForHerd(resultSet.getString(mb4hid), resultSet.getString(notes), resultSet.getTimestamp(entryDate), resultSet.getString(groupFemale), resultSet.getString(employeeID), resultSet.getString(location), resultSet.getString(animalID), resultSet.getTimestamp(exitDate));

	}
}
