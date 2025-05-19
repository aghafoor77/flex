package centralhostregistration.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class CentralHostRegistrationMapper implements ResultSetMapper<CentralHostRegistration> {

	private static final String  name = "name";
	private static final String  address = "address";
	private static final String  port = "port";
	private static final String  healthCheck = "healthCheck";
	private static final String  registrationDate = "registrationDate";
	private static final String  status = "status";
	private static final String  hb = "hb";
	private static final String  type = "type";
	
	public CentralHostRegistration map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new CentralHostRegistration(resultSet.getString(name), resultSet.getString(address), resultSet.getInt(port), resultSet.getString(healthCheck), resultSet.getTimestamp(registrationDate), resultSet.getString(status), resultSet.getInt(hb), resultSet.getString(type));

	}
}
