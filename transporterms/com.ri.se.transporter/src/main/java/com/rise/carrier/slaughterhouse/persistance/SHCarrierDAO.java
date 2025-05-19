package com.rise.carrier.slaughterhouse.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SHCarrierMapper.class)
public interface SHCarrierDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `SHCarrier` ( `tripNo` varchar(100) NOT NULL,`carrierNumber` varchar(100) NULL DEFAULT NULL,`carrierId` varchar(100) NULL DEFAULT NULL,`CID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`tripNo`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into SHCarrier(tripNo, carrierNumber, carrierId, CID) values(:tripNo, :carrierNumber, :carrierId, :CID)")
	public int post(@BindBean SHCarrier shcarrier_);

	@SqlUpdate("UPDATE SHCarrier SET tripNo=:tripNo, carrierNumber=:carrierNumber, carrierId=:carrierId, CID=:CID WHERE tripNo=:tripNo")
	public int put(@BindBean SHCarrier shcarrier_);

	@SqlQuery("select * from SHCarrier;")
	public List<SHCarrier> list();

	@SqlQuery("select * from SHCarrier where tripNo=:tripNo;")
	public SHCarrier get(@Bind("tripNo") String tripNo);

	@SqlUpdate("Delete from SHCarrier where tripNo=:tripNo;")
	public int delete(@Bind("tripNo") String tripNo);

}
