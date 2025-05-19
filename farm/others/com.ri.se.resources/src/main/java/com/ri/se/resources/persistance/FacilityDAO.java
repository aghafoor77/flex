package com.ri.se.resources.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(FacilityMapper.class)
public interface FacilityDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Facility` ( `facilityNr` varchar(100) NOT NULL,`areasize` varchar(100) NULL DEFAULT NULL,`fromDate` varchar(100) NULL DEFAULT NULL,`movementacrossEU` varchar(100) NULL DEFAULT NULL,`address` varchar(100) NULL DEFAULT NULL,`maxcapacity` varchar(100) NULL DEFAULT NULL,`temporaryactivity` varchar(100) NULL DEFAULT NULL,`anlaggningsnumber` varchar(100) NULL DEFAULT NULL,`specieskept` varchar(100) NULL DEFAULT NULL,`farmId` varchar(100) NULL DEFAULT NULL,`type` varchar(100) NULL DEFAULT NULL,`breedingmaterials` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`facilityNr`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Facility(facilityNr, areasize, fromDate, movementacrossEU, address, maxcapacity, temporaryactivity, anlaggningsnumber, specieskept, farmId, type, breedingmaterials) values(:facilityNr, :areasize, :fromDate, :movementacrossEU, :address, :maxcapacity, :temporaryactivity, :anlaggningsnumber, :specieskept, :farmId, :type, :breedingmaterials)")
	public int post(@BindBean Facility facility_);

	@SqlUpdate("UPDATE Facility SET facilityNr=:facilityNr, areasize=:areasize, fromDate=:fromDate, movementacrossEU=:movementacrossEU, address=:address, maxcapacity=:maxcapacity, temporaryactivity=:temporaryactivity, anlaggningsnumber=:anlaggningsnumber, specieskept=:specieskept, farmId=:farmId, type=:type, breedingmaterials=:breedingmaterials WHERE facilityNr=:facilityNr")
	public int put(@BindBean Facility facility_);

	@SqlQuery("select * from Facility;")
	public List<Facility> list();

	@SqlQuery("select * from Facility where facilityNr=:facilityNr;")
	public Facility get(@Bind("facilityNr") String facilityNr);

	@SqlUpdate("Delete from Facility where facilityNr=:facilityNr;")
	public int delete(@Bind("facilityNr") String facilityNr);

}
