package com.user.api.controller;

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
import com.user.api.dto.UserDto;
import com.user.api.transformer.UserDtoTransformer;
import com.user.db.entity.User;
import com.user.service.UserService;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getLogger(UserController.class);
	private final ObjectMapper mapper = new ObjectMapper();
	private final UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		String id = request.getParameter("id");
		try {
			User user = userService.get(id);
			response.setStatus(200);
			String userData = mapper.writeValueAsString(user);
			response.setContentType("application/json");
			response.getWriter().println(userData);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage());
			response.setStatus(404);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		response.setContentType("application/json");
		Reader dataReader = request.getReader();
		User user = UserDtoTransformer.transform(mapper.readValue(dataReader, UserDto.class));
		try {
			printWriter.println(userService.create(user));
			response.setStatus(201);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage());
			response.setStatus(404);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		String id = request.getParameter("id");
		Reader data = request.getReader();
		UserDto userDto = mapper.readValue(data, UserDto.class);
		User user = UserDtoTransformer.transform(userDto);
		try {
			userService.update(id, user);
			response.setStatus(200);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage());
			response.setStatus(404);
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		String id = request.getParameter("id");
		try {
			userService.delete(id);
			response.setStatus(200);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage());
			response.setStatus(404);
		}
	}
}
