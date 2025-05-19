package ri.se.trace.persistant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AttachedResourceMapper implements ResultSetMapper<AttachedResource> {

	private static final String ipfshash = "ipfshash";
	private static final String owner = "owner";
	private static final String name = "name";
	private static final String identity = "identity";
	private static final String type = "type";
	private static final String securityLevel = "securityLevel";
	private static final String uploadTime = "uploadTime";
	private static final String description = "description";
	private static final String status = "status";

	public AttachedResource map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new AttachedResource(resultSet.getString(ipfshash), resultSet.getString(owner),
				resultSet.getString(name), resultSet.getString(identity), resultSet.getString(type), resultSet.getString(securityLevel),
				resultSet.getTimestamp(uploadTime), resultSet.getString(description), resultSet.getString(status));
	}
}
