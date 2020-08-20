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
	private Logger logger = LogManager.getLogger(UserController.class);

	UserRepository userRepository = new UserRepository();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = null;
		String id = request.getParameter("id");
		try {
			user = userRepository.getUser(id);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
		String userData = new ObjectMapper().writeValueAsString(user);
		response.setContentType("application/json");
		PrintWriter printWriter = response.getWriter();
		printWriter.println(userData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html");
		Reader data = request.getReader();
		User user = new ObjectMapper().readValue(data, User.class);
		try {
			long id = userRepository.createUser(user);
			printWriter.println(id);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Reader data = request.getReader();
		User user = new ObjectMapper().readValue(data, User.class);
		try {
			userRepository.updateUser(user, id);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		try {
			userRepository.deleteUser(id);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
}
