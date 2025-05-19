package com.ri.se.breed.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(BreedMapper.class)
public interface BreedDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Breed` ( `breedname` varchar(100) NOT NULL, PRIMARY KEY (`breedname`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Breed(breedname) values(:breedname)")
	public int post(@BindBean Breed breed_);

	@SqlUpdate("UPDATE Breed SET breedname=:breedname WHERE breedname=:breedname")
	public int put(@BindBean Breed breed_);

	@SqlQuery("select * from Breed;")
	public List<Breed> list();

	@SqlQuery("select * from Breed where breedname=:breedname;")
	public Breed get(@Bind("breedname") String breedname);

	@SqlUpdate("Delete from Breed where breedname=:breedname;")
	public int delete(@Bind("breedname") String breedname);

}
