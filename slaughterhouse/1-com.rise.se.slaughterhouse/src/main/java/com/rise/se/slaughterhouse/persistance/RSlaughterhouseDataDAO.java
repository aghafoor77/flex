package com.rise.se.slaughterhouse.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(RSlaughterhouseDataMapper.class)
public interface RSlaughterhouseDataDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `RSlaughterhouseData` ( `tripNo` varchar(100) NOT NULL,`slaughterhousename` varchar(100) NULL DEFAULT NULL,`currentStatus` varchar(100) NULL DEFAULT NULL,`expectedArrivalDate` bigint(20) NULL DEFAULT NULL,`location` varchar(100) NULL DEFAULT NULL,`startDate` bigint(20) NULL DEFAULT NULL, PRIMARY KEY (`tripNo`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into RSlaughterhouseData(tripNo, slaughterhousename, currentStatus, expectedArrivalDate, location, startDate) values(:tripNo, :slaughterhousename, :currentStatus, :expectedArrivalDate, :location, :startDate)")
	public int post(@BindBean RSlaughterhouseData rslaughterhousedata_);

	@SqlUpdate("UPDATE RSlaughterhouseData SET tripNo=:tripNo, slaughterhousename=:slaughterhousename, currentStatus=:currentStatus, expectedArrivalDate=:expectedArrivalDate, location=:location, startDate=:startDate WHERE tripNo=:tripNo")
	public int put(@BindBean RSlaughterhouseData rslaughterhousedata_);

	@SqlQuery("select * from RSlaughterhouseData;")
	public List<RSlaughterhouseData> list();

	@SqlQuery("select * from RSlaughterhouseData where tripNo=:tripNo;")
	public RSlaughterhouseData get(@Bind("tripNo") String tripNo);

	@SqlUpdate("Delete from RSlaughterhouseData where tripNo=:tripNo;")
	public int delete(@Bind("tripNo") String tripNo);

}
