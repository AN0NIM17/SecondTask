package controller;

import java.sql.SQLException;

import entity.User;

public class UserService {

	private UserRepository userRepository = new UserRepository();

	public User get(String id) throws SQLException, ClassNotFoundException {
		return userRepository.getUser(id);
	}
	
	public Long create(User user) throws SQLException, ClassNotFoundException {
		return userRepository.createUser(user);
	}
	
	public void update(String id, User user) throws SQLException, ClassNotFoundException {
		userRepository.updateUser(user, id);
	}
	
	public void delete(String id) throws SQLException, ClassNotFoundException {
		userRepository.deleteUser(id);
	}
}
