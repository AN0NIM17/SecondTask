package Task2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class UserInteraction {
	public static JSONObject getUser(String id) throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		Statement myStatement = myConnection.createStatement();
		ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM user WHERE `id` =" + id);
		myResultSet.next();
		JSONObject obj = new JSONObject();
		obj.put("firstname", myResultSet.getString("firstname"));
		obj.put("middlename", myResultSet.getString("middlename"));
		obj.put("lastname", myResultSet.getString("lastname"));
		try {
			myResultSet.close();
			myStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.closeConnection();
		return obj;
	}

	public static boolean createUser(String firstname, String middlename, String lastname) throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		Statement myStatement = myConnection.createStatement();
		boolean myResultSet = myStatement.execute("INSERT INTO user (firstname, middlename, lastname) VALUES ('"
				+ firstname + "', '" + middlename + "', '" + lastname + "');");
		try {
			myStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbConnection.closeConnection();
		return myResultSet;
	}

	public static boolean updateUser(String firstname, String middlename, String lastname, String id)
			throws SQLException {
		Connection myConnection = DbConnection.getConnection();
		Statement myStatement = myConnection.createStatement();
		boolean myResultSet = myStatement.execute("UPDATE user SET firstname = '" + firstname + "', middlename = '"
				+ middlename + "', lastname = '" + lastname + "' WHERE `id` = " + id + ";");
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
