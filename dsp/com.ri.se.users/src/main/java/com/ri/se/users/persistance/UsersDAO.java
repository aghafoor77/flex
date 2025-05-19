package com.ri.se.users.persistance;

import java.util.List;
import java.util.Date;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UsersMapper.class)
public interface UsersDAO{
	@SqlUpdate("CREATE TABLE IF NOT EXISTS `Users` ( `veid` varchar(100) NOT NULL, `email` varchar(100) NOT NULL,`password` varchar(100) NULL DEFAULT NULL,`roles` varchar(100) NULL DEFAULT NULL, PRIMARY KEY (`veid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;")
	public void create();

	@SqlUpdate("insert into Users(veid, email, password, roles) values(:veid, :email, :password, :roles)")
	public int post(@BindBean Users users_);

	@SqlUpdate("UPDATE Users SET email=:email, password=:password, roles=:roles WHERE veid=:veid")
	public int put(@BindBean Users users_);

	@SqlQuery("select * from Users where roles=:roles;")
	public List<Users> listUserByRole(@Bind("roles") String roles);
	
	@SqlQuery("select * from Users;")
	public List<Users> list();

	@SqlQuery("select * from Users where email=:email;")
	public Users get(@Bind("email") String email);
	
	@SqlQuery("select * from Users where veid=:veid;")
	public Users getByVeid(@Bind("veid") String veid);
	
	
	@SqlUpdate("Delete from Users where email=:email;")
	public int delete(@Bind("email") String email);

}
