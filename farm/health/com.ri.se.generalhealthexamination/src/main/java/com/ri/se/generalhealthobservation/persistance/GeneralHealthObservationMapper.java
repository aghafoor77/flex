package com.ri.se.generalhealthobservation.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class GeneralHealthObservationMapper implements ResultSetMapper<GeneralHealthObservation> {

	private static final String  ghoid = "ghoid";
	private static final String  limping = "limping";
	private static final String  observer = "observer";
	private static final String  wound = "wound";
	private static final String  bcs = "bcs";
	private static final String  notes = "notes";
	private static final String  swelling = "swelling";
	private static final String  gheDate = "gheDate";
	private static final String  animalID = "animalID";
	private static final String  weight = "weight";

	public GeneralHealthObservation map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new GeneralHealthObservation(resultSet.getString(ghoid), resultSet.getString(limping), resultSet.getString(observer), resultSet.getString(wound), resultSet.getString(bcs), resultSet.getString(notes), resultSet.getString(swelling), resultSet.getTimestamp(gheDate), resultSet.getString(animalID), resultSet.getString(weight));

	}
}
