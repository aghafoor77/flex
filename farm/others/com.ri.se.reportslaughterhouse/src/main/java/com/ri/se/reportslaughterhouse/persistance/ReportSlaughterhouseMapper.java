package com.ri.se.reportslaughterhouse.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ReportSlaughterhouseMapper implements ResultSetMapper<ReportSlaughterhouse> {

	private static final String  rid = "rid";
	private static final String  notes = "notes";
	private static final String  slaughterhousename = "slaughterhousename";
	private static final String  response = "response";
	private static final String  sex = "sex";
	private static final String  numberofanimals = "numberofanimals";
	private static final String  reportingDate = "reportingDate";
	private static final String  employeeID = "employeeID";
	private static final String  slaughterhousecontact = "slaughterhousecontact";
	private static final String  age = "age";
	private static final String  breed = "breed";

	public ReportSlaughterhouse map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new ReportSlaughterhouse(resultSet.getString(rid), resultSet.getString(notes), resultSet.getString(slaughterhousename), resultSet.getString(response), resultSet.getString(sex), resultSet.getInt(numberofanimals), resultSet.getTimestamp(reportingDate), resultSet.getString(employeeID), resultSet.getString(slaughterhousecontact), resultSet.getString(age), resultSet.getString(breed));

	}
}
