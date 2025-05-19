package com.rise.vdr.api.utils;

import java.util.Hashtable;

public interface IAuthenticator {
	public static final String OWNER = "owner";
	public static final String PASSWORD = "pass";
	public static final String ADDRESS = "dltaddress";
	public static final String RSAPUBLICKEY= "rsapublickey";
	public Hashtable<String, String> authenticate(String info);

}
