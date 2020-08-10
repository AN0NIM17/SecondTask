package Task2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet implementation class SecondTask
 */
@WebServlet("/SecondTask")
public class SecondTask extends HttpServlet {
	private static Logger logger = LogManager.getLogger(SecondTask.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SecondTask() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = null;
		String id = request.getParameter("id");
		try {
			user = UserInteraction.getUser(id);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
		JSONObject jsonObject = new JSONObject(user);
		String myJson = jsonObject.toString();
		response.setContentType("application/json");
		PrintWriter printWriter = response.getWriter();
		printWriter.println(myJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		JSONObject jsonObject = new JSONObject(data);
		String firstname = jsonObject.getString("firstname");
		String middlename = jsonObject.getString("middlename");
		String lastname = jsonObject.getString("lastname");
		User user = new User(firstname, middlename, lastname);
		try {
			UserInteraction.createUser(user);
		} catch (SQLException e) {
			logger.error(e.toString());
		}

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		JSONObject jsonObject = new JSONObject(data);
		String firstname = jsonObject.getString("firstname");
		String middlename = jsonObject.getString("middlename");
		String lastname = jsonObject.getString("lastname");
		User user = new User(firstname, middlename, lastname);
		try {
			UserInteraction.updateUser(user, id);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		try {
			UserInteraction.deleteUser(id);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

}
