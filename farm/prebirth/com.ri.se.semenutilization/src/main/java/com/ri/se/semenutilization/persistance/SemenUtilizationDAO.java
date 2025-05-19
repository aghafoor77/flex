package com.ri.se.semenutilization.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SemenUtilizationMapper.class)
public interface SemenUtilizationDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `SemenUtilization` ( `suid` varchar(100) NOT NULL,`insemationDate` timestamp NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`osid` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`suid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into SemenUtilization(suid, insemationDate, notes, employeeID, osid, animalID) values(:suid, :insemationDate, :notes, :employeeID, :osid, :animalID)")
	public int post(@BindBean SemenUtilization semenutilization_);

	@SqlUpdate("UPDATE SemenUtilization SET suid=:suid, insemationDate=:insemationDate, notes=:notes, employeeID=:employeeID, osid=:osid, animalID=:animalID WHERE suid=:suid")
	public int put(@BindBean SemenUtilization semenutilization_);

	@SqlQuery("select * from SemenUtilization;")
	public List<SemenUtilization> list();

	@SqlQuery("select * from SemenUtilization where suid=:suid;")
	public SemenUtilization get(@Bind("suid") String suid);

	@SqlUpdate("Delete from SemenUtilization where suid=:suid;")
	public int delete(@Bind("suid") String suid);
	
	@SqlQuery("select * from SemenUtilization where osid=:osid;")
	public List<SemenUtilization> getByOrder(@Bind("osid") String osid);
	
	@SqlQuery("select * from SemenUtilization where animalID=:animalID order by insemationDate;")
	public List<SemenUtilization> getByAnimal(@Bind("animalID") String animalID);

}
