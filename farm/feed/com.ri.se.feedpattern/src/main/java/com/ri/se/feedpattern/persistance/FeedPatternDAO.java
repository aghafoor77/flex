package com.ri.se.feedpattern.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(FeedPatternMapper.class)
public interface FeedPatternDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `FeedPattern` ( `fpid` varchar(100) NOT NULL,`certiOfIngredients` varchar(100) NULL DEFAULT NULL,`notes` varchar(100) NULL DEFAULT NULL,`feedName` varchar(100) NULL DEFAULT NULL,`price` varchar(100) NULL DEFAULT NULL,`feedType` varchar(100) NULL DEFAULT NULL,`percentage` varchar(100) NULL DEFAULT NULL,`creationDate` timestamp NULL DEFAULT NULL,`foodSource` varchar(100) NULL DEFAULT NULL, `volume` DECIMAL NULL DEFAULT NULL, PRIMARY KEY (`fpid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into FeedPattern(fpid, certiOfIngredients, notes, feedName, price, feedType, percentage, creationDate, foodSource, volume) values(:fpid, :certiOfIngredients, :notes, :feedName, :price, :feedType, :percentage, :creationDate, :foodSource, :volume)")
	public int post(@BindBean FeedPattern feedpattern_);

	@SqlUpdate("UPDATE FeedPattern SET fpid=:fpid, certiOfIngredients=:certiOfIngredients, notes=:notes, feedName=:feedName, price=:price, feedType=:feedType, percentage=:percentage, creationDate=:creationDate, volume=:volume, foodSource=:foodSource WHERE fpid=:fpid")
	public int put(@BindBean FeedPattern feedpattern_);

	@SqlQuery("select * from FeedPattern;")
	public List<FeedPattern> list();

	@SqlQuery("select * from FeedPattern where fpid=:fpid;")
	public FeedPattern get(@Bind("fpid") String fpid);

	@SqlUpdate("Delete from FeedPattern where fpid=:fpid;")
	public int delete(@Bind("fpid") String fpid);

}


