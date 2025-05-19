package com.ri.se.drugstreatments.persistance;

import java.util.List;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.ri.se.drugstreatments.utils.AbstractHealthCheck;

public abstract class DrugsTreatmentsService  extends AbstractHealthCheck {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract DrugsTreatmentsDAO dao();

	public void create() {
		dao().create();
	}

	public int post(DrugsTreatments drugstreatments_){

		return dao().post(drugstreatments_);
	}
	public int put(DrugsTreatments drugstreatments_){

		return dao().put(drugstreatments_);
	}
	public List<DrugsTreatments> list(){

		return dao().list();
	}

	public List<DrugsTreatments> listByAnimal(String animalID){

		return dao().listByAnimal(animalID);
	}
	public DrugsTreatments get(String dtid){

		return dao().get(dtid);
	}
	public int delete(String dtid){

		return dao().delete(dtid);
	}
	public List<DrugsTreatments> chkquarantinePeriod(String animalID){
		return dao().chkquarantinePeriod(animalID);
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
