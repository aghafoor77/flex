package com.ri.se.animalexamination.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AnimalExaminationMapper.class)
public interface AnimalExaminationDAO{
	
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `AnimalExamination` ( `aeid` varchar(100) NOT NULL,`notes` varchar(100) NULL DEFAULT NULL,`examinationDate` timestamp NULL DEFAULT NULL,`refnumber` varchar(100) NULL DEFAULT NULL,`refType` varchar(100) NULL DEFAULT NULL,`sensorData` varchar(100) NULL DEFAULT NULL,`employeeID` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL,`extepctedDate` timestamp NULL DEFAULT NULL,`status` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`aeid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into AnimalExamination(aeid, notes, examinationDate, refnumber, refType, sensorData, employeeID, animalID, extepctedDate, status) values(:aeid, :notes, :examinationDate, :refnumber, :refType, :sensorData, :employeeID, :animalID, :extepctedDate, :status)")
	public int post(@BindBean AnimalExamination animalexamination_);

	@SqlUpdate("UPDATE AnimalExamination SET aeid=:aeid, notes=:notes, examinationDate=:examinationDate, refnumber=:refnumber, refType=:refType, sensorData=:sensorData, employeeID=:employeeID, animalID=:animalID, extepctedDate=:extepctedDate, status=:status WHERE aeid=:aeid")
	public int put(@BindBean AnimalExamination animalexamination_);

	@SqlQuery("select * from AnimalExamination;")
	public List<AnimalExamination> list();

	@SqlQuery("select * from AnimalExamination where aeid=:aeid;")
	public AnimalExamination get(@Bind("aeid") String aeid);

	@SqlQuery("select * from AnimalExamination where status=:status;")
	public List<AnimalExamination> getListByStatus(@Bind("status") String status);
	
	@SqlQuery("select * from AnimalExamination where animalID=:animalID;")
	public List<AnimalExamination> getByAnimalID(@Bind("animalID")String animalID);
	
	@SqlQuery("select * from AnimalExamination where animalID=:animalID AND status=:status;")
	public List<AnimalExamination> getListByAnimalAndStatus(@Bind("animalID") String animalID, @Bind("status") String status);
	
	@SqlUpdate("Delete from AnimalExamination where aeid=:aeid;")
	public int delete(@Bind("aeid") String aeid);

}
