package com.rise.vdr.api.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SCResourceMapper.class)
public interface SCResourceDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `SCResource` ( `did` varchar(100) NOT NULL,`role` varchar(100) NULL DEFAULT NULL,`publickey` varchar(100) NULL DEFAULT NULL,`company` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`did`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into SCResource(did, role, publickey, company) values(:did, :role, :publickey, :company)")
	public int post(@BindBean SCResource scresource_);

	@SqlUpdate("UPDATE SCResource SET did=:did, role=:role, publickey=:publickey, company=:company WHERE did=:did")
	public int put(@BindBean SCResource scresource_);

	@SqlQuery("select * from SCResource;")
	public List<SCResource> list();

	@SqlQuery("select * from SCResource where did=:did;")
	public SCResource get(@Bind("did") String did);

	@SqlUpdate("Delete from SCResource where did=:did;")
	public int delete(@Bind("did") String did);

}
