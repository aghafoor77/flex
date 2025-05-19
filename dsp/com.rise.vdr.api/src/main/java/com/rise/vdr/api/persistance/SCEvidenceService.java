package com.rise.vdr.api.persistance;

import java.util.List;
import java.util.Objects;
import java.util.Date;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

public abstract class SCEvidenceService {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract SCEvidenceDAO dao();

	public void create() {
		dao().create();
	}

	public int post(SCEvidence scevidence_){

		return dao().post(scevidence_);
	}
	public int put(SCEvidence scevidence_){

		return dao().put(scevidence_);
	}
	public List<SCEvidence> list(){

		return dao().list();
	}
	public SCEvidence get(String eid){

		return dao().get(eid);
	}

	public List<SCEvidence> getByTransactionID(String transactionID){
		return dao().getByTransactionID(transactionID);
	}
	
	public List<SCEvidence> getByTransactionIDData(String transactionID){
		return dao().getByTransactionIDData(transactionID);
	}
	
	
	public int delete(String eid){

		return dao().delete(eid);
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
