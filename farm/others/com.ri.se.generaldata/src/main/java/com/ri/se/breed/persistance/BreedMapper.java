package com.ri.se.breed.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class BreedMapper implements ResultSetMapper<Breed> {

	private static final String  breedname = "breedname";

	public Breed map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Breed(resultSet.getString(breedname));

	}
}
