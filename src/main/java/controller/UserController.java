package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet implementation class SecondTask
 */
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static Logger logger = LogManager.getLogger(UserController.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
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
		String userData = new ObjectMapper().writeValueAsString(user);
		response.setContentType("application/json");
		PrintWriter printWriter = response.getWriter();
		printWriter.println(userData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html");
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		User user = new ObjectMapper().readValue(data, User.class);


		try {
			long id = UserInteraction.createUser(user);
			printWriter.println(id);

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
		User user = new ObjectMapper().readValue(data, User.class);
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
