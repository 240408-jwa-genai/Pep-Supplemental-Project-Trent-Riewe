package com.revature.controller;

import com.revature.MainDriver;
import com.revature.exceptions.UserFailException;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;

import java.sql.SQLException;


public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public void authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: implement
		// TODO: handle logged in user
		User currentUser = userService.authenticate(loginRequestData);
		if (currentUser.getUsername().isEmpty()) {
			System.out.println("Invalid username/password combination");
			return;
		}
		System.out.printf("Welcome back %s!", currentUser.getUsername());
		MainDriver.loggedInUser = currentUser;


    }

	public void register(User registerRequestData) {

		try {
			User registeredUser = userService.register(registerRequestData);
			System.out.println("Thank you for registering " + registeredUser.getUsername() + "!");
			System.out.println("Please login using your username and password.");

		} catch (UserFailException e) {
			System.out.println(e.getMessage());

		}

	}

	public void logout() {
		// TODO: implement
		MainDriver.loggedInUser = new User();
		System.out.println("Thank you for using our app.");
		System.out.println("Have a nice day");
	}
	
	public boolean checkAuthorization(int userId) {	
		// TODO: implement
		return false;
	}

}
