package com.ri.se.transferedanimal.persistance;

import java.util.List;
import java.util.Objects;
import java.util.Date;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

public abstract class TransferedAnimalService {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract TransferedAnimalDAO dao();

	public void create() {
		dao().create();
	}

	public int post(TransferedAnimal transferedanimal_){

		return dao().post(transferedanimal_);
	}
	public int put(TransferedAnimal transferedanimal_){

		return dao().put(transferedanimal_);
	}
	public List<TransferedAnimal> list(){

		return dao().list();
	}
	public List<TransferedAnimal> listAvaiable(){
		return dao().listAvaiable();
	}
	public TransferedAnimal get(String animalID){

		return dao().get(animalID);
	}
	public long getLatest(){
		return dao().getLatest();
	}
	public int delete(String animalID){

		return dao().delete(animalID);
	}
	
	public List<TransferedAnimal> getAnimalAsCurrentStatus(String status) {
		return dao().getAnimalAsCurrentStatus(status); 
	}
	
	
	public String performHealthCheck() {
		try {
			dao().list();
		} catch (UnableToObtainConnectionException ex) {
			return checkUnableToObtainConnectionException(ex);
		} catch (UnableToExecuteStatementException ex) {
			return checkUnableToExecuteStatementException(ex);
		} catch (Exception ex) {
			return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
		}
		return null;
	}
		private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
			if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
				return DATABASE_REACH_ERROR + ex.getCause().getLocalizedMessage();
		} else if (ex.getCause() instanceof java.sql.SQLException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
		}
	}
	private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
		if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
		}
	}
}
