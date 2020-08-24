package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.User;

@WebServlet("/user")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getLogger(UserController.class);
	private final ObjectMapper mapper = new ObjectMapper();
	private final UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = null;
		String id = request.getParameter("id");
		try {
			user = userService.get(id);
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		}
		String userData = mapper.writeValueAsString(user);
		response.setContentType("application/json");
		response.getWriter().println(userData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html");
		Reader data = request.getReader();
		User user = mapper.readValue(data, User.class);
		try {
			printWriter.println(userService.create(user));
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Reader data = request.getReader();
		User user = new ObjectMapper().readValue(data, User.class);
		try {
			userService.update(id, user);
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		try {
			userService.delete(id);
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		}
	}
}
