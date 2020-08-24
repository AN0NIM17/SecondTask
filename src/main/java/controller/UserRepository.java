package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRepository {
	private String dbUrl = "jdbc:mysql://localhost:3306/user";
	private String user = "root";
	private String password = "123";

	private final String SQL_SELECT = "SELECT * FROM user WHERE `id` = ?;";
	private final String SQL_INSERT = "INSERT INTO user (firstname, middlename, lastname) VALUES(?, ?, ?);";
	private final String SQL_UPDATE = "UPDATE user SET firstname = ?, middlename = ?, lastname = ? WHERE `id` = ?;";
	private final String SQL_DELETE = "DELETE FROM user WHERE `id` = ?;";

	private DbConnection dbConnection = new DbConnection(dbUrl, user, password);

	public User getUser(String id) throws SQLException, ClassNotFoundException {
		Connection myConnection = dbConnection.getConnection();
		try (PreparedStatement myStatement = myConnection.prepareStatement(SQL_SELECT)) {
			myStatement.setString(1, id);
			try (ResultSet myResultSet = myStatement.executeQuery();) {
				myResultSet.next();
				dbConnection.closeConnection();
				return new User(myResultSet.getString("firstname"), myResultSet.getString("middlename"),
						myResultSet.getString("lastname"));
			}
		}
	}

	public long createUser(User user) throws SQLException, ClassNotFoundException {
		Connection myConnection = dbConnection.getConnection();

		try (PreparedStatement myStatement = myConnection.prepareStatement(SQL_INSERT,
				Statement.RETURN_GENERATED_KEYS)) {
			myStatement.setString(1, user.getFirstname());
			myStatement.setString(2, user.getMiddlename());
			myStatement.setString(3, user.getLastname());
			myStatement.execute();
			try (ResultSet myResultSet = myStatement.getGeneratedKeys()) {
				myResultSet.next();
				dbConnection.closeConnection();
				return myResultSet.getLong(1);
			}
		}
	}

	public void updateUser(User user, String id) throws SQLException, ClassNotFoundException {
		Connection myConnection = dbConnection.getConnection();
		try (PreparedStatement myStatement = myConnection.prepareStatement(SQL_UPDATE)) {
			myStatement.setString(1, user.getFirstname());
			myStatement.setString(2, user.getMiddlename());
			myStatement.setString(3, user.getLastname());
			myStatement.setString(4, id);
			myStatement.execute();
		}
		dbConnection.closeConnection();
	}

	public void deleteUser(String id) throws SQLException, ClassNotFoundException {
		Connection myConnection = dbConnection.getConnection();
		try (PreparedStatement myStatement = myConnection.prepareStatement(SQL_DELETE)) {
			myStatement.setString(1, id);
			myStatement.execute();
		}
		dbConnection.closeConnection();
	}

}
