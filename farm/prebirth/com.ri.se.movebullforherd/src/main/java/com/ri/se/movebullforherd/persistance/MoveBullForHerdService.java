package com.ri.se.movebullforherd.persistance;

import java.util.List;

import javax.ws.rs.PathParam;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.ri.se.movebullforherd.utils.AbstractHealthCheck;

public abstract class MoveBullForHerdService extends AbstractHealthCheck {

	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";

	@CreateSqlObject
	abstract MoveBullForHerdDAO dao();

	public void create() {
		dao().create();
	}

	public int post(MoveBullForHerd movebullforherd_) {

		return dao().post(movebullforherd_);
	}

	public int put(MoveBullForHerd movebullforherd_) {

		return dao().put(movebullforherd_);
	}

	public List<MoveBullForHerd> list() {

		return dao().list();
	}

	public MoveBullForHerd get(String mb4hid) {

		return dao().get(mb4hid);
	}

	public int delete(String mb4hid) {

		return dao().delete(mb4hid);
	}

	public List<MoveBullForHerd> getAnimalMovement(String animalID) {
		String likeStr = "%" + animalID + "%";
		List<MoveBullForHerd> list = dao().getAnimalMovement(animalID, likeStr);
		return list;
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
