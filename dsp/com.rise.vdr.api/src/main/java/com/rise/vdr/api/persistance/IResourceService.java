package com.rise.vdr.api.persistance;

import java.util.List;

public interface IResourceService {


	public void create();
	public int post(SCResource scresource_);
	public int put(SCResource scresource_);
	public List<SCResource> list();
	public List<SCResource> listByRole(String role) ;
	public SCResource get(String did);
	public int delete(String did);
	public String performHealthCheck();
	
}
