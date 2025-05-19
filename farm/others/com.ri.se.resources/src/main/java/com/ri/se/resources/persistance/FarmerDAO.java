package com.ri.se.resources.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(FarmerMapper.class)
public interface FarmerDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Farmer` ( `resourceId` varchar(100) NOT NULL,`country` varchar(100) NULL DEFAULT NULL,`role` varchar(100) NULL DEFAULT NULL,`street` varchar(100) NULL DEFAULT NULL,`contact` varchar(100) NULL DEFAULT NULL,`name` varchar(100) NULL DEFAULT NULL,`postcode` varchar(100) NULL DEFAULT NULL,`county` varchar(100) NULL DEFAULT NULL,`municipality` varchar(100) NULL DEFAULT NULL,`farmId` varchar(100) NULL DEFAULT NULL,`email` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`resourceId`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Farmer(resourceId, country, role, street, contact, name, postcode, county, municipality, farmId, email) values(:resourceId, :country, :role, :street, :contact, :name, :postcode, :county, :municipality, :farmId, :email)")
	public int post(@BindBean Farmer farmer_);

	@SqlUpdate("UPDATE Farmer SET resourceId=:resourceId, country=:country, role=:role, street=:street, contact=:contact, name=:name, postcode=:postcode, county=:county, municipality=:municipality, farmId=:farmId, email=:email WHERE resourceId=:resourceId")
	public int put(@BindBean Farmer farmer_);

	@SqlQuery("select * from Farmer;")
	public List<Farmer> list();

	@SqlQuery("select * from Farmer where resourceId=:resourceId;")
	public Farmer get(@Bind("resourceId") String resourceId);

	@SqlUpdate("Delete from Farmer where resourceId=:resourceId;")
	public int delete(@Bind("resourceId") String resourceId);

}
