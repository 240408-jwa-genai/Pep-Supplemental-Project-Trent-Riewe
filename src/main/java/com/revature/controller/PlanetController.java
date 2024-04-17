package com.revature.controller;

import com.revature.exceptions.UserFailException;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.service.PlanetService;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetController {
	
	private PlanetService planetService;

	public PlanetController(PlanetService planetService){
		this.planetService = planetService;
	}

	public void getAllPlanets(int currentUserId) {
		// TODO: implement
		List<Planet> planets = planetService.getAllPlanets(currentUserId);
		for (Planet planet : planets) {
			System.out.printf("id: %d, name: %s \n", planet.getId(), planet.getName());
		}
	}

	public void getPlanetByName(int currentUserId, String name) {
		// TODO: implement
		Planet planet = planetService.getPlanetByName(currentUserId, name);
		System.out.println(planet);
	}

	public void getPlanetByID(int currentUserId, int id) {
		// TODO: implement
		Planet planet = planetService.getPlanetById(currentUserId, id);
		System.out.println(planet);
	}

	public void createPlanet(int currentUserId, Planet planet) {
		// TODO: implement
		try {
			Planet newPlanet = planetService.createPlanet(currentUserId, planet);
			System.out.println("Thank you for adding " + newPlanet.getName() + " to our database!");

		} catch (UserFailException e) {
			System.out.println(e.getMessage());

		}
	}

	public void deletePlanet(int currentUserId, int id) {
		// TODO: implement
		Planet planetToDelete = planetService.getPlanetById(currentUserId, id);
		if (planetToDelete.getName() == null) {
			System.out.println("You cannot delete planets that do not belong to you or do not exist");
			return;
		}
		if (planetService.deletePlanetById(id)) {
			System.out.println("Planet with id: " + id + " successfully deleted");
		}
	}
}
