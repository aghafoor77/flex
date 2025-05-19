package com.rise.se.slaughterhouse.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SlaughterhouseMapper.class)
public interface SlaughterhouseDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Slaughterhouse` ( `sid` varchar(100) NOT NULL, PRIMARY KEY (`sid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Slaughterhouse(sid) values(:sid)")
	public int post(@BindBean Slaughterhouse slaughterhouse_);

	@SqlUpdate("UPDATE Slaughterhouse SET sid=:sid WHERE sid=:sid")
	public int put(@BindBean Slaughterhouse slaughterhouse_);

	@SqlQuery("select * from Slaughterhouse;")
	public List<Slaughterhouse> list();

	@SqlQuery("select * from Slaughterhouse where sid=:sid;")
	public Slaughterhouse get(@Bind("sid") String sid);

	@SqlUpdate("Delete from Slaughterhouse where sid=:sid;")
	public int delete(@Bind("sid") String sid);

}
