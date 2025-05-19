package com.ri.se.generalhealthobservation.persistance;

import java.util.List;
import java.util.Objects;
import java.util.Date;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.ri.se.generalhealthobservation.utils.AbstractHealthCheck;

public abstract class GeneralHealthObservationService extends AbstractHealthCheck {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract GeneralHealthObservationDAO dao();

	public void create() {
		dao().create();
	}

	public int post(GeneralHealthObservation generalhealthobservation_){

		return dao().post(generalhealthobservation_);
	}
	public int put(GeneralHealthObservation generalhealthobservation_){

		return dao().put(generalhealthobservation_);
	}
	public List<GeneralHealthObservation> list(){

		return dao().list();
	}
	public List<GeneralHealthObservation> getByAnimalID(String animalID){
		return dao().getByAnimalID(animalID);
	}
		
	public GeneralHealthObservation get(String ghoid){

		return dao().get(ghoid);
	}
	public int delete(String ghoid){

		return dao().delete(ghoid);
	}
	public List<GeneralHealthObservation> getByAnimal(String animalID){
		return dao().getByAnimal(animalID);
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
