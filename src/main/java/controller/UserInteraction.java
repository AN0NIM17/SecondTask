package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.User;

public class UserInteraction {
	private static Logger logger = LogManager.getLogger(UserInteraction.class);
	public static User getUser(String id) throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		Statement myStatement = myConnection.createStatement();
		ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM user WHERE `id` =" + id);
		myResultSet.next();
		
		User user = new User(myResultSet.getString("firstname"), myResultSet.getString("middlename"), myResultSet.getString("lastname"));
		try {
			myResultSet.close();
			myStatement.close();

		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		DbConnection.closeConnection();
		
		return user;
	}

	public static long createUser(User user) throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		String SQL_INSERT = "INSERT INTO user (firstname, middlename, lastname) VALUES(?, ?, ?);";
		PreparedStatement myStatement = myConnection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
		myStatement.setString(1, user.getFirstname());
		myStatement.setString(2, user.getMiddlename());
		myStatement.setString(3, user.getLastname());
		myStatement.execute();
		ResultSet myResultSet = myStatement.getGeneratedKeys();
		myResultSet.next();
		long id = myResultSet.getLong(1);		
		try {
			myStatement.close();

		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		DbConnection.closeConnection();
		
		return id;
	}

	public static boolean updateUser(User user, String id)
			throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		Statement myStatement = myConnection.createStatement();
		boolean myResultSet = myStatement.execute("UPDATE user SET firstname = '" + user.getFirstname() + "', middlename = '"
				+ user.getMiddlename() + "', lastname = '" + user.getLastname() + "' WHERE `id` = " + id + ";");
		try {
			myStatement.close();

		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		DbConnection.closeConnection();

		return myResultSet;
	}

	public static boolean deleteUser(String id) throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		Statement myStatement = myConnection.createStatement();
		boolean myResultSet = myStatement.execute("DELETE FROM user WHERE `id` = " + id);
		try {
			myStatement.close();

		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		}
		DbConnection.closeConnection();

		return myResultSet;
	}

}
