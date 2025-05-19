package com.rise.vdr.api.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SCSecretKeyMapper.class)
public interface SCSecretKeyDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `SCSecretKey` ( `ssid` varchar(100) NOT NULL,`eid` varchar(100) NULL DEFAULT NULL,`did` varchar(100) NULL DEFAULT NULL,`key` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`ssid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into SCSecretKey(ssid, eid, did, key) values(:ssid, :eid, :did, :key)")
	public int post(@BindBean SCSecretKey scsecretkey_);

	@SqlUpdate("UPDATE SCSecretKey SET ssid=:ssid, eid=:eid, did=:did, key=:key WHERE ssid=:ssid")
	public int put(@BindBean SCSecretKey scsecretkey_);

	@SqlQuery("select * from SCSecretKey;")
	public List<SCSecretKey> list();

	@SqlQuery("select * from SCSecretKey where ssid=:ssid;")
	public SCSecretKey get(@Bind("ssid") String ssid);

	@SqlUpdate("Delete from SCSecretKey where ssid=:ssid;")
	public int delete(@Bind("ssid") String ssid);

}
