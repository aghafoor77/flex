package com.ri.se.semenutilization.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SemenUtilizationMapper implements ResultSetMapper<SemenUtilization> {

	private static final String  suid = "suid";
	private static final String  insemationDate = "insemationDate";
	private static final String  notes = "notes";
	private static final String  employeeID = "employeeID";
	private static final String  osid = "osid";
	private static final String  animalID = "animalID";

	public SemenUtilization map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new SemenUtilization(resultSet.getString(suid), resultSet.getTimestamp(insemationDate), resultSet.getString(notes), resultSet.getString(employeeID), resultSet.getString(osid), resultSet.getString(animalID));

	}
}
