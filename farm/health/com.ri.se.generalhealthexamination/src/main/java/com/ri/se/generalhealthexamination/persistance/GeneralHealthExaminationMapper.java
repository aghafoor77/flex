package com.ri.se.generalhealthexamination.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class GeneralHealthExaminationMapper implements ResultSetMapper<GeneralHealthExamination> {

	private static final String  gaheid = "gaheid";
	private static final String  observer = "observer";
	private static final String  wound = "wound";
	private static final String  notes = "notes";
	private static final String  notation = "notation";
	private static final String  temperature = "temperature";
	private static final String  infections = "infections";
	private static final String  lameness = "lameness";
	private static final String  swelling = "swelling";
	private static final String  gheDate = "gheDate";
	private static final String  animalID = "animalID";
	private static final String  weak = "weak";
	private static final String  reportType = "reportType";

	public GeneralHealthExamination map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new GeneralHealthExamination(resultSet.getString(gaheid), resultSet.getString(observer), resultSet.getString(wound), resultSet.getString(notes), resultSet.getString(notation), resultSet.getString(temperature), resultSet.getString(infections), resultSet.getString(lameness), resultSet.getString(swelling), resultSet.getTimestamp(gheDate), resultSet.getString(animalID), resultSet.getString(weak),resultSet.getString(reportType));

	}
}
