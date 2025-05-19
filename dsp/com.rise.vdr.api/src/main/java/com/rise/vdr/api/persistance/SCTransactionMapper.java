package com.rise.vdr.api.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SCTransactionMapper implements ResultSetMapper<SCTransaction> {

	private static final String  transactionID = "transactionID";
	private static final String  transactiondDate = "transactiondDate";
	private static final String  trasactionReceiver = "trasactionReceiver";
	private static final String  tarnsactionOwner = "tarnsactionOwner";
	private static final String  animalID = "animalID";

	public SCTransaction map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new SCTransaction(resultSet.getString(transactionID), resultSet.getLong(transactiondDate), resultSet.getString(trasactionReceiver), resultSet.getString(tarnsactionOwner), resultSet.getString(animalID));

	}
}
