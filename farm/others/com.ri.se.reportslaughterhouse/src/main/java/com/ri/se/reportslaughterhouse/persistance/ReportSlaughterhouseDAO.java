package com.ri.se.reportslaughterhouse.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(ReportSlaughterhouseMapper.class)
public interface ReportSlaughterhouseDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `ReportSlaughterhouse` ( `rid` varchar(100) NOT NULL,`notes` varchar(100) NULL DEFAULT NULL,`slaughterhousename` varchar(100) NULL DEFAULT NULL,`response` varchar(100) NULL DEFAULT NULL,`sex` varchar(100) NULL DEFAULT NULL,`numberofanimals` bigint(10) NULL DEFAULT NULL,`reportingDate` timestamp NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`slaughterhousecontact` varchar(100) NULL DEFAULT NULL,`age` varchar(100) NULL DEFAULT NULL,`breed` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`rid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into ReportSlaughterhouse(rid, notes, slaughterhousename, response, sex, numberofanimals, reportingDate, employeeID, slaughterhousecontact, age, breed) values(:rid, :notes, :slaughterhousename, :response, :sex, :numberofanimals, :reportingDate, :employeeID, :slaughterhousecontact, :age, :breed)")
	public int post(@BindBean ReportSlaughterhouse reportslaughterhouse_);

	@SqlUpdate("UPDATE ReportSlaughterhouse SET rid=:rid, notes=:notes, slaughterhousename=:slaughterhousename, response=:response, sex=:sex, numberofanimals=:numberofanimals, reportingDate=:reportingDate, employeeID=:employeeID, slaughterhousecontact=:slaughterhousecontact, age=:age, breed=:breed WHERE rid=:rid")
	public int put(@BindBean ReportSlaughterhouse reportslaughterhouse_);

	@SqlQuery("select * from ReportSlaughterhouse;")
	public List<ReportSlaughterhouse> list();

	@SqlQuery("select * from ReportSlaughterhouse where rid=:rid;")
	public ReportSlaughterhouse get(@Bind("rid") String rid);

	@SqlUpdate("Delete from ReportSlaughterhouse where rid=:rid;")
	public int delete(@Bind("rid") String rid);

}
