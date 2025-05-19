package com.ri.se.assignfeed.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AssignFeedMapper.class)
public interface AssignFeedDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `AssignFeed` ( `afid` varchar(100) NOT NULL,`assignedDate` timestamp NULL DEFAULT NULL, `expectedFinishDate` timestamp NULL DEFAULT NULL, `assignedBy` varchar(100) NULL DEFAULT NULL,`fpid` varchar(100) NULL DEFAULT NULL,`assignedTo` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`afid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into AssignFeed(afid, assignedDate, expectedFinishDate, assignedBy, fpid, assignedTo) values(:afid, :assignedDate, :expectedFinishDate, :assignedBy, :fpid, :assignedTo)")
	public int post(@BindBean AssignFeed assignfeed_);

	@SqlUpdate("UPDATE AssignFeed SET afid=:afid, assignedDate=:assignedDate, expectedFinishDate=:expectedFinishDate, assignedBy=:assignedBy, fpid=:fpid, assignedTo=:assignedTo WHERE afid=:afid")
	public int put(@BindBean AssignFeed assignfeed_);

	@SqlQuery("select * from AssignFeed;")
	public List<AssignFeed> list();

	@SqlQuery("select * from AssignFeed where afid=:afid;")
	public AssignFeed get(@Bind("afid") String afid);
	
	@SqlQuery("select * from AssignFeed where assignedTo=:assignedTo;")
	public List<AssignFeed> getFeedByAnimal(@Bind("assignedTo")String assignedTo);
	
	@SqlQuery("select * from AssignFeed where fpid=:fpid AND assignedTo=:assignedTo;")
	public AssignFeed getByAnimalIdAndFPid(@Bind("assignedTo") String assignedTo, @Bind("fpid") String fpid); 
	
	@SqlQuery("select * from AssignFeed where fpid=:fpid;")
	public List<AssignFeed> getAnimalsByFP(@Bind("fpid") String fpid);
	
	@SqlQuery("select * from AssignFeed where assignedTo=:assignedTo;")
	public List<AssignFeed> getFPByrAnimalID(@Bind("assignedTo") String assignedTo);
	
	@SqlUpdate("Delete from AssignFeed where afid=:afid;")
	public int delete(@Bind("afid") String afid);
	
	@SqlUpdate("Delete from AssignFeed where fpid=:fpid;")
	public int deleteByFP(@Bind("fpid")  String fpid);
	

}
