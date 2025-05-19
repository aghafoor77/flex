package centralhostregistration.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(CentralHostRegistrationMapper.class)
public interface CentralHostRegistrationDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `CentralHostRegistration` ( `name` varchar(100) NOT NULL,`address` varchar(100) NULL DEFAULT NULL,`port` bigint(10) NULL DEFAULT NULL,`healthCheck` varchar(100) NULL DEFAULT NULL,`registrationDate` timestamp NULL DEFAULT NULL,`status` varchar(100) NULL DEFAULT NULL, `hb` bigint(10) NULL DEFAULT NULL,`type` varchar(50) NULL DEFAULT NULL , PRIMARY KEY (`name`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into CentralHostRegistration(name, address, port, healthCheck, registrationDate, status, hb,type) values(:name, :address, :port, :healthCheck, :registrationDate, :status, :hb, :type)")
	public int post(@BindBean CentralHostRegistration centralhostregistration_);

	@SqlUpdate("UPDATE CentralHostRegistration SET name=:name, address=:address, port=:port, healthCheck=:healthCheck, registrationDate=:registrationDate, status=:status, hb=:hb, type=:type WHERE name=:name")
	public int put(@BindBean CentralHostRegistration centralhostregistration_);

	@SqlUpdate("UPDATE CentralHostRegistration SET hb=:hb WHERE name=:name")
	public int putHb(@Bind("name") String name,@Bind("hb")  int hb);

	
	@SqlQuery("select * from CentralHostRegistration;")
	public List<CentralHostRegistration> list();

	
	@SqlQuery("select * from CentralHostRegistration where name=:name;")
	public CentralHostRegistration get(@Bind("name") String name);

	@SqlQuery("select * from CentralHostRegistration where type=:type;")
	public List<CentralHostRegistration> getByType(@Bind("type") String type);

	
	@SqlUpdate("Delete from CentralHostRegistration where name=:name;")
	public int delete(@Bind("name") String name);
	
	@SqlUpdate("Delete from CentralHostRegistration;")
	public int deleteAll();

}
