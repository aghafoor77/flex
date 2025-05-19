package ri.se.farm.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(FarmMapper.class)
public interface FarmDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Farm` ( `farmId` varchar(100) NOT NULL,`farmName` varchar(100) NULL DEFAULT NULL,`owner` varchar(100) NULL DEFAULT NULL,`dateTime` timestamp NULL DEFAULT NULL,`country` varchar(100) NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`city` varchar(100) NULL DEFAULT NULL,`street` varchar(100) NULL DEFAULT NULL,`postalcode` varchar(100) NULL DEFAULT NULL,`farmContactNo` varchar(100) NULL DEFAULT NULL,`farmCompany` varchar(100) NULL DEFAULT NULL,`email` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`farmId`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Farm(farmId, farmName, owner, dateTime, country, notes, city, street, postalcode, farmContactNo, farmCompany, email) values(:farmId, :farmName, :owner, :dateTime, :country, :notes, :city, :street, :postalcode, :farmContactNo, :farmCompany, :email)")
	public int post(@BindBean Farm farm_);

	@SqlUpdate("UPDATE Farm SET farmId=:farmId, farmName=:farmName, owner=:owner, dateTime=:dateTime, country=:country, notes=:notes, city=:city, street=:street, postalcode=:postalcode, farmContactNo=:farmContactNo, farmCompany=:farmCompany, email=:email WHERE farmId=:farmId")
	public int put(@BindBean Farm farm_);

	@SqlQuery("select * from Farm;")
	public List<Farm> list();

	@SqlQuery("select * from Farm where farmId=:farmId;")
	public Farm get(@Bind("farmId") String farmId);

	@SqlQuery("select * from Farm where owner=:owner;")
	public List<Farm> getByOwner(@Bind("owner") String owner);
	
	
	@SqlUpdate("Delete from Farm where farmId=:farmId;")
	public int delete(@Bind("farmId") String farmId);

}
