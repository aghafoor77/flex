package com.rise.se.slaughterhouse.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(RAnimalSlaughterhouseDataMapper.class)
public interface RAnimalSlaughterhouseDataDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `RAnimalSlaughterhouseData` ( `animalID` varchar(100) NOT NULL,`animalIDMother` varchar(100) NULL DEFAULT NULL,`currentWeight` bigint(10) NULL DEFAULT NULL,`currentStatus` varchar(100) NULL DEFAULT NULL,`sex` varchar(100) NULL DEFAULT NULL,`farmId` varchar(100) NULL DEFAULT NULL,`transferDate` varchar(100) NULL DEFAULT NULL,`birthDate` varchar(100) NULL DEFAULT NULL,`transactionID` varchar(100) NULL DEFAULT NULL,`breed` varchar(100) NULL DEFAULT NULL,`birthPlace` varchar(100) NULL DEFAULT NULL,`previousFarmContact` varchar(100) NULL DEFAULT NULL,`receivedFarmName` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`animalID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into RAnimalSlaughterhouseData(animalID, animalIDMother, currentWeight, currentStatus, sex, farmId, transferDate, birthDate, transactionID, breed, birthPlace, previousFarmContact, receivedFarmName) values(:animalID, :animalIDMother, :currentWeight, :currentStatus, :sex, :farmId, :transferDate, :birthDate, :transactionID, :breed, :birthPlace, :previousFarmContact, :receivedFarmName)")
	public int post(@BindBean RAnimalSlaughterhouseData ranimalslaughterhousedata_);

	@SqlUpdate("UPDATE RAnimalSlaughterhouseData SET animalID=:animalID, animalIDMother=:animalIDMother, currentWeight=:currentWeight, currentStatus=:currentStatus, sex=:sex, farmId=:farmId, transferDate=:transferDate, birthDate=:birthDate, transactionID=:transactionID, breed=:breed, birthPlace=:birthPlace, previousFarmContact=:previousFarmContact, receivedFarmName=:receivedFarmName WHERE animalID=:animalID")
	public int put(@BindBean RAnimalSlaughterhouseData ranimalslaughterhousedata_);

	@SqlQuery("select * from RAnimalSlaughterhouseData;")
	public List<RAnimalSlaughterhouseData> list();

	@SqlQuery("select * from RAnimalSlaughterhouseData where animalID=:animalID;")
	public RAnimalSlaughterhouseData get(@Bind("animalID") String animalID);

	@SqlUpdate("Delete from RAnimalSlaughterhouseData where animalID=:animalID;")
	public int delete(@Bind("animalID") String animalID);

}
