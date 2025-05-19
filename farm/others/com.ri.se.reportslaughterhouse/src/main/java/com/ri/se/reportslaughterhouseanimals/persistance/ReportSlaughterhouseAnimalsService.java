package com.ri.se.reportslaughterhouseanimals.persistance;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.PathParam;

import java.util.Date;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

public abstract class ReportSlaughterhouseAnimalsService {

	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";

	@CreateSqlObject
	abstract ReportSlaughterhouseAnimalsDAO dao();

	public void create() {
		dao().create();
	}

	public int post(ReportSlaughterhouseAnimals reportslaughterhouseanimals_) {

		return dao().post(reportslaughterhouseanimals_);
	}

	public int put(ReportSlaughterhouseAnimals reportslaughterhouseanimals_) {

		return dao().put(reportslaughterhouseanimals_);
	}

	public List<ReportSlaughterhouseAnimals> list() {

		return dao().list();
	}

	public List<ReportSlaughterhouseAnimals> getByAnimalID(String animalID) {
		return dao().getByAnimalID(animalID);
	}

	public List<ReportSlaughterhouseAnimals> getAnimalsByRID(String rid) {

		return dao().getAnimalsByRID(rid);
	}

	public ReportSlaughterhouseAnimals get(String rsaid) {

		return dao().get(rsaid);
	}

	public int delete(String rsaid) {

		return dao().delete(rsaid);
	}

	public int deleteByRID(String rid) {
		return dao().deleteByRID(rid);
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
