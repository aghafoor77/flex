/*
 * package ri.se.animal;
 * 
 * import java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.SQLException;
 * 
 * public class Test {
 * 
 * public static void main(String[] args) { String url =
 * "jdbc:mysql://localhost:3306/formas?createDatabaseIfNotExist=true&enabledTLSProtocols=TLSv1.2"
 * ;//"jdbc:mysql://localhost:3306/javabase"; String username = "root"; String
 * password = "Rise1234";
 * 
 * System.out.println("Connecting database...");
 * 
 * try (Connection connection = DriverManager.getConnection(url, username,
 * password)) { System.out.println("Database connected!"); } catch (SQLException
 * e) { throw new IllegalStateException("Cannot connect the database!", e); }
 * 
 * }
 * 
 * }
 */