package transportedcattle.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TransportedCattleMapper.class)
public interface TransportedCattleDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `TransportedCattle` ( `TCID` varchar(100) NOT NULL,`notes` varchar(100) NULL DEFAULT NULL,`GHEID` varchar(100) NULL DEFAULT NULL,`destination` varchar(100) NULL DEFAULT NULL,`departDate` timestamp NULL DEFAULT NULL,`transportType` varchar(100) NULL DEFAULT NULL,`source` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL,`carriernumber` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`TCID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into TransportedCattle(TCID, notes, GHEID, destination, departDate, transportType, source, animalID, carriernumber) values(:TCID, :notes, :GHEID, :destination, :departDate, :transportType, :source, :animalID, :carriernumber)")
	public int post(@BindBean TransportedCattle transportedcattle_);

	@SqlUpdate("UPDATE TransportedCattle SET TCID=:TCID, notes=:notes, GHEID=:GHEID, destination=:destination, departDate=:departDate, transportType=:transportType, source=:source, animalID=:animalID, carriernumber=:carriernumber WHERE TCID=:TCID")
	public int put(@BindBean TransportedCattle transportedcattle_);

	@SqlQuery("select * from TransportedCattle;")
	public List<TransportedCattle> list();

	@SqlQuery("select * from TransportedCattle where TCID=:TCID;")
	public TransportedCattle get(@Bind("TCID") String TCID);

	@SqlUpdate("Delete from TransportedCattle where TCID=:TCID;")
	public int delete(@Bind("TCID") String TCID);

}
