package com.ri.se.assignfeed.persistance;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.PathParam;

import java.util.Date;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

public abstract class AssignFeedService {

	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";

	@CreateSqlObject
	abstract AssignFeedDAO dao();

	public void create() {
		dao().create();
	}

	public int post(AssignFeed assignfeed_) {

		return dao().post(assignfeed_);
	}

	public int put(AssignFeed assignfeed_) {

		return dao().put(assignfeed_);
	}

	public List<AssignFeed> list() {

		return dao().list();
	}

	public List<AssignFeed> getFeedByAnimal(String animalID){
		return dao().getFeedByAnimal(animalID);
	}
	public AssignFeed get(String afid) {

		return dao().get(afid);
	}

	public int delete(String afid) {

		return dao().delete(afid);
	}

	public AssignFeed getByAnimalIdAndFPid(String animalID, String fpid) {
		return dao().getByAnimalIdAndFPid(animalID, fpid);
	}

	public void deleteByFP(String fpid) {
		System.out.println(fpid);
		System.out.println(dao().deleteByFP(fpid));
	}

	public List<AssignFeed> getAnimalsByFP(String fpid) {
		return dao().getAnimalsByFP(fpid);

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
