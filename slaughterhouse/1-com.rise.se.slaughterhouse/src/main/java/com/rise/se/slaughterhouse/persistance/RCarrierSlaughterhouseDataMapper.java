package com.rise.se.slaughterhouse.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class RCarrierSlaughterhouseDataMapper implements ResultSetMapper<RCarrierSlaughterhouseData> {

	private static final String  carrierId = "carrierId";
	private static final String  carrierNumber = "carrierNumber";
	private static final String  tripNo = "tripNo";
	private static final String  CID = "CID";

	public RCarrierSlaughterhouseData map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new RCarrierSlaughterhouseData(resultSet.getString(carrierId), resultSet.getString(carrierNumber), resultSet.getString(tripNo), resultSet.getString(CID));

	}
}
