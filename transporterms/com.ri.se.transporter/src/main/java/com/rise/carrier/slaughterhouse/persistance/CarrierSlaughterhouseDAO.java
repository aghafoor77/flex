package com.rise.carrier.slaughterhouse.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(CarrierSlaughterhouseMapper.class)
public interface CarrierSlaughterhouseDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `CarrierSlaughterhouse` ( `tripNo` varchar(100) NOT NULL,`slaughterhousename` varchar(100) NULL DEFAULT NULL,`currentStatus` varchar(100) NULL DEFAULT NULL,`expectedArrivalDate` bigint(20) NULL DEFAULT NULL,`location` varchar(100) NULL DEFAULT NULL,`startDate` bigint(20) NULL DEFAULT NULL, PRIMARY KEY (`tripNo`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into CarrierSlaughterhouse(tripNo, slaughterhousename, currentStatus, expectedArrivalDate, location, startDate) values(:tripNo, :slaughterhousename, :currentStatus, :expectedArrivalDate, :location, :startDate)")
	public int post(@BindBean CarrierSlaughterhouse carrierslaughterhouse_);

	@SqlUpdate("UPDATE CarrierSlaughterhouse SET tripNo=:tripNo, slaughterhousename=:slaughterhousename, currentStatus=:currentStatus, expectedArrivalDate=:expectedArrivalDate, location=:location, startDate=:startDate WHERE tripNo=:tripNo")
	public int put(@BindBean CarrierSlaughterhouse carrierslaughterhouse_);

	@SqlQuery("select * from CarrierSlaughterhouse;")
	public List<CarrierSlaughterhouse> list();

	@SqlQuery("select * from CarrierSlaughterhouse where tripNo=:tripNo;")
	public CarrierSlaughterhouse get(@Bind("tripNo") String tripNo);

	@SqlUpdate("Delete from CarrierSlaughterhouse where tripNo=:tripNo;")
	public int delete(@Bind("tripNo") String tripNo);

}
