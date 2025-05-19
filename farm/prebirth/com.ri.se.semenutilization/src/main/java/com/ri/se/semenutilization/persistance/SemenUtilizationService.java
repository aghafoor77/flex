package com.ri.se.semenutilization.persistance;

import java.util.List;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.ri.se.semenutilization.utils.AbstractHealthCheck;

public abstract class SemenUtilizationService extends AbstractHealthCheck {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract SemenUtilizationDAO dao();

	public void create() {
		dao().create();
	}

	public int post(SemenUtilization semenutilization_){

		return dao().post(semenutilization_);
	}
	public int put(SemenUtilization semenutilization_){

		return dao().put(semenutilization_);
	}
	public List<SemenUtilization> list(){

		return dao().list();
	}
	public SemenUtilization get(String suid){

		return dao().get(suid);
	}
	public int delete(String suid){

		return dao().delete(suid);
	}
	public List<SemenUtilization> getByAnimal(String animalID){
		return dao().getByAnimal(animalID);
	}	
	public List<SemenUtilization> getByOrder(String osid){

		return dao().getByOrder(osid);
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
