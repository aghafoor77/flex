package com.rise.vdr.api.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SCResourceMapper implements ResultSetMapper<SCResource> {

	private static final String  did = "did";
	private static final String  role = "role";
	private static final String  publickey = "publickey";
	private static final String  company = "company";

	public SCResource map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new SCResource(resultSet.getString(did), resultSet.getString(role), resultSet.getString(publickey), resultSet.getString(company));

	}
}
