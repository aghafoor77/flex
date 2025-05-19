package com.ri.se.temporarymovementgroup.persistance;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.PathParam;

import java.util.Date;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

public abstract class TemporaryMovementGroupService {


	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	
	@CreateSqlObject
	abstract TemporaryMovementGroupDAO dao();

	public void create() {
		dao().create();
	}

	public int post(TemporaryMovementGroup temporarymovementgroup_){

		return dao().post(temporarymovementgroup_);
	}
	public int put(TemporaryMovementGroup temporarymovementgroup_){

		return dao().put(temporarymovementgroup_);
	}
	public List<TemporaryMovementGroup> list(){

		return dao().list();
	}
	
	public List<TemporaryMovementGroup> getByAnimalID(String animalID) {

		return dao().getByAnimalID(animalID);
	}	
	
	public int manageUpdate(List<String> list, String tmid, String employeeID){
		
		
		List<TemporaryMovementGroup> stored = getAnimals(tmid);
		for(TemporaryMovementGroup temporaryMovementGroup: stored ) {
			if(!list.contains(temporaryMovementGroup.getAnimalID())){
				temporaryMovementGroup.setInDate(new Date());
				temporaryMovementGroup.setEmployeeID(employeeID);
				put(temporaryMovementGroup);
				list.remove(temporaryMovementGroup.getAnimalID());
			} else if(list.contains(temporaryMovementGroup.getAnimalID())){
				list.remove(temporaryMovementGroup.getAnimalID());
			} 
		}
		
		for(String newEntry : list) {
			TemporaryMovementGroup temporaryMovementGroup = new TemporaryMovementGroup();
			temporaryMovementGroup.setAnimalID(newEntry);
			temporaryMovementGroup.setEmployeeID(employeeID);
			temporaryMovementGroup.setOutDate(new Date());
			temporaryMovementGroup.setInDate(null);
			temporaryMovementGroup.setTmid(tmid);
			temporaryMovementGroup.setTmgid("TMG-" + System.currentTimeMillis());
			post(temporaryMovementGroup);
		}
		return 1;
	}
	
	public List<TemporaryMovementGroup> getAnimals(String tmid){
		return dao().getAnimals(tmid);
	}
	
	public List<TemporaryMovementGroup> getAnimalsStillInField(String tmid){
		return dao().getAnimalsStillInField(tmid);
	}
	
	public TemporaryMovementGroup get(String tmgid){

		return dao().get(tmgid);
	}
	public int delete(String tmgid){

		return dao().delete(tmgid);
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
	
	private void whatToDo(List<TemporaryMovementGroup> stored, List<String> received) {
		
	}
}
