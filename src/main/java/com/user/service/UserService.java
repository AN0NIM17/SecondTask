package com.user.service;

import java.sql.SQLException;

import com.user.db.entity.User;
import com.user.db.repository.UserRepository;

public class UserService {
	private UserRepository userRepository = new UserRepository();

	public User get(String id) throws SQLException, ClassNotFoundException {
		return userRepository.get(id);
	}
	
	public User create(User user) throws SQLException, ClassNotFoundException {
		return userRepository.create(user);
	}
	
	public void update(String id, User user) throws SQLException, ClassNotFoundException {
		userRepository.update(user, id);
	}
	
	public void delete(String id) throws SQLException, ClassNotFoundException {
		userRepository.delete(id);
	}
}
