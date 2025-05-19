package com.ri.se.transferedanimal.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TransferedAnimalMapper implements ResultSetMapper<TransferedAnimal> {

	private static final String  animalID = "animalID";
	private static final String  animalIDMother = "animalIDMother";
	private static final String  birthPlace = "birthPlace";
	private static final String  previousFarmContact = "previousFarmContact";
	private static final String  currentWeight = "currentWeight";
	private static final String  receivedFarmName = "receivedFarmName";
	private static final String  sex = "sex";
	private static final String  farmId = "farmId";
	private static final String  transferDate = "transferDate";
	private static final String  birthDate = "birthDate";
	private static final String  breed = "breed";
	private static final String  transactionID = "transactionID";
	private static final String  transactionTime = "transactionTime";
	private static final String  currentStatus="currentStatus";
	
	public TransferedAnimal map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new TransferedAnimal(resultSet.getString(animalID), resultSet.getString(animalIDMother), resultSet.getString(birthPlace), resultSet.getString(previousFarmContact), resultSet.getInt(currentWeight), resultSet.getString(receivedFarmName), resultSet.getString(sex), resultSet.getString(farmId), resultSet.getTimestamp(transferDate), resultSet.getTimestamp(birthDate), resultSet.getString(breed), resultSet.getString(transactionID),resultSet.getLong(transactionTime), resultSet.getString(currentStatus));

	}
}
