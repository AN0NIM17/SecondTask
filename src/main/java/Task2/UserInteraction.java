package Task2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInteraction {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.closeConnection();
		return user;
	}

	public static boolean createUser(User user) throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		Statement myStatement = myConnection.createStatement();
		boolean myResultSet = myStatement.execute("INSERT INTO user (firstname, middlename, lastname) VALUES ('"
				+ user.getFirstname() + "', '" + user.getMiddlename() + "', '" + user.getLastname() + "');");
		
		try {
			myStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.closeConnection();
		return myResultSet;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.closeConnection();

		return myResultSet;
	}

}
