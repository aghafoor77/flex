package com.rise.se.slaughterhouse.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(RCarrierSlaughterhouseDataMapper.class)
public interface RCarrierSlaughterhouseDataDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `RCarrierSlaughterhouseData` ( `carrierId` varchar(100) NOT NULL,`carrierNumber` varchar(100) NULL DEFAULT NULL,`tripNo` varchar(100) NULL DEFAULT NULL,`CID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`carrierId`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into RCarrierSlaughterhouseData(carrierId, carrierNumber, tripNo, CID) values(:carrierId, :carrierNumber, :tripNo, :CID)")
	public int post(@BindBean RCarrierSlaughterhouseData rcarrierslaughterhousedata_);

	@SqlUpdate("UPDATE RCarrierSlaughterhouseData SET carrierId=:carrierId, carrierNumber=:carrierNumber, tripNo=:tripNo, CID=:CID WHERE carrierId=:carrierId")
	public int put(@BindBean RCarrierSlaughterhouseData rcarrierslaughterhousedata_);

	@SqlQuery("select * from RCarrierSlaughterhouseData;")
	public List<RCarrierSlaughterhouseData> list();

	@SqlQuery("select * from RCarrierSlaughterhouseData where carrierId=:carrierId;")
	public RCarrierSlaughterhouseData get(@Bind("carrierId") String carrierId);

	@SqlUpdate("Delete from RCarrierSlaughterhouseData where carrierId=:carrierId;")
	public int delete(@Bind("carrierId") String carrierId);
	
	@SqlQuery("select * from RCarrierSlaughterhouseData where tripNo=:tripNo;")
	public List<RCarrierSlaughterhouseData> getByTripNo(@Bind("tripNo") String tripNo);

}
