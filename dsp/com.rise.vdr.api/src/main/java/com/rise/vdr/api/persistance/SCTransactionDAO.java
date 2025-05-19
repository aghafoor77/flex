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
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `SCTransaction` ( `transactionID` varchar(100) NOT NULL,`transactiondDate` BIGINT NULL DEFAULT NULL,`trasactionReceiver` varchar(100) NULL DEFAULT NULL,`tarnsactionOwner` varchar(100) NULL DEFAULT NULL,`animalID` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`transactionID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into SCTransaction(transactionID, transactiondDate, trasactionReceiver, tarnsactionOwner, animalID) values(:transactionID, :transactiondDate, :trasactionReceiver, :tarnsactionOwner, :animalID)")
	public int post(@BindBean SCTransaction sctransaction_);

	@SqlUpdate("UPDATE SCTransaction SET transactionID=:transactionID, transactiondDate=:transactiondDate, trasactionReceiver=:trasactionReceiver, tarnsactionOwner=:tarnsactionOwner, animalID=:animalID WHERE transactionID=:transactionID")
	public int put(@BindBean SCTransaction sctransaction_);

	@SqlQuery("select * from SCTransaction;")
	public List<SCTransaction> list();

	@SqlQuery("select * from SCTransaction where transactionID=:transactionID;")
	public SCTransaction get(@Bind("transactionID") String transactionID);
	
	@SqlQuery("select * from SCTransaction where animalID=:animalID ORDER BY transactiondDate;")
	public List<SCTransaction> isAnimalExist(@Bind("animalID") String  animalID);
	
	@SqlQuery("select * from SCTransaction where trasactionReceiver=:trasactionReceiver;")
	public List<SCTransaction> getOwnedAnimals(@Bind("trasactionReceiver") String transactionReceiver);
	
	@SqlQuery("select * from SCTransaction where trasactionReceiver=:trasactionReceiver AND transactiondDate > :transactiondDate;")
	public List<SCTransaction> getOwnedUpdatedAnimals(@Bind("trasactionReceiver") String transactionReceiver, @Bind("transactiondDate") long transactiondDate);
	
	
	@SqlUpdate("Delete from SCTransaction where transactionID=:transactionID;")
	public int delete(@Bind("transactionID") String transactionID);
	
	
	@SqlQuery("SELECT * FROM SCTransaction	WHERE animalID = :animalID	AND transactiondDate = ( SELECT MIN(transactiondDate) FROM SCTransaction WHERE animalID =:animalID);")
	public List<SCTransaction> fetchFirstTrasaction(@Bind("animalID") String  animalID);
	
	
	

}
