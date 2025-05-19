package com.rise.se.slaughterhouse.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class RSlaughterhouseDataMapper implements ResultSetMapper<RSlaughterhouseData> {

	private static final String  tripNo = "tripNo";
	private static final String  slaughterhousename = "slaughterhousename";
	private static final String  currentStatus = "currentStatus";
	private static final String  expectedArrivalDate = "expectedArrivalDate";
	private static final String  location = "location";
	private static final String  startDate = "startDate";

	public RSlaughterhouseData map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new RSlaughterhouseData(resultSet.getString(tripNo), resultSet.getString(slaughterhousename), resultSet.getString(currentStatus), resultSet.getLong(expectedArrivalDate), resultSet.getString(location), resultSet.getLong(startDate));

	}
}
