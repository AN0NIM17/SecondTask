package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbConnection {
	private static Logger logger = LogManager.getLogger(DbConnection.class);
	private static Connection myConnection = null;
	
	private static String dbUrl = "jdbc:mysql://localhost:3306/user";
	private static String user = "root";
	private static String password = "123";
	
	public DbConnection(String dbUrl, String user, String password) {
		DbConnection.dbUrl = dbUrl;
		DbConnection.user = user;
		DbConnection.password = password;
	}

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection(dbUrl, user, password);
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
