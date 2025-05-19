package com.ri.se.movebullforherd.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(MoveBullForHerdMapper.class)
public interface MoveBullForHerdDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `MoveBullForHerd` ( `mb4hid` varchar(100) NOT NULL,`notes` varchar(100) NULL DEFAULT NULL,`entryDate` timestamp NULL DEFAULT NULL,`groupFemale` varchar(100) NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`location` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL,`exitDate` timestamp NULL DEFAULT NULL, PRIMARY KEY (`mb4hid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into MoveBullForHerd(mb4hid, notes, entryDate, groupFemale, employeeID, location, animalID, exitDate) values(:mb4hid, :notes, :entryDate, :groupFemale, :employeeID, :location, :animalID, :exitDate)")
	public int post(@BindBean MoveBullForHerd movebullforherd_);

	@SqlUpdate("UPDATE MoveBullForHerd SET mb4hid=:mb4hid, notes=:notes, entryDate=:entryDate, groupFemale=:groupFemale, employeeID=:employeeID, location=:location, animalID=:animalID, exitDate=:exitDate WHERE mb4hid=:mb4hid")
	public int put(@BindBean MoveBullForHerd movebullforherd_);

	@SqlQuery("select * from MoveBullForHerd;")
	public List<MoveBullForHerd> list();

	@SqlQuery("select * from MoveBullForHerd where mb4hid=:mb4hid;")
	public MoveBullForHerd get(@Bind("mb4hid") String mb4hid);
	
	@SqlQuery("select * from MoveBullForHerd where animalID=:animalID OR groupFemale LIKE :l;")
	public List<MoveBullForHerd> getAnimalMovement(@Bind("animalID") String animalID, @Bind("l") String l);

	@SqlUpdate("Delete from MoveBullForHerd where mb4hid=:mb4hid;")
	public int delete(@Bind("mb4hid") String mb4hid);

}
