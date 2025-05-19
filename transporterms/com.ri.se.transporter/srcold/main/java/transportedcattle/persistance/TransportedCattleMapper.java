package transportedcattle.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TransportedCattleMapper implements ResultSetMapper<TransportedCattle> {

	private static final String  TCID = "TCID";
	private static final String  notes = "notes";
	private static final String  GHEID = "GHEID";
	private static final String  destination = "destination";
	private static final String  departDate = "departDate";
	private static final String  transportType = "transportType";
	private static final String  source = "source";
	private static final String  animalID = "animalID";
	private static final String  carriernumber = "carriernumber";

	public TransportedCattle map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new TransportedCattle(resultSet.getString(TCID), resultSet.getString(notes), resultSet.getString(GHEID), resultSet.getString(destination), resultSet.getTimestamp(departDate), resultSet.getString(transportType), resultSet.getString(source), resultSet.getString(animalID), resultSet.getString(carriernumber));

	}
}
