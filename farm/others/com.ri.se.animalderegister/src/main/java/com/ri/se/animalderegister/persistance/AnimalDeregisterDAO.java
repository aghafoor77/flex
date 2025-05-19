package com.ri.se.animalderegister.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AnimalDeregisterMapper.class)
public interface AnimalDeregisterDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `AnimalDeregister` ( `animalID` varchar(100) NOT NULL,`deregisterDate` timestamp NULL DEFAULT NULL,`reportBy` varchar(100) NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`reportTo` varchar(100) NULL DEFAULT NULL,`response` varchar(100) NULL DEFAULT NULL,`location` varchar(100) NULL DEFAULT NULL,`emailTo` varchar(100) NULL DEFAULT NULL,`dcode` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`animalID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into AnimalDeregister(animalID, deregisterDate, reportBy, notes, reportTo, response, location, emailTo, dcode) values(:animalID, :deregisterDate, :reportBy, :notes, :reportTo, :response, :location, :emailTo, :dcode)")
	public int post(@BindBean AnimalDeregister animalderegister_);

	@SqlUpdate("UPDATE AnimalDeregister SET animalID=:animalID, deregisterDate=:deregisterDate, reportBy=:reportBy, notes=:notes, reportTo=:reportTo, response=:response, location=:location, emailTo=:emailTo, dcode=:dcode WHERE animalID=:animalID")
	public int put(@BindBean AnimalDeregister animalderegister_);

	@SqlQuery("select * from AnimalDeregister;")
	public List<AnimalDeregister> list();

	@SqlQuery("select * from AnimalDeregister where animalID=:animalID;")
	public AnimalDeregister get(@Bind("animalID") String animalID);

	@SqlUpdate("Delete from AnimalDeregister where animalID=:animalID;")
	public int delete(@Bind("animalID") String animalID);

}
