package com.ri.se.animalexamination.persistance;

import java.util.List;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.ri.se.animalexamination.utils.AbstractHealthCheck;

public abstract class AnimalExaminationService  extends AbstractHealthCheck {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract AnimalExaminationDAO dao();

	public void create() {
		dao().create();
	}

	public int post(AnimalExamination animalexamination_){

		return dao().post(animalexamination_);
	}
	public int put(AnimalExamination animalexamination_){

		return dao().put(animalexamination_);
	}
	public List<AnimalExamination> list(){

		return dao().list();
	}
	
	public List<AnimalExamination> getByAnimalID(String animalID){
		return dao().getByAnimalID(animalID);
	}
	
	public AnimalExamination get(String aeid){

		return dao().get(aeid);
	}
	public int delete(String aeid){

		return dao().delete(aeid);
	}
	public List<AnimalExamination> getListByStatus(String status){
		return dao().getListByStatus(status);
	}	
	public List<AnimalExamination> getListByAnimalAndStatus(String animalID, String status){
		return dao().getListByAnimalAndStatus(animalID, status);
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
