package com.ri.se.animalreg.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(RegisterAnimalMapper.class)
public interface RegisterAnimalDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `RegisterAnimal` ( `animalID` varchar(100) NOT NULL,`receivedFarmID` varchar(100) NULL DEFAULT NULL,`animalIDMother` varchar(100) NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`pregnancyExamination` varchar(100) NULL DEFAULT NULL,`sex` varchar(100) NULL DEFAULT NULL,`weight` bigint(10) NULL DEFAULT NULL,`dateOfBirth` timestamp NULL DEFAULT NULL,`breed` varchar(100) NULL DEFAULT NULL,`birthPlace` varchar(100) NULL DEFAULT NULL,`employerID` varchar(100) NULL DEFAULT NULL,`unit` varchar(100) NULL DEFAULT NULL,`receivedFarmName` varchar(100) NULL DEFAULT NULL,`previousAnimalID` varchar(100) NULL DEFAULT NULL,`aboutAnimal` varchar(100) NULL DEFAULT NULL,`others` varchar(100) NULL DEFAULT NULL,`status` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`animalID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into RegisterAnimal(animalID, receivedFarmID, animalIDMother, notes, pregnancyExamination, sex, weight, dateOfBirth, breed, birthPlace, employerID, unit, receivedFarmName, previousAnimalID, aboutAnimal, others, status) values(:animalID, :receivedFarmID, :animalIDMother, :notes, :pregnancyExamination, :sex, :weight, :dateOfBirth, :breed, :birthPlace, :employerID, :unit, :receivedFarmName, :previousAnimalID, :aboutAnimal, :others, :status)")
	public int post(@BindBean RegisterAnimal registeranimal_);

	@SqlUpdate("UPDATE RegisterAnimal SET animalID=:animalID, receivedFarmID=:receivedFarmID, animalIDMother=:animalIDMother, notes=:notes, pregnancyExamination=:pregnancyExamination, sex=:sex, weight=:weight, dateOfBirth=:dateOfBirth, breed=:breed, birthPlace=:birthPlace, employerID=:employerID, unit=:unit, receivedFarmName=:receivedFarmName, previousAnimalID=:previousAnimalID, aboutAnimal=:aboutAnimal, others=:others, status=:status WHERE animalID=:animalID")
	public int put(@BindBean RegisterAnimal registeranimal_);

	@SqlQuery("select * from RegisterAnimal;")
	public List<RegisterAnimal> list();

	@SqlQuery("select * from RegisterAnimal where animalID=:animalID;")
	public RegisterAnimal get(@Bind("animalID") String animalID);

	@SqlUpdate("Delete from RegisterAnimal where animalID=:animalID;")
	public int delete(@Bind("animalID") String animalID);

}
