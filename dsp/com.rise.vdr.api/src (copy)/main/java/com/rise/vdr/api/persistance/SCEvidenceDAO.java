package com.rise.vdr.api.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SCEvidenceMapper.class)
public interface SCEvidenceDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `SCEvidence` ( `eid` varchar(100) NOT NULL,`accessLevel` varchar(100) NULL DEFAULT NULL,`resType` varchar(100) NULL DEFAULT NULL,`link` varchar(100) NULL DEFAULT NULL,`transactionID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`eid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into SCEvidence(eid, accessLevel, resType, link, transactionID) values(:eid, :accessLevel, :resType, :link, :transactionID)")
	public int post(@BindBean SCEvidence scevidence_);

	@SqlUpdate("UPDATE SCEvidence SET eid=:eid, accessLevel=:accessLevel, resType=:resType, link=:link, transactionID=:transactionID WHERE eid=:eid")
	public int put(@BindBean SCEvidence scevidence_);

	@SqlQuery("select * from SCEvidence;")
	public List<SCEvidence> list();

	@SqlQuery("select * from SCEvidence where eid=:eid;")
	public SCEvidence get(@Bind("eid") String eid);

	@SqlUpdate("Delete from SCEvidence where eid=:eid;")
	public int delete(@Bind("eid") String eid);

}
