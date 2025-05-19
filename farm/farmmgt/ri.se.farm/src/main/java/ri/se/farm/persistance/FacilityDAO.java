package ri.se.farm.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(FacilityMapper.class)
public interface FacilityDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Facility` ( `facilityId` varchar(100) NOT NULL,`address` varchar(100) NULL DEFAULT NULL,`maxcapacity` bigint(10) NULL DEFAULT NULL,`temporaryactivity` varchar(100) NULL DEFAULT NULL,`anlaggningsnumber` varchar(100) NULL DEFAULT NULL,`farmId` varchar(100) NULL DEFAULT NULL,`type` varchar(100) NULL DEFAULT NULL,`areasize` varchar(100) NULL DEFAULT NULL,`fromDate` timestamp NULL DEFAULT NULL,`movementacrossEU` tinyint(1) NULL DEFAULT NULL,`facilityNr` varchar(100) NULL DEFAULT NULL,`specieskept` varchar(100) NULL DEFAULT NULL,`breedingmaterials` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`facilityId`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Facility(facilityId, address, maxcapacity, temporaryactivity, anlaggningsnumber, farmId, type, areasize, fromDate, movementacrossEU, facilityNr, specieskept, breedingmaterials) values(:facilityId, :address, :maxcapacity, :temporaryactivity, :anlaggningsnumber, :farmId, :type, :areasize, :fromDate, :movementacrossEU, :facilityNr, :specieskept, :breedingmaterials)")
	public int post(@BindBean Facility facility_);

	@SqlUpdate("UPDATE Facility SET facilityId=:facilityId, address=:address, maxcapacity=:maxcapacity, temporaryactivity=:temporaryactivity, anlaggningsnumber=:anlaggningsnumber, farmId=:farmId, type=:type, areasize=:areasize, fromDate=:fromDate, movementacrossEU=:movementacrossEU, facilityNr=:facilityNr, specieskept=:specieskept, breedingmaterials=:breedingmaterials WHERE facilityId=:facilityId")
	public int put(@BindBean Facility facility_);

	@SqlQuery("select * from Facility;")
	public List<Facility> list();

	@SqlQuery("select * from Facility where facilityId=:facilityId;")
	public Facility get(@Bind("facilityId") String facilityId);

	@SqlQuery("select * from Facility where farmId=:farmId;")
	public List<Facility> getbyfarmid(@Bind("farmId") String farmId);
	
	@SqlQuery("select * from Facility where facilityNr=:facilityNr and farmId=:farmId;")
	public Facility getByFacilityFarm(@Bind("facilityNr") String facilityNr, @Bind("farmId") String farmId);
		
	@SqlUpdate("Delete from Facility where facilityId=:facilityId;")
	public int delete(@Bind("facilityId") String facilityId);

}
