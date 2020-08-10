package Task2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbConnection {
	private static Logger logger = LogManager.getLogger(DbConnection.class);
	private static Connection myConnection = null;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String bdUrl = "jdbc:mysql://localhost:3306/user";
			String user = "root";
			String password = "123";

			myConnection = DriverManager.getConnection(bdUrl, user, password);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		}

		return myConnection;
	}

	public static void closeConnection() {
		try {
			if (myConnection != null)
				myConnection.close();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

}
