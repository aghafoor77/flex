package com.ri.se.users.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class UsersMapper implements ResultSetMapper<Users> {

	private static final String  veid = "veid";
	private static final String  email = "email";
	private static final String  password = "password";
	private static final String  roles = "roles";

	public Users map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Users(resultSet.getString(veid), resultSet.getString(email), resultSet.getString(password), resultSet.getString(roles));
	}
}
