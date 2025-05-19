package com.rise.se.slaughterhouse.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class RAnimalSlaughterhouseDataMapper implements ResultSetMapper<RAnimalSlaughterhouseData> {

	private static final String  animalID = "animalID";
	private static final String  animalIDMother = "animalIDMother";
	private static final String  currentWeight = "currentWeight";
	private static final String  currentStatus = "currentStatus";
	private static final String  sex = "sex";
	private static final String  farmId = "farmId";
	private static final String  transferDate = "transferDate";
	private static final String  birthDate = "birthDate";
	private static final String  transactionID = "transactionID";
	private static final String  breed = "breed";
	private static final String  birthPlace = "birthPlace";
	private static final String  previousFarmContact = "previousFarmContact";
	private static final String  receivedFarmName = "receivedFarmName";

	public RAnimalSlaughterhouseData map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new RAnimalSlaughterhouseData(resultSet.getString(animalID), resultSet.getString(animalIDMother), resultSet.getInt(currentWeight), resultSet.getString(currentStatus), resultSet.getString(sex), resultSet.getString(farmId), resultSet.getString(transferDate), resultSet.getString(birthDate), resultSet.getString(transactionID), resultSet.getString(breed), resultSet.getString(birthPlace), resultSet.getString(previousFarmContact), resultSet.getString(receivedFarmName));

	}
}
