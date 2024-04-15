package com.revature;

import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.exceptions.UserFailException;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;
import com.revature.service.UserService;

public class MainDriver {
    
    public static UserDao userDao = new UserDao();
    public static UserService userService = new UserService(userDao);
    public static UserController userController = new UserController(userService);

    public static User loggedInUser = null;

    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application
        try (Scanner in = new Scanner(System.in);) {

            boolean activeUser = true;
            while (activeUser) {
                
                if (loggedInUser != null) {
                    loggedInScreen();
                } else {
                   welcomeScreen(in);
                }
            }
        }
    }

    private static void loggedInScreen() {
    }

    private static void welcomeScreen(Scanner in) {
        System.out.println("Welcome to the Panetarium");
        System.out.println("Would you like to: 1: Login, or 2: Register");
        int command = in.nextInt();
        switch (command) {
            case 0:
                break;
            case 1:
                login(in);
                break;
            case 2:
                System.out.println("Register");
                register(in);
                break;
            default:
                System.out.println("Invalid Input: Choose 1 or 2");
        }
    }

    private static void login(Scanner in) {
        System.out.println("Please entry your username and password to login");
        UsernamePasswordAuthentication userAuth = new UsernamePasswordAuthentication();
        System.out.print("Username: ");
        userAuth.setUsername(in.nextLine());
        System.out.print("Password: ");
        String password = in.nextLine();

    }

    private static void register(Scanner in) {
        boolean registered = false;
        while (!registered) {
            System.out.println("Thank you for deciding to register with our app.");
            System.out.println("Please enter the username and password you would like to use");
            System.out.println("The username must be unique, and both username and password must " +
                    "be shorter than 30 characters");
            System.out.print("Username: ");
            String username = in.nextLine();

            System.out.print("Password: ");
            String password = in.nextLine();
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            try {
                loggedInUser = userController.register(newUser);
                registered = true;

            } catch (UserFailException e) {
                System.out.println(e);

            }
        }
    }

}
