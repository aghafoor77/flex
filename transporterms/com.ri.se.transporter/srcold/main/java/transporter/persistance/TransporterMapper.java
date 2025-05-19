package transporter.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TransporterMapper implements ResultSetMapper<Transporter> {

	private static final String  TID = "TID";
	private static final String  notes = "notes";
	private static final String  animalPeryear = "animalPeryear";
	private static final String  ownerId = "ownerId";
	private static final String  isOrganization = "isOrganization";

	public Transporter map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Transporter(resultSet.getString(TID), resultSet.getString(notes), resultSet.getString(animalPeryear), resultSet.getString(ownerId), resultSet.getBoolean(isOrganization));

	}
}
