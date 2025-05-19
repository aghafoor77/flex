package com.ri.se.responseordersemen.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ResponseOrderSemenMapper.class)
public interface ResponseOrderSemenDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `ResponseOrderSemen` ( `osid` varchar(100) NOT NULL,`notes` varchar(100) NULL DEFAULT NULL,`resDate` timestamp NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`repliedBy` varchar(100) NULL DEFAULT NULL,`billingURL` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`osid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into ResponseOrderSemen(osid, notes, resDate, employeeID, repliedBy, billingURL) values(:osid, :notes, :resDate, :employeeID, :repliedBy, :billingURL)")
	public int post(@BindBean ResponseOrderSemen responseordersemen_);

	@SqlUpdate("UPDATE ResponseOrderSemen SET osid=:osid, notes=:notes, resDate=:resDate, employeeID=:employeeID, repliedBy=:repliedBy, billingURL=:billingURL WHERE osid=:osid")
	public int put(@BindBean ResponseOrderSemen responseordersemen_);

	@SqlQuery("select * from ResponseOrderSemen;")
	public List<ResponseOrderSemen> list();

	@SqlQuery("select * from ResponseOrderSemen where osid=:osid;")
	public ResponseOrderSemen get(@Bind("osid") String osid);

	@SqlUpdate("Delete from ResponseOrderSemen where osid=:osid;")
	public int delete(@Bind("osid") String osid);

}
