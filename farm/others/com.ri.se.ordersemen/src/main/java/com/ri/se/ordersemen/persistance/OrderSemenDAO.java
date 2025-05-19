package com.ri.se.ordersemen.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(OrderSemenMapper.class)
public interface OrderSemenDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `OrderSemen` ( `osid` varchar(100) NOT NULL,`notes` varchar(100) NULL DEFAULT NULL,`contact` varchar(100) NULL DEFAULT NULL,`orderedTo` varchar(100) NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`emailto` varchar(100) NULL DEFAULT NULL,`farmID` varchar(100) NULL DEFAULT NULL,`orderDate` timestamp NULL DEFAULT NULL,`breed` varchar(100) NULL DEFAULT NULL,`status` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`osid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into OrderSemen(osid, notes, contact, orderedTo, employeeID, emailto, farmID, orderDate, breed, status) values(:osid, :notes, :contact, :orderedTo, :employeeID, :emailto, :farmID, :orderDate, :breed, :status)")
	public int post(@BindBean OrderSemen ordersemen_);

	@SqlUpdate("UPDATE OrderSemen SET osid=:osid, notes=:notes, contact=:contact, orderedTo=:orderedTo, employeeID=:employeeID, emailto=:emailto, farmID=:farmID, orderDate=:orderDate, breed=:breed, status=:status WHERE osid=:osid")
	public int put(@BindBean OrderSemen ordersemen_);

	@SqlQuery("select * from OrderSemen;")
	public List<OrderSemen> list();

	@SqlQuery("select * from OrderSemen where osid=:osid;")
	public OrderSemen get(@Bind("osid") String osid);

	@SqlQuery("select * from OrderSemen where status=:status;")
	public List<OrderSemen> getByStatus(@Bind("status") String status);
	
	@SqlUpdate("Delete from OrderSemen where osid=:osid;")
	public int delete(@Bind("osid") String osid);

}
