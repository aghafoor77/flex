package com.ri.se.animalreg.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class RegisterAnimalMapper implements ResultSetMapper<RegisterAnimal> {

	private static final String animalID = "animalID";
	private static final String receivedFarmID = "receivedFarmID";
	private static final String animalIDMother = "animalIDMother";
	private static final String notes = "notes";
	private static final String pregnancyExamination = "pregnancyExamination";
	private static final String sex = "sex";
	private static final String weight = "weight";
	private static final String dateOfBirth = "dateOfBirth";
	private static final String registrationDate = "registrationDate";
	private static final String breed = "breed";
	private static final String birthPlace = "birthPlace";
	private static final String employerID = "employerID";
	private static final String unit = "unit";
	private static final String receivedFarmName = "receivedFarmName";
	private static final String previousAnimalID = "previousAnimalID";
	private static final String aboutAnimal = "aboutAnimal";
	private static final String others = "others";
	private static final String status = "status";

	public RegisterAnimal map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new RegisterAnimal(resultSet.getString(animalID), resultSet.getString(receivedFarmID),
				resultSet.getString(animalIDMother), resultSet.getString(notes),
				resultSet.getString(pregnancyExamination), resultSet.getString(sex), resultSet.getInt(weight),
				resultSet.getTimestamp(dateOfBirth), resultSet.getTimestamp(registrationDate),
				resultSet.getString(breed), resultSet.getString(birthPlace), resultSet.getString(employerID),
				resultSet.getString(unit), resultSet.getString(receivedFarmName), resultSet.getString(previousAnimalID),
				resultSet.getString(aboutAnimal), resultSet.getString(others), resultSet.getString(status));
	}
}
