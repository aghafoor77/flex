package com.rise.vdr.api.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SCEvidenceMapper implements ResultSetMapper<SCEvidence> {

	private static final String  eid = "eid";
	private static final String  accessLevel = "accessLevel";
	private static final String  resType = "resType";
	private static final String  link = "link";
	private static final String  transactionID = "transactionID";

	public SCEvidence map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new SCEvidence(resultSet.getString(eid), resultSet.getString(accessLevel), resultSet.getString(resType), resultSet.getString(link), resultSet.getString(transactionID));

	}
}
