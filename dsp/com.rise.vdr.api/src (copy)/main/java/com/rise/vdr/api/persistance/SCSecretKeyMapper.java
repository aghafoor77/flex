package com.rise.vdr.api.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SCSecretKeyMapper implements ResultSetMapper<SCSecretKey> {

	private static final String  ssid = "ssid";
	private static final String  eid = "eid";
	private static final String  did = "did";
	private static final String  key = "key";

	public SCSecretKey map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new SCSecretKey(resultSet.getString(ssid), resultSet.getString(eid), resultSet.getString(did), resultSet.getString(key));

	}
}
