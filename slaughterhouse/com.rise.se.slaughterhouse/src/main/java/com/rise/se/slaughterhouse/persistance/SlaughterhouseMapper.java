package com.rise.se.slaughterhouse.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SlaughterhouseMapper implements ResultSetMapper<Slaughterhouse> {

	private static final String  sid = "sid";

	public Slaughterhouse map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Slaughterhouse(resultSet.getString(sid));

	}
}
