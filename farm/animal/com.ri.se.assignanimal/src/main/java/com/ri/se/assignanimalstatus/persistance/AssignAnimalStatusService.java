package com.ri.se.assignanimalstatus.persistance;

import java.util.List;
import java.util.Objects;
import java.util.Date;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

public abstract class AssignAnimalStatusService {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract AssignAnimalStatusDAO dao();

	public void create() {
		dao().create();
	}

	public int post(AssignAnimalStatus assignanimalstatus_){

		return dao().post(assignanimalstatus_);
	}
	public int put(AssignAnimalStatus assignanimalstatus_){

		return dao().put(assignanimalstatus_);
	}
	public List<AssignAnimalStatus> list(){

		return dao().list();
	}
	public AssignAnimalStatus get(String aasid){

		return dao().get(aasid);
	}
	public int delete(String aasid){

		return dao().delete(aasid);
	}
	
	public List<AssignAnimalStatus> getByAaid(String aaid){
		return dao().getByAaid(aaid);
	}
	public List<AssignAnimalStatus> getByExaminer( String examiner){
		return dao().getByExaminer(examiner);
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
