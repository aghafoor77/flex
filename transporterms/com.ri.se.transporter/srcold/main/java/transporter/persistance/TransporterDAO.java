package transporter.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TransporterMapper.class)
public interface TransporterDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Transporter` ( `TID` varchar(100) NOT NULL,`notes` varchar(100) NULL DEFAULT NULL,`animalPeryear` varchar(100) NULL DEFAULT NULL,`ownerId` varchar(100) NULL DEFAULT NULL,`isOrganization` tinyint(1) NULL DEFAULT NULL, PRIMARY KEY (`TID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Transporter(TID, notes, animalPeryear, ownerId, isOrganization) values(:TID, :notes, :animalPeryear, :ownerId, :isOrganization)")
	public int post(@BindBean Transporter transporter_);

	@SqlUpdate("UPDATE Transporter SET TID=:TID, notes=:notes, animalPeryear=:animalPeryear, ownerId=:ownerId, isOrganization=:isOrganization WHERE TID=:TID")
	public int put(@BindBean Transporter transporter_);

	@SqlQuery("select * from Transporter;")
	public List<Transporter> list();

	@SqlQuery("select * from Transporter where TID=:TID;")
	public Transporter get(@Bind("TID") String TID);

	@SqlUpdate("Delete from Transporter where TID=:TID;")
	public int delete(@Bind("TID") String TID);

}
