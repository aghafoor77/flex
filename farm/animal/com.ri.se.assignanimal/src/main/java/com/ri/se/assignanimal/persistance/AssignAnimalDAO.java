package com.ri.se.assignanimal.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AssignAnimalMapper.class)
public interface AssignAnimalDAO{
	
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `AssignAnimal` ( `aaid` varchar(100) NOT NULL,`assignedDate` timestamp NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`examiner` varchar(100) NULL DEFAULT NULL,`action` varchar(100) NULL DEFAULT NULL, `assignedBy` varchar(50) NULL DEFAULT NULL, PRIMARY KEY (`aaid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into AssignAnimal(aaid, assignedDate, notes, examiner, action, assignedBy) values(:aaid, :assignedDate, :notes, :examiner, :action, :assignedBy)")
	public int post(@BindBean AssignAnimal assignanimal_);

	@SqlUpdate("UPDATE AssignAnimal SET aaid=:aaid, assignedDate=:assignedDate, notes=:notes, examiner=:examiner, action=:action, assignedBy=:assignedBy WHERE aaid=:aaid")
	public int put(@BindBean AssignAnimal assignanimal_);

	@SqlQuery("select * from AssignAnimal;")
	public List<AssignAnimal> list();

	@SqlQuery("select * from AssignAnimal where aaid=:aaid;")
	public AssignAnimal get(@Bind("aaid") String aaid);

	@SqlQuery("select * from AssignAnimal where aaid in (select aaid from AssignAnimalStatus where animals=:animals);")
	public List<AssignAnimal> getByAnimlalID(@Bind("animals") String animals);
	
	@SqlQuery("select * from AssignAnimal where examiner=:examiner;")
	public List<AssignAnimal> getByExaminer(@Bind("examiner") String examiner);
		
	@SqlUpdate("Delete from AssignAnimal where aaid=:aaid;")
	public int delete(@Bind("aaid") String aaid);
}