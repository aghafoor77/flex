package com.ri.se.users.dto;

import java.util.Date;
import com.ri.se.users.persistance.*;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class UsersEntityConverter{

	public DTOUsers getDTOUsers(Users _users)  throws Exception {
		DTOUsers _dTOUsers = new DTOUsers();
		_dTOUsers.setEmail( _users.getEmail());
		_dTOUsers.setPassword( _users.getPassword());
		_dTOUsers.setRoles( _users.getRoles());
		return _dTOUsers;
	}
	private Date toDate(String dateStr) throws Exception {
		String full = "yyyy-MM-dd HH:mm:ss";		String small= "yyyy-MM-dd";			try {
				if(Objects.isNull(dateStr) || dateStr.length() == 0 ) {
					return null;
				}
				if(!dateStr.contains("-")) {
					throw new Exception("Invalid date foramt. It should be either '"+small+"' or '"+full+"'format." );
				}
				if(!dateStr.contains(":")) {
						dateStr+=" 00:00:00";
				}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(full);
				return simpleDateFormat.parse(dateStr);
			}catch(Exception exp) {
				throw new Exception("Invalid date foramt. It should be either '"+small+"' or '"+full+"'format." );
		}
	}
	
	private String toString(Date date) throws Exception {
		String full = "yyyy-MM-dd HH:mm:ss";
		String small= "yyyy-MM-dd";

		String smallTime= "00:00:00";

		if(Objects.isNull(date)) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(full);
			if(!simpleDateFormat.format(date).contains(smallTime))
				return simpleDateFormat.format(date);
			else {
				simpleDateFormat = new SimpleDateFormat(small);
				return simpleDateFormat.format(date);
			}
		}catch(Exception exp) {
			throw new Exception("Invalid date foramt.");
		}
	}


	public Users getUsers(DTOUsers __dTOUsers)  throws Exception {
		Users users = new Users();
		users.setEmail( __dTOUsers.getEmail());
		users.setPassword( __dTOUsers.getPassword());
		users.setRoles( __dTOUsers.getRoles());
		return users;
	}
}
