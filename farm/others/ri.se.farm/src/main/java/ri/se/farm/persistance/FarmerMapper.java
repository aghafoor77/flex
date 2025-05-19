package ri.se.farm.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class FarmerMapper implements ResultSetMapper<Farmer> {

	private static final String  resourceId = "resourceId";
	private static final String  country = "country";
	private static final String  role = "role";
	private static final String  street = "street";
	private static final String  contact = "contact";
	private static final String  name = "name";
	private static final String  postcode = "postcode";
	private static final String  county = "county";
	private static final String  municipality = "municipality";
	private static final String  farmId = "farmId";
	private static final String  email = "email";

	public Farmer map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Farmer(resultSet.getString(resourceId), resultSet.getString(country), resultSet.getString(role), resultSet.getString(street), resultSet.getString(contact), resultSet.getString(name), resultSet.getString(postcode), resultSet.getString(county), resultSet.getString(municipality), resultSet.getString(farmId), resultSet.getString(email));

	}
}
