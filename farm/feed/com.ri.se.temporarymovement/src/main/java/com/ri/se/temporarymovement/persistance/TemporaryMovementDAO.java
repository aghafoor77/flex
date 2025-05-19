package com.ri.se.temporarymovement.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TemporaryMovementMapper.class)
public interface TemporaryMovementDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `TemporaryMovement` ( `tmid` varchar(100) NOT NULL,`outDate` timestamp NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`purpose` varchar(100) NULL DEFAULT NULL,`inDate` timestamp NULL DEFAULT NULL,`tmType` varchar(100) NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`tmid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into TemporaryMovement(tmid, outDate, notes, purpose, inDate, tmType, employeeID) values(:tmid, :outDate, :notes, :purpose, :inDate, :tmType, :employeeID)")
	public int post(@BindBean TemporaryMovement temporarymovement_);

	@SqlUpdate("UPDATE TemporaryMovement SET tmid=:tmid, outDate=:outDate, notes=:notes, purpose=:purpose, inDate=:inDate, tmType=:tmType, employeeID=:employeeID WHERE tmid=:tmid")
	public int put(@BindBean TemporaryMovement temporarymovement_);

	@SqlQuery("select * from TemporaryMovement;")
	public List<TemporaryMovement> list();

	@SqlQuery("select * from TemporaryMovement where tmid=:tmid;")
	public TemporaryMovement get(@Bind("tmid") String tmid);

	@SqlUpdate("Delete from TemporaryMovement where tmid=:tmid;")
	public int delete(@Bind("tmid") String tmid);

}
