package com.user.db.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.user.db.entity.User;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRepository {
	private final String dbURL = "jdbc:mysql://localhost:3306/user";
	private final String username = "root";
	private final String password = "123";

	private final String SQL_SELECT = "SELECT * FROM user WHERE `id` = ?;";
	private final String SQL_INSERT = "INSERT INTO user (firstname, middlename, lastname) VALUES(?, ?, ?);";
	private final String SQL_UPDATE = "UPDATE user SET firstname = ?, middlename = ?, lastname = ? WHERE `id` = ?;";
	private final String SQL_DELETE = "DELETE FROM user WHERE `id` = ?;";

	private Connection dbConnection;

	public UserRepository() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection(dbURL, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e.getMessage());
		}
	}

	public User get(String id) throws SQLException, ClassNotFoundException {
		try (PreparedStatement myStatement = dbConnection.prepareStatement(SQL_SELECT)) {
			myStatement.setString(1, id);
			try (ResultSet myResultSet = myStatement.executeQuery()) {
				myResultSet.next();
				return new User(myResultSet.getString("firstname"), myResultSet.getString("middlename"),
				        myResultSet.getString("lastname"));
			}
		}
	}

	public User create(User user) throws SQLException, ClassNotFoundException {
		try (PreparedStatement myStatement = dbConnection.prepareStatement(SQL_INSERT,
		        Statement.RETURN_GENERATED_KEYS)) {
			myStatement.setString(1, user.getFirstname());
			myStatement.setString(2, user.getMiddlename());
			myStatement.setString(3, user.getLastname());
			myStatement.execute();
			try (ResultSet myResultSet = myStatement.executeQuery()) {
				myResultSet.next();
				return new User(myResultSet.getString("firstname"), myResultSet.getString("middlename"),
				        myResultSet.getString("lastname"));
			}
		}
	}

	public void update(User user, String id) throws SQLException, ClassNotFoundException {
		try (PreparedStatement myStatement = dbConnection.prepareStatement(SQL_UPDATE)) {
			myStatement.setString(1, user.getFirstname());
			myStatement.setString(2, user.getMiddlename());
			myStatement.setString(3, user.getLastname());
			myStatement.setString(4, id);
			myStatement.execute();
		}
	}

	public void delete(String id) throws SQLException, ClassNotFoundException {
		try (PreparedStatement myStatement = dbConnection.prepareStatement(SQL_DELETE)) {
			myStatement.setString(1, id);
			myStatement.execute();
		}
	}
}
