package com.ri.se.resources.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class FacilityMapper implements ResultSetMapper<Facility> {

	private static final String  facilityNr = "facilityNr";
	private static final String  areasize = "areasize";
	private static final String  fromDate = "fromDate";
	private static final String  movementacrossEU = "movementacrossEU";
	private static final String  address = "address";
	private static final String  maxcapacity = "maxcapacity";
	private static final String  temporaryactivity = "temporaryactivity";
	private static final String  anlaggningsnumber = "anlaggningsnumber";
	private static final String  specieskept = "specieskept";
	private static final String  farmId = "farmId";
	private static final String  type = "type";
	private static final String  breedingmaterials = "breedingmaterials";

	public Facility map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Facility(resultSet.getString(facilityNr), resultSet.getString(areasize), resultSet.getString(fromDate), resultSet.getString(movementacrossEU), resultSet.getString(address), resultSet.getString(maxcapacity), resultSet.getString(temporaryactivity), resultSet.getString(anlaggningsnumber), resultSet.getString(specieskept), resultSet.getString(farmId), resultSet.getString(type), resultSet.getString(breedingmaterials));

	}
}
