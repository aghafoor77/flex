package analytics.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AnalyticsMapper implements ResultSetMapper<Analytics> {

	private static final String  AID = "AID";
	private static final String  ADG = "ADG";

	public Analytics map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Analytics(resultSet.getString(AID), resultSet.getString(ADG));

	}
}
