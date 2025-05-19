package com.rise.carrier.slaughterhouse.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SHCarrierMapper implements ResultSetMapper<SHCarrier> {

	private static final String  tripNo = "tripNo";
	private static final String  carrierNumber = "carrierNumber";
	private static final String  carrierId = "carrierId";
	private static final String  CID = "CID";

	public SHCarrier map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new SHCarrier(resultSet.getString(tripNo), resultSet.getString(carrierNumber), resultSet.getString(carrierId), resultSet.getString(CID));

	}
}
