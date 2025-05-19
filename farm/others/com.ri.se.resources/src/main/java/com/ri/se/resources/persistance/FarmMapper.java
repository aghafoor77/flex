package com.ri.se.resources.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class FarmMapper implements ResultSetMapper<Farm> {

	private static final String  farmId = "farmId";
	private static final String  farmName = "farmName";
	private static final String  owner = "owner";
	private static final String  dateTime = "dateTime";
	private static final String  country = "country";
	private static final String  notes = "notes";
	private static final String  city = "city";
	private static final String  street = "street";
	private static final String  postalcode = "postalcode";
	private static final String  farmContactNo = "farmContactNo";
	private static final String  farmCompany = "farmCompany";
	private static final String  email = "email";

	public Farm map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Farm(resultSet.getString(farmId), resultSet.getString(farmName), resultSet.getString(owner), resultSet.getString(dateTime), resultSet.getString(country), resultSet.getString(notes), resultSet.getString(city), resultSet.getString(street), resultSet.getString(postalcode), resultSet.getString(farmContactNo), resultSet.getString(farmCompany), resultSet.getString(email));

	}
}
