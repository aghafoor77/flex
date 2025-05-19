package com.ri.se.ordersemen.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class OrderSemenMapper implements ResultSetMapper<OrderSemen> {

	private static final String  osid = "osid";
	private static final String  notes = "notes";
	private static final String  contact = "contact";
	private static final String  orderedTo = "orderedTo";
	private static final String  employeeID = "employeeID";
	private static final String  emailto = "emailto";
	private static final String  farmID = "farmID";
	private static final String  orderDate = "orderDate";
	private static final String  breed = "breed";
	private static final String  status = "status";

	public OrderSemen map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new OrderSemen(resultSet.getString(osid), resultSet.getString(notes), resultSet.getString(contact), resultSet.getString(orderedTo), resultSet.getString(employeeID), resultSet.getString(emailto), resultSet.getString(farmID), resultSet.getTimestamp(orderDate), resultSet.getString(breed), resultSet.getString(status));

	}
}
