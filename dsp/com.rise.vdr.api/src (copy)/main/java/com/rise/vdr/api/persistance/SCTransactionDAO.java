package com.rise.vdr.api.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SCTransactionMapper.class)
public interface SCTransactionDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `SCTransaction` ( `eid` varchar(100) NOT NULL,`transactiondDate` varchar(100) NULL DEFAULT NULL,`trasactionReceiver` varchar(100) NULL DEFAULT NULL,`tarnsactionOwner` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL,`transactionID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`eid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into SCTransaction(eid, transactiondDate, trasactionReceiver, tarnsactionOwner, animalID, transactionID) values(:eid, :transactiondDate, :trasactionReceiver, :tarnsactionOwner, :animalID, :transactionID)")
	public int post(@BindBean SCTransaction sctransaction_);

	@SqlUpdate("UPDATE SCTransaction SET eid=:eid, transactiondDate=:transactiondDate, trasactionReceiver=:trasactionReceiver, tarnsactionOwner=:tarnsactionOwner, animalID=:animalID, transactionID=:transactionID WHERE eid=:eid")
	public int put(@BindBean SCTransaction sctransaction_);

	@SqlQuery("select * from SCTransaction;")
	public List<SCTransaction> list();

	@SqlQuery("select * from SCTransaction where eid=:eid;")
	public SCTransaction getTrans(@Bind("eid") String eid);
	

	@SqlUpdate("Delete from SCTransaction where eid=:eid;")
	public int delete(@Bind("eid") String eid);

}
