package com.ri.se.generalhealthexamination.persistance;

import java.util.List;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.ri.se.generalhealthobservation.utils.AbstractHealthCheck;

public abstract class GeneralHealthExaminationService  extends AbstractHealthCheck  {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract GeneralHealthExaminationDAO dao();

	public void create() {
		dao().create();
	}

	public int post(GeneralHealthExamination generalhealthexamination_){

		return dao().post(generalhealthexamination_);
	}
	public int put(GeneralHealthExamination generalhealthexamination_){

		return dao().put(generalhealthexamination_);
	}
	public List<GeneralHealthExamination> list(){

		return dao().list();
	}
	public List<GeneralHealthExamination> getByAnimal(String animalID){

		return dao().getByAnimal(animalID);
	}	
	
	public GeneralHealthExamination get(String gaheid){

		return dao().get(gaheid);
	}
	public int delete(String gaheid){

		return dao().delete(gaheid);
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
