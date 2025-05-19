package com.ri.se.temporarymovementgroup.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TemporaryMovementGroupMapper.class)
public interface TemporaryMovementGroupDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `TemporaryMovementGroup` ( `tmgid` varchar(100) NOT NULL,`outDate` timestamp NULL DEFAULT NULL,`tmid` varchar(100) NULL DEFAULT NULL,`inDate` timestamp NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`tmgid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into TemporaryMovementGroup(tmgid, outDate, tmid, inDate, employeeID, animalID) values(:tmgid, :outDate, :tmid, :inDate, :employeeID, :animalID)")
	public int post(@BindBean TemporaryMovementGroup temporarymovementgroup_);

	@SqlUpdate("UPDATE TemporaryMovementGroup SET tmgid=:tmgid, outDate=:outDate, tmid=:tmid, inDate=:inDate, employeeID=:employeeID, animalID=:animalID WHERE tmgid=:tmgid")
	public int put(@BindBean TemporaryMovementGroup temporarymovementgroup_);

	@SqlQuery("select * from TemporaryMovementGroup;")
	public List<TemporaryMovementGroup> list();

	@SqlQuery("select * from TemporaryMovementGroup where tmgid=:tmgid;")
	public TemporaryMovementGroup get(@Bind("tmgid") String tmgid);
	
	@SqlQuery("select * from TemporaryMovementGroup where tmid=:tmid;")
	public List<TemporaryMovementGroup> getAnimals(@Bind("tmid") String tmid);
	
	@SqlQuery("select * from TemporaryMovementGroup where animalID=:animalID;")
	public List<TemporaryMovementGroup> getByAnimalID(@Bind("animalID") String animalID);
	
	@SqlQuery("select * from TemporaryMovementGroup where tmid=:tmid AND inDate is NULL;")
	public List<TemporaryMovementGroup> getAnimalsStillInField(@Bind("tmid") String tmid);
	
	
	@SqlUpdate("Delete from TemporaryMovementGroup where tmgid=:tmgid;")
	public int delete(@Bind("tmgid") String tmgid);

}
