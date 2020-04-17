package exa_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTester {
	private Connection connection;

	public DBTester() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
	}

	public void connect(String dbName) throws ClassNotFoundException, SQLException {
		String url = "jdbc:postgresql://localhost/" + dbName;
		String username = "postgres";
		String password = "postgres";
		connection = DriverManager.getConnection(url, username, password);
	}

	public void disconnect() throws SQLException {
		if (this.connection != null) {
			this.connection.close();
		}
	}

}
