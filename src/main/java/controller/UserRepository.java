package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRepository {
	private Logger logger = LogManager.getLogger(UserRepository.class);
	private String dbUrl = "jdbc:mysql://localhost:3306/user";
	private String user = "root";
	private String password = "123";

	private final String SQL_SELECT = "SELECT * FROM user WHERE `id` = ?;";
	private final String SQL_INSERT = "INSERT INTO user (firstname, middlename, lastname) VALUES(?, ?, ?);";
	private final String SQL_UPDATE = "UPDATE user SET firstname = ?, middlename = ?, lastname = ? WHERE `id` = ?;";
	private final String SQL_DELETE = "DELETE FROM user WHERE `id` = ?;";

	DbConnection dbConnection = new DbConnection(dbUrl, user, password);

	public User getUser(String id) throws SQLException {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myResultSet = null;
		try {
			myConnection = dbConnection.getConnection();
			myStatement = myConnection.prepareStatement(SQL_SELECT);
			myStatement.setString(1, id);
			myResultSet = myStatement.executeQuery();
			myResultSet.next();

			User user = new User(myResultSet.getString("firstname"), myResultSet.getString("middlename"),
					myResultSet.getString("lastname"));
			return user;
		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		} finally {
			if (myResultSet != null) {
				myResultSet.close();
			}
			if (myStatement != null) {
				myStatement.close();
			}
			dbConnection.closeConnection();
		}

	}

	public long createUser(User user) throws SQLException {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myResultSet = null;
		try {
			myConnection = dbConnection.getConnection();

			myStatement = myConnection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			myStatement.setString(1, user.getFirstname());
			myStatement.setString(2, user.getMiddlename());
			myStatement.setString(3, user.getLastname());
			myStatement.execute();
			myResultSet = myStatement.getGeneratedKeys();
			myResultSet.next();
			long id = myResultSet.getLong(1);
			return id;
		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		} finally {
			if (myResultSet != null) {
				myResultSet.close();
			}
			if (myStatement != null) {
				myStatement.close();
			}
			dbConnection.closeConnection();
		}
	}

	public void updateUser(User user, String id) throws SQLException {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		try {
			myConnection = dbConnection.getConnection();
			myStatement = myConnection.prepareStatement(SQL_UPDATE);
			myStatement.setString(1, user.getFirstname());
			myStatement.setString(2, user.getMiddlename());
			myStatement.setString(3, user.getLastname());
			myStatement.setString(4, id);
			myStatement.execute();
		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		} finally {
			if (myStatement != null) {
				myStatement.close();
			}
			dbConnection.closeConnection();
		}
	}

	public void deleteUser(String id) throws SQLException {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		try {
			myConnection = dbConnection.getConnection();
			myStatement = myConnection.prepareStatement(SQL_DELETE);
			myStatement.setString(1, id);
			myStatement.execute();
		} catch (SQLException e) {
			logger.error(e.toString());
			throw e;
		} finally {
			if (myStatement != null) {
				myStatement.close();
			}
			dbConnection.closeConnection();
		}
	}

}
