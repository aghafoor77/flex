package com.ri.se.feedtype.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(FeedTypeMapper.class)
public interface FeedTypeDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `FeedType` ( `feedName` varchar(100) NOT NULL,`creationDate` timestamp NULL DEFAULT NULL, PRIMARY KEY (`feedName`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into FeedType(feedName, creationDate) values(:feedName, :creationDate)")
	public int post(@BindBean FeedType feedtype_);

	@SqlUpdate("UPDATE FeedType SET feedName=:feedName, creationDate=:creationDate WHERE feedName=:feedName")
	public int put(@BindBean FeedType feedtype_);

	@SqlQuery("select * from FeedType;")
	public List<FeedType> list();

	@SqlQuery("select * from FeedType where feedName=:feedName;")
	public FeedType get(@Bind("feedName") String feedName);

	@SqlUpdate("Delete from FeedType where feedName=:feedName;")
	public int delete(@Bind("feedName") String feedName);

}
