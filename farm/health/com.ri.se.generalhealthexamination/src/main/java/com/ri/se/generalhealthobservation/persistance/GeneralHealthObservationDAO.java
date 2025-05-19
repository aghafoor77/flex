package com.ri.se.generalhealthobservation.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(GeneralHealthObservationMapper.class)
public interface GeneralHealthObservationDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `GeneralHealthObservation` ( `ghoid` varchar(100) NOT NULL,`limping` varchar(100) NULL DEFAULT NULL,`observer` varchar(100) NULL DEFAULT NULL,`wound` varchar(100) NULL DEFAULT NULL,`bcs` varchar(100) NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`swelling` varchar(100) NULL DEFAULT NULL,`gheDate` timestamp NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL,`weight` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`ghoid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into GeneralHealthObservation(ghoid, limping, observer, wound, bcs, notes, swelling, gheDate, animalID, weight) values(:ghoid, :limping, :observer, :wound, :bcs, :notes, :swelling, :gheDate, :animalID, :weight)")
	public int post(@BindBean GeneralHealthObservation generalhealthobservation_);

	@SqlUpdate("UPDATE GeneralHealthObservation SET ghoid=:ghoid, limping=:limping, observer=:observer, wound=:wound, bcs=:bcs, notes=:notes, swelling=:swelling, gheDate=:gheDate, animalID=:animalID, weight=:weight WHERE ghoid=:ghoid")
	public int put(@BindBean GeneralHealthObservation generalhealthobservation_);

	@SqlQuery("select * from GeneralHealthObservation;")
	public List<GeneralHealthObservation> list();

	@SqlQuery("select * from GeneralHealthObservation where ghoid=:ghoid;")
	public GeneralHealthObservation get(@Bind("ghoid") String ghoid);
	
	@SqlQuery("select * from GeneralHealthObservation where animalID=:animalID;")
	public List<GeneralHealthObservation> getByAnimalID(@Bind("animalID") String animalID);
	
	@SqlQuery("select * from GeneralHealthObservation where animalID=:animalID ORDER BY gheDate DESC;")
	List<GeneralHealthObservation> getByAnimal(@Bind("animalID") String animalID);

	@SqlUpdate("Delete from GeneralHealthObservation where ghoid=:ghoid;")
	public int delete(@Bind("ghoid") String ghoid);

}
