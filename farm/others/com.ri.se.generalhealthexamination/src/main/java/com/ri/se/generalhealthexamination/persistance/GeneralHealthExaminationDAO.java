package com.ri.se.generalhealthexamination.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(GeneralHealthExaminationMapper.class)
public interface GeneralHealthExaminationDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `GeneralHealthExamination` ( `gaheid` varchar(100) NOT NULL,`observer` varchar(100) NULL DEFAULT NULL,`wound` varchar(100) NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`notation` varchar(100) NULL DEFAULT NULL,`temperature` varchar(100) NULL DEFAULT NULL,`infections` varchar(100) NULL DEFAULT NULL,`lameness` varchar(100) NULL DEFAULT NULL,`swelling` varchar(100) NULL DEFAULT NULL,`gheDate` timestamp NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL,`weak` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`gaheid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into GeneralHealthExamination(gaheid, observer, wound, notes, notation, temperature, infections, lameness, swelling, gheDate, animalID, weak) values(:gaheid, :observer, :wound, :notes, :notation, :temperature, :infections, :lameness, :swelling, :gheDate, :animalID, :weak)")
	public int post(@BindBean GeneralHealthExamination generalhealthexamination_);

	@SqlUpdate("UPDATE GeneralHealthExamination SET gaheid=:gaheid, observer=:observer, wound=:wound, notes=:notes, notation=:notation, temperature=:temperature, infections=:infections, lameness=:lameness, swelling=:swelling, gheDate=:gheDate, animalID=:animalID, weak=:weak WHERE gaheid=:gaheid")
	public int put(@BindBean GeneralHealthExamination generalhealthexamination_);

	@SqlQuery("select * from GeneralHealthExamination;")
	public List<GeneralHealthExamination> list();

	@SqlQuery("select * from GeneralHealthExamination where gaheid=:gaheid;")
	public GeneralHealthExamination get(@Bind("gaheid") String gaheid);
	
	@SqlQuery("select * from GeneralHealthExamination where animalID=:animalID;")
	public List<GeneralHealthExamination> getByAnimal(@Bind("animalID") String animalID);

	@SqlUpdate("Delete from GeneralHealthExamination where gaheid=:gaheid;")
	public int delete(@Bind("gaheid") String gaheid);

}
