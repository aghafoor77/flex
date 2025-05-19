package com.ri.se.responseordersemen.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ResponseOrderSemenMapper implements ResultSetMapper<ResponseOrderSemen> {

	private static final String  osid = "osid";
	private static final String  notes = "notes";
	private static final String  resDate = "resDate";
	private static final String  employeeID = "employeeID";
	private static final String  repliedBy = "repliedBy";
	private static final String  billingURL = "billingURL";

	public ResponseOrderSemen map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new ResponseOrderSemen(resultSet.getString(osid), resultSet.getString(notes), resultSet.getTimestamp(resDate), resultSet.getString(employeeID), resultSet.getString(repliedBy), resultSet.getString(billingURL));

	}
}
