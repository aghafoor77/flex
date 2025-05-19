package org.ri.se.ipfsj.lsc;

public interface IAttachedResourceHandler {

	public DataResourceProps upload(DataResourceProps file) throws Exception;

	public DataResourceProps[] upload(DataResourceProps[] files)  throws Exception;
}
