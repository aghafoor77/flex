package com.ri.se.assignanimalstatus.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AssignAnimalStatusMapper.class)
public interface AssignAnimalStatusDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `AssignAnimalStatus` ( `aasid` varchar(100) NOT NULL,`aaid` varchar(100) NULL DEFAULT NULL,`submissionDate` timestamp NULL DEFAULT NULL,`animals` varchar(100) NULL DEFAULT NULL,`reportReference` varchar(100) NULL DEFAULT NULL,`reportSubmitted` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`aasid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into AssignAnimalStatus(aasid, aaid, submissionDate, animals, reportReference, reportSubmitted) values(:aasid, :aaid, :submissionDate, :animals, :reportReference, :reportSubmitted)")
	public int post(@BindBean AssignAnimalStatus assignanimalstatus_);

	@SqlUpdate("UPDATE AssignAnimalStatus SET aasid=:aasid, aaid=:aaid, submissionDate=:submissionDate, animals=:animals, reportReference=:reportReference, reportSubmitted=:reportSubmitted WHERE aasid=:aasid")
	public int put(@BindBean AssignAnimalStatus assignanimalstatus_);

	@SqlQuery("select * from AssignAnimalStatus;")
	public List<AssignAnimalStatus> list();

	@SqlQuery("select * from AssignAnimalStatus where aasid=:aasid;")
	public AssignAnimalStatus get(@Bind("aasid") String aasid);

	@SqlUpdate("Delete from AssignAnimalStatus where aasid=:aasid;")
	public int delete(@Bind("aasid") String aasid);

}
