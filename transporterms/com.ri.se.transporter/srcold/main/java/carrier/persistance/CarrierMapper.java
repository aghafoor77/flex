package carrier.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class CarrierMapper implements ResultSetMapper<Carrier> {

	private static final String  CID = "CID";
	private static final String  carrierNumber = "carrierNumber";
	private static final String  species = "species";
	private static final String  transportType = "transportType";
	private static final String  TID = "TID";
	private static final String  longDistance = "longDistance";

	public Carrier map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Carrier(resultSet.getString(CID), resultSet.getString(carrierNumber), resultSet.getString(species), resultSet.getString(transportType), resultSet.getString(TID), resultSet.getBoolean(longDistance));

	}
}
