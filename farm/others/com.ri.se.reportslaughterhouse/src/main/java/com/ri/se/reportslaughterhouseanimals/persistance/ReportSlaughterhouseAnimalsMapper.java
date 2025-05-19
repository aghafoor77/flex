package com.ri.se.reportslaughterhouseanimals.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ReportSlaughterhouseAnimalsMapper implements ResultSetMapper<ReportSlaughterhouseAnimals> {

	private static final String  rsaid = "rsaid";
	private static final String  selectionDate = "selectionDate";
	private static final String  employeeID = "employeeID";
	private static final String  rid = "rid";
	private static final String  animalID = "animalID";

	public ReportSlaughterhouseAnimals map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new ReportSlaughterhouseAnimals(resultSet.getString(rsaid), resultSet.getTimestamp(selectionDate), resultSet.getString(employeeID), resultSet.getString(rid), resultSet.getString(animalID));

	}
}
