package com.ri.se.drugstreatments.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class DrugsTreatmentsMapper implements ResultSetMapper<DrugsTreatments> {

	private static final String  dtid = "dtid";
	private static final String  assignedDate = "assignedDate";
	private static final String  reason = "reason";
	private static final String  drungs = "drungs";
	private static final String  refnumber = "refnumber";
	private static final String  examinedBy = "examinedBy";
	private static final String  animalID = "animalID";
	private static final String  reftype = "reftype";

	public DrugsTreatments map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new DrugsTreatments(resultSet.getString(dtid), resultSet.getTimestamp(assignedDate), resultSet.getString(reason), resultSet.getString(drungs), resultSet.getString(refnumber), resultSet.getString(examinedBy), resultSet.getString(animalID), resultSet.getString(reftype));

	}
}
