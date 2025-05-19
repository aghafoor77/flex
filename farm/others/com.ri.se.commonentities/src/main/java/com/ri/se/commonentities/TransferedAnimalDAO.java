/*
 * package com.ri.se.commonentities;
 * 
 * import java.util.List;
 * 
 * import org.skife.jdbi.v2.sqlobject.Bind; import
 * org.skife.jdbi.v2.sqlobject.BindBean; import
 * org.skife.jdbi.v2.sqlobject.SqlQuery; import
 * org.skife.jdbi.v2.sqlobject.SqlUpdate; import
 * org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
 * 
 * @RegisterMapper(TransferedAnimalMapper.class) public interface
 * TransferedAnimalDAO{
 * 
 * @SqlUpdate("CREATE TABLE IF NOT EXISTS `TransferedAnimal` ( `animalID` varchar(100) NOT NULL,`animalIDMother` varchar(100) NULL DEFAULT NULL,`birthPlace` varchar(100) NULL DEFAULT NULL,`previousFarmContact` varchar(100) NULL DEFAULT NULL,`currentWeight` bigint(10) NULL DEFAULT NULL,`receivedFarmName` varchar(100) NULL DEFAULT NULL,`sex` varchar(100) NULL DEFAULT NULL,`farmId` varchar(100) NULL DEFAULT NULL,`transferDate` timestamp NULL DEFAULT NULL,`birthDate` timestamp NULL DEFAULT NULL,`breed` varchar(100) NULL DEFAULT NULL,`transactionID` varchar(100) NULL DEFAULT NULL,`transactionTime` BIGINT NULL DEFAULT NULL, `currentStatus` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`animalID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;"
 * ) public void create();
 * 
 * @SqlUpdate("insert into TransferedAnimal(animalID, animalIDMother, birthPlace, previousFarmContact, currentWeight, receivedFarmName, sex, farmId, transferDate, birthDate, breed, transactionID, transactionTime, currentStatus) values(:animalID, :animalIDMother, :birthPlace, :previousFarmContact, :currentWeight, :receivedFarmName, :sex, :farmId, :transferDate, :birthDate, :breed, :transactionID, :transactionTime, :currentStatus )"
 * ) public int post(@BindBean TransferedAnimal transferedanimal_);
 * 
 * @SqlUpdate("UPDATE TransferedAnimal SET animalID=:animalID, animalIDMother=:animalIDMother, birthPlace=:birthPlace, previousFarmContact=:previousFarmContact, currentWeight=:currentWeight, receivedFarmName=:receivedFarmName, sex=:sex, farmId=:farmId, transferDate=:transferDate, birthDate=:birthDate, breed=:breed, transactionID=:transactionID, transactionTime=:transactionTime WHERE animalID=:animalID, currentStatus =:currentStatus"
 * ) public int put(@BindBean TransferedAnimal transferedanimal_);
 * 
 * @SqlQuery("select * from TransferedAnimal;") public List<TransferedAnimal>
 * list();
 * 
 * @SqlQuery("select * from TransferedAnimal where animalID=:animalID;") public
 * TransferedAnimal get(@Bind("animalID") String animalID);
 * 
 * @SqlUpdate("Delete from TransferedAnimal where animalID=:animalID;") public
 * int delete(@Bind("animalID") String animalID);
 * 
 * @SqlQuery("select MAX(transactionTime) from TransferedAnimal;") public
 * TransferedAnimal getLatest();
 * 
 * 
 * }
 */