package analytics.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AnalyticsMapper.class)
public interface AnalyticsDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Analytics` ( `AID` varchar(100) NOT NULL,`ADG` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`AID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Analytics(AID, ADG) values(:AID, :ADG)")
	public int post(@BindBean Analytics analytics_);

	@SqlUpdate("UPDATE Analytics SET AID=:AID, ADG=:ADG WHERE AID=:AID")
	public int put(@BindBean Analytics analytics_);

	@SqlQuery("select * from Analytics;")
	public List<Analytics> list();

	@SqlQuery("select * from Analytics where AID=:AID;")
	public Analytics get(@Bind("AID") String AID);

	@SqlUpdate("Delete from Analytics where AID=:AID;")
	public int delete(@Bind("AID") String AID);

}
