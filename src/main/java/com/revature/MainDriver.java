package com.revature;

import java.util.Scanner;

import com.revature.controller.MoonController;
import com.revature.controller.PlanetController;
import com.revature.controller.UserController;
import com.revature.exceptions.UserFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.MoonDao;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;
import com.revature.service.UserService;


public class MainDriver {
    
    public static UserDao userDao = new UserDao();
    public static MoonDao moonDao = new MoonDao();
    public static PlanetDao planetDao = new PlanetDao();


    public static UserService userService = new UserService(userDao);
    public static PlanetService planetService = new PlanetService(planetDao);
    public static MoonService moonService = new MoonService(moonDao, planetService);

    public static UserController userController = new UserController(userService);
    public static PlanetController planetController = new PlanetController(planetService, moonService);
    public static MoonController moonController = new MoonController(moonService, planetService);

    public static int loggedInUser = -1;
    public static boolean activeUser = true;

    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application
        try (Scanner in = new Scanner(System.in);) {


            while (activeUser) {
                
                if (loggedInUser < 0) {
                    welcomeScreen(in);
                } else {
                    loggedInScreen(in);
                }
            }
        }
    }

    private static void loggedInScreen(Scanner in) {
        printLoggedInOptions();
        String command = in.nextLine();
        switch (command) {
            case "1":
                viewPlanets(in);
                break;
            case "2":
                createPlanet(in);
                break;
            case "3":
                deletePlanet(in);
                break;
            case "4":
                viewMoons(in);
                break;
            case "5":
                createMoon(in);
                break;
            case "6":
                removeMoon(in);
                break;
            case "7":
                System.out.println("Logout");
                userController.logout();
                break;
            case "q":
                System.out.println("Thank you for using our program.");
                System.out.println("Have a nice day!");
                activeUser = !activeUser;
                break;
            default:
                System.out.println("Invalid Input: Choose 1, 2, or q");
        }

    }

    private static void removeMoon(Scanner in) {
        int planetId = -1, moonId =-1;
        String command = "";
        while (moonId <0) {
            if (planetId < 0) {
                System.out.println("Choose the planet whose moon you would like to delete");
                System.out.println("If you want to view the planets you own press 'v'.");
                System.out.println("Otherwise enter the id of the planet you would like to access.");
                command = in.nextLine();
                if (command.equals("v")) {
                    planetController.getAllPlanets(loggedInUser);

                } else if (command.equals("b")) {
                    return;
                } else {
                    planetId = Integer.valueOf(command);
                }
            }
            if (moonId< 0 && planetId >0) {
                System.out.println("If you want to view the moons associated with the planet press 'v'.");
                command = in.nextLine();
                if (command.equals("v")) {
                    moonController.getPlanetMoons(planetId);
                } else if (command.equals("b")) {
                    return;
                } else {
                    moonController.deleteMoon(planetId, Integer.parseInt(command));
                    return;
                }
            }
        }
    }

    private static void createMoon(Scanner in) {
        boolean notChosen = true;
        Moon potentialMoon = new Moon();
        int planetId = -1;
        String moonName = "";
        while(notChosen) {
            if (planetId < 0) {
                System.out.println("Please choose which of your planets the moon will be added to.");
                System.out.println("PLease enter the id of the planet you selected.");
                planetController.getAllPlanets(loggedInUser);
                String planetString = in.nextLine();
                if(planetString.matches("[0-9]+")) {
                    planetId = Integer.parseInt(planetString);
                }
            }
            if (moonName.isEmpty()) {
                System.out.println("Please enter the name of the moon:");
                moonName = in.nextLine();
            }
            notChosen = moonName.isEmpty() || planetId < 0;
        }
        potentialMoon.setMyPlanetId(planetId);
        potentialMoon.setName(moonName);
        moonController.createMoon(loggedInUser, potentialMoon);

    }

    private static void viewMoons(Scanner in) {
        System.out.println("View Moons");
        moonController.getAllMoons(loggedInUser);
        System.out.println("Press enter when ready to move on.");
        in.nextLine();

    }

    private static void viewPlanets(Scanner in) {
        System.out.println("View Planets");
        planetController.getAllPlanets(loggedInUser);
        System.out.println("Press enter when ready to move on.");
        in.nextLine();
    }

    private static void deletePlanet(Scanner in) {
        int planetId = -1;
        while (planetId < 0) {
            System.out.println("Warning deleting a planet will also delete all the moons associated with it");
            System.out.println("If you want to view the planets you own press 'v'.");
            System.out.println("Otherwise enter the id of the planet you would like to delete.");
            System.out.println("You can also press 'b' to go back");
            String command = in.nextLine();
            if (command.equals("v")) {
                planetController.getAllPlanets(loggedInUser);

            } else if (command.equals("b")) {
                return;
            } else {
                planetId = Integer.valueOf(command);
            }

        }
        planetController.deletePlanet(loggedInUser, planetId);

    }

    private static void createPlanet(Scanner in) {
        System.out.println("Add Planet");
        System.out.println("Please enter the planet's name");
        Planet potentialPlanet = new Planet();
        potentialPlanet.setName(in.nextLine());
        planetController.createPlanet(loggedInUser, potentialPlanet);
    }

    private static void printLoggedInOptions() {
        System.out.println("What would you like to do today?");
        System.out.println("1: View Planets");
        System.out.println("2: Add a new Planet");
        System.out.println("3: Remove a Planet");
        System.out.println("4: View Moons");
        System.out.println("5: Add a new Moon");
        System.out.println("6: Remove a Moon");
        System.out.println("7: Logout");
    }

    private static void welcomeScreen(Scanner in) {
        System.out.println("Welcome to the Panetarium");
        System.out.println("Would you like to: 1: Login, or 2: Register");
        System.out.println("You may also press 'q' to quit");
        String command = in.nextLine();
        switch (command) {
            case "1":
                login(in);
                break;
            case "2":
                register(in);
                break;
            case "q":
                System.out.println("Thank you for using our program.");
                System.out.println("Have a nice day!");
                activeUser = !activeUser;
                break;
            default:
                System.out.println("Invalid Input: Choose 1, 2, or q");
        }
    }

    private static void login(Scanner in) {
        System.out.println("Please enter your username and password to login");
        UsernamePasswordAuthentication userAuth = new UsernamePasswordAuthentication();
        System.out.print("Username: ");
        String username = in.nextLine();
        userAuth.setUsername(username);

        System.out.print("Password: ");
        String password = in.nextLine();
        userAuth.setPassword(password);
        userController.authenticate(userAuth);

    }

    private static void register(Scanner in) {
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

        // TODO add register implementation
        userController.register(newUser);

    }

}
