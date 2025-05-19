package com.ri.se.animalexamination.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AnimalExaminationMapper implements ResultSetMapper<AnimalExamination> {

	private static final String  aeid = "aeid";
	private static final String  notes = "notes";
	private static final String  examinationDate = "examinationDate";
	private static final String  refnumber = "refnumber";
	private static final String  refType = "refType";
	private static final String  sensorData = "sensorData";
	private static final String  employeeID = "employeeID";
	private static final String  animalID = "animalID";
	private static final String  extepctedDate = "extepctedDate";
	private static final String  status = "status";

	public AnimalExamination map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new AnimalExamination(resultSet.getString(aeid), resultSet.getString(notes), resultSet.getTimestamp(examinationDate), resultSet.getString(refnumber), resultSet.getString(refType), resultSet.getString(sensorData), resultSet.getString(employeeID), resultSet.getString(animalID), resultSet.getTimestamp(extepctedDate), resultSet.getString(status));

	}
}
