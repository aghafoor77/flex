package com.ri.se.reportslaughterhouseanimals.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ReportSlaughterhouseAnimalsMapper.class)
public interface ReportSlaughterhouseAnimalsDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `ReportSlaughterhouseAnimals` ( `rsaid` varchar(100) NOT NULL,`selectionDate` timestamp NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`rid` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`rsaid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into ReportSlaughterhouseAnimals(rsaid, selectionDate, employeeID, rid, animalID) values(:rsaid, :selectionDate, :employeeID, :rid, :animalID)")
	public int post(@BindBean ReportSlaughterhouseAnimals reportslaughterhouseanimals_);

	@SqlUpdate("UPDATE ReportSlaughterhouseAnimals SET rsaid=:rsaid, selectionDate=:selectionDate, employeeID=:employeeID, rid=:rid, animalID=:animalID WHERE rsaid=:rsaid")
	public int put(@BindBean ReportSlaughterhouseAnimals reportslaughterhouseanimals_);

	@SqlQuery("select * from ReportSlaughterhouseAnimals;")
	public List<ReportSlaughterhouseAnimals> list();

	@SqlQuery("select * from ReportSlaughterhouseAnimals where rid=:rid;")
	public List<ReportSlaughterhouseAnimals> getAnimalsByRID(@Bind("rid") String rid);
	
	@SqlQuery("select * from ReportSlaughterhouseAnimals where animalID=:animalID;")
	public List<ReportSlaughterhouseAnimals> getByAnimalID(@Bind("animalID") String animalID);
	
	@SqlQuery("select * from ReportSlaughterhouseAnimals where rsaid=:rsaid;")
	public ReportSlaughterhouseAnimals get(@Bind("rsaid") String rsaid);

	@SqlUpdate("Delete from ReportSlaughterhouseAnimals where rsaid=:rsaid;")
	public int delete(@Bind("rsaid") String rsaid);
	
	
	@SqlUpdate("Delete from ReportSlaughterhouseAnimals where rid=:rid;")
	public int deleteByRID(@Bind("rid") String rid);

}
