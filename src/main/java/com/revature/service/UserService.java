package com.revature.service;

import com.revature.exceptions.UserFailException;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(UserDao dao){
		this.dao = dao;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: implement
		User possibleUser = dao.getUserByUsername(loginRequestData.getUsername());
		if (possibleUser == null) {
			throw new UserFailException("User not found.");
		}
		if (!possibleUser.getPassword().equals(loginRequestData.getPassword())) {
			throw new UserFailException("Passwords do not match.");
		}

		return possibleUser;
	}

	public User register(User registerRequestData) {
		// TODO: implement

		String message = validateUser(registerRequestData);

		if (!message.isEmpty()) {
			throw new UserFailException(message);
		}

//		UsernamePasswordAuthentication newUserAuth = new UsernamePasswordAuthentication();
//		newUserAuth.setPassword(registerRequestData.getPassword());
//		newUserAuth.setUsername(registerRequestData.getUsername());

        return dao.createUser(registerRequestData);
	}

	private String validateUser(User registerRequestData) {
		String message = "";
		if (registerRequestData.getUsername().length() > 30) {
			message += "Username is too long\n";
		}
		if (usernameIsUsed(registerRequestData.getUsername())) {
			message += "Username is in Use\n";
		}
		if (registerRequestData.getPassword().length() > 30) {
			message += "Password is too long\n";
		}

		return message;

	}

	private boolean usernameIsUsed(String username) {
		User potentialDuplicate = dao.getUserByUsername(username);
        return potentialDuplicate.getUsername() == null;
    }
}
