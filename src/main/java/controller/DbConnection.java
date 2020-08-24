package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbConnection {
	private Logger logger = LogManager.getLogger(DbConnection.class);
	private Connection myConnection = null;

	private String dbUrl;
	private String user;
	private String password;

	public DbConnection(String dbUrl, String user, String password) {
		this.dbUrl = dbUrl;
		this.user = user;
		this.password = password;
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConnection = DriverManager.getConnection(dbUrl, user, password);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
			throw e;
		} catch (RuntimeException e) {
			logger.error(e.toString());
			throw e;
		}

		return myConnection;
	}

	public void closeConnection() throws SQLException {
		try {
			if (myConnection != null) {
				myConnection.close();
			}
		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
	}

}
