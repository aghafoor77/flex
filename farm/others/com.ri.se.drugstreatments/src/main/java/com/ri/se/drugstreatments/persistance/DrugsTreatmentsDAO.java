package com.ri.se.drugstreatments.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(DrugsTreatmentsMapper.class)
public interface DrugsTreatmentsDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `DrugsTreatments` ( `dtid` varchar(100) NOT NULL,`assignedDate` timestamp NULL DEFAULT NULL,`reason` varchar(100) NULL DEFAULT NULL,`drungs` varchar(100) NULL DEFAULT NULL,`refnumber` varchar(100) NULL DEFAULT NULL,`examinedBy` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL,`reftype` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`dtid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into DrugsTreatments(dtid, assignedDate, reason, drungs, refnumber, examinedBy, animalID, reftype) values(:dtid, :assignedDate, :reason, :drungs, :refnumber, :examinedBy, :animalID, :reftype)")
	public int post(@BindBean DrugsTreatments drugstreatments_);

	@SqlUpdate("UPDATE DrugsTreatments SET dtid=:dtid, assignedDate=:assignedDate, reason=:reason, drungs=:drungs, refnumber=:refnumber, examinedBy=:examinedBy, animalID=:animalID, reftype=:reftype WHERE dtid=:dtid")
	public int put(@BindBean DrugsTreatments drugstreatments_);

	@SqlQuery("select * from DrugsTreatments;")
	public List<DrugsTreatments> list();

	@SqlQuery("select * from DrugsTreatments where dtid=:dtid;")
	public DrugsTreatments get(@Bind("dtid") String dtid);
	
	@SqlQuery("select * from DrugsTreatments where animalID=:animalID;")
	public List<DrugsTreatments> listByAnimal(@Bind("animalID") String animalID);
	
	@SqlUpdate("Delete from DrugsTreatments where dtid=:dtid;")
	public int delete(@Bind("dtid") String dtid);

}
