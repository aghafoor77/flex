package carrier.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(CarrierMapper.class)
public interface CarrierDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Carrier` ( `CID` varchar(100) NOT NULL,`carrierNumber` varchar(100) NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`species` varchar(100) NULL DEFAULT NULL,`transportType` varchar(100) NULL DEFAULT NULL,`TID` varchar(100) NULL DEFAULT NULL,`longDistance` tinyint(1) NULL DEFAULT NULL, PRIMARY KEY (`CID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Carrier(CID, carrierNumber, notes, species, transportType, TID, longDistance) values(:CID, :carrierNumber, :notes, :species, :transportType, :TID, :longDistance)")
	public int post(@BindBean Carrier carrier_);

	@SqlUpdate("UPDATE Carrier SET CID=:CID, carrierNumber=:carrierNumber, notes=:notes, species=:species, transportType=:transportType, TID=:TID, longDistance=:longDistance WHERE CID=:CID")
	public int put(@BindBean Carrier carrier_);

	@SqlQuery("select * from Carrier;")
	public List<Carrier> list();

	@SqlQuery("select * from Carrier where CID=:CID;")
	public Carrier get(@Bind("CID") String CID);

	@SqlUpdate("Delete from Carrier where CID=:CID;")
	public int delete(@Bind("CID") String CID);

}
