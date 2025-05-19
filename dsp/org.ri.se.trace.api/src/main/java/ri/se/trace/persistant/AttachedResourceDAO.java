package ri.se.trace.persistant;



import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AttachedResourceMapper.class)
public interface AttachedResourceDAO {
	
	
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `AttachedResource` (" 
			+ "`ipfshash` varchar(128) NOT NULL,"
			+ "`owner` varchar(520) NOT NULL,"
			+ "`name` varchar(128) NOT NULL,"
			+ "`identity` varchar(128) NOT NULL,"
			+ "`type` varchar(50) NOT NULL,"
			+ "`securityLevel` varchar(50) NOT NULL,"
			+ "`uploadTime` timestamp NOT NULL,"
			+ "`description` varchar(512) DEFAULT NULL,"
			+ "`status` varchar(12) DEFAULT NULL,"
			+ " PRIMARY KEY (`ipfshash`)"
			+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public int create();

	@SqlUpdate("insert into AttachedResource(ipfshash, owner, name, identity, type, securityLevel, uploadTime, description, status) values(:ipfshash, :owner, :name, :identity, :type, :securityLevel, :uploadTime, :description, :status)")
	public int post(@BindBean AttachedResource attachedResource);

	@SqlQuery("select * from AttachedResource WHERE ipfshash = :ipfshash order by uploadTime DESC;")
	public AttachedResource getAttachedResource(@Bind("ipfshash") String ipfshash);
	
	@SqlQuery("select * from AttachedResource where owner = :owner order by uploadTime DESC;")
	public List<AttachedResource> getResourceByOwner(@Bind("owner") String owner);

	@SqlQuery("select * from AttachedResource WHERE name = :name order by uploadTime DESC")
	public List<AttachedResource> getAttachedResourceByName(@Bind("name") String name);

	@SqlQuery("select * from AttachedResource WHERE identity = :identity order by uploadTime DESC")
	public List<AttachedResource> getAttachedResourceByIdentity(@Bind("identity") String identity);
	
	@SqlQuery("select * from AttachedResource WHERE status = :status order by uploadTime DESC")
	public List<AttachedResource> getAttachedResourceByStatus(@Bind("status") String status);
	
	@SqlQuery("select * from AttachedResource WHERE identity=:identity AND status = :status order by uploadTime DESC")
	public List<AttachedResource> getAttachedResourceByIdentityAndStatus(@Bind("identity")String identity, @Bind("status") String status);
	
	@SqlQuery("select * from AttachedResource WHERE identity=:identity AND status=:status AND owner=:owner order by uploadTime DESC")
	public List<AttachedResource> getAttachedResourceByIdentityStatusAndAddress(@Bind("identity")String identity, @Bind("owner") String owner, @Bind("status") String status);
		
	@SqlUpdate("Delete from AttachedResource WHERE ipfshash = :ipfshash;")
	public int delete(@Bind("ipfshash") String ipfshash);
	
	
	@SqlQuery("select * from AttachedResource;")
	public List<AttachedResource> list();
}
