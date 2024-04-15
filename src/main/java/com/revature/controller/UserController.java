package com.revature.controller;

import com.revature.exceptions.UserFailException;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;


public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: implement
		try {
			User currentUser = userService.authenticate(loginRequestData);
			return currentUser;
		} catch (UserFailException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public User register(User registerRequestData) {
		// TODO: implement
//		throw new UserFailException("Testing try catch");
		try {
			User registeredUser = userService.register(registerRequestData);
			return registeredUser;
		} catch (UserFailException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public void logout() {
		// TODO: implement
	}
	
	public boolean checkAuthorization(int userId) {	
		// TODO: implement
		return false;
	}

}
