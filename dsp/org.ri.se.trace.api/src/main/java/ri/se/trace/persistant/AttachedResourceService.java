package ri.se.trace.persistant;

import java.util.List;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AttachedResourceService {
	
	private static final String DATABASE_REACH_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String DATABASE_UNEXPECTED_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";

	final static Logger logger = LoggerFactory.getLogger(AttachedResourceService.class);

	@CreateSqlObject
	abstract AttachedResourceDAO attachedResourceDAO();

	public int create() {
		return attachedResourceDAO().create();
	}
	
	public int post(@BindBean AttachedResource attachedResource) {
		return attachedResourceDAO().post(attachedResource);
	}

	public AttachedResource getAttachedResource(String ipfshash) {
		return attachedResourceDAO().getAttachedResource(ipfshash);
	}
		
	public List<AttachedResource> getResourceByOwner(String owner){
		return attachedResourceDAO().getResourceByOwner(owner);
	}

	public List<AttachedResource> getAttachedResourceByName(String name){
		return attachedResourceDAO().getAttachedResourceByName(name);
	}

	public List<AttachedResource> getAttachedResourceByIdentity(String identity){
		return attachedResourceDAO().getAttachedResourceByIdentity(identity);
	}
	
	public List<AttachedResource> getAttachedResourceByStatus(String status){
		return attachedResourceDAO().getAttachedResourceByStatus(status);
	}
	public List<AttachedResource> getAttachedResourceByIdentityAndStatus(String identity, String status){
		return attachedResourceDAO().getAttachedResourceByIdentityAndStatus(identity, status);
	}
	public List<AttachedResource> getAttachedResourceByIdentityStatusAndAddress(String identity, String owner, String status){
		return attachedResourceDAO().getAttachedResourceByIdentityStatusAndAddress(identity, owner, status);	
	}
	
	public List<AttachedResource> list(){
		return attachedResourceDAO().list();
	}

	public AttachedResource delete(String ipfdcid) {
		AttachedResource ss = getAttachedResource(ipfdcid);
		attachedResourceDAO().delete(ipfdcid);
		return ss;
	}
	public String performHealthCheck() {
		try {
			attachedResourceDAO().list();
		} catch (UnableToObtainConnectionException ex) {
			return checkUnableToObtainConnectionException(ex);
		} catch (UnableToExecuteStatementException ex) {
			return checkUnableToExecuteStatementException(ex);
		} catch (Exception ex) {
			return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
		}
		return null;
	}

	private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
		if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
			return DATABASE_REACH_ERROR + ex.getCause().getLocalizedMessage();
		} else if (ex.getCause() instanceof java.sql.SQLException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
		}
	}

	private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
		if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
		}
	}

}
