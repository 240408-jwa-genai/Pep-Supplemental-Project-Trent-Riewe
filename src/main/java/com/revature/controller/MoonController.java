package com.revature.controller;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;

import java.util.ArrayList;
import java.util.List;

public class MoonController {
	
	private MoonService moonService;
	private PlanetService planetService;

	public MoonController(MoonService moonService, PlanetService planetService) {
		this.moonService = moonService;
        this.planetService = planetService;
    }

	public void getAllMoons(int currentUserId) {
		List<Planet> userPlanets = planetService.getAllPlanets(currentUserId);
		if (userPlanets.isEmpty()) {
			System.out.println("You have no Planets and Moons please add some");
			return;
		}
		List<Moon> moons = new ArrayList<>();
		userPlanets.forEach(planet -> moons.addAll(moonService.getMoonsFromPlanet(planet.getId())));
		if(moons.isEmpty()) {
			System.out.println("Your Planets have no moons please add some");
		} else {
			for (Moon moon : moons) {
				System.out.printf("id: %d, name: %s, planetId: %d\n",
						moon.getId(), moon.getName(), moon.getMyPlanetId());
			}
		}

	}

	public void getMoonByName(int planetId, String name) {
		Moon moon = moonService.getMoonByName(planetId, name);
		if (moon.getName() == null) {
			System.out.println("Invalid planet and moon name combo.");
		} else {
			System.out.println("Here is the found moon with name: " + name);
			System.out.println(moon);
		}
	}

	public void getMoonById(int planetId, int id) {
		Moon moon = moonService.getMoonById(planetId, id);
		if (moon.getName() == null) {
			System.out.println("Invalid planet and moon id combo.");
		} else {
			System.out.println("Here is the found moon with id: " + id);
			System.out.println(moon);
		}
	}

	public void createMoon(int currentUserId, Moon moon) {
		try {
			moon = moonService.createMoon(moon);
			System.out.println("Thank you for adding the moon: " + moon);
		} catch (MoonFailException e) {
			System.out.println("Could not create the moon");
			System.out.println(e.getMessage());
		}

	}

	public void deleteMoon(int planetId, int id) {
		Moon moonToDelete = moonService.getMoonById(planetId, id);
		if (moonToDelete.getName() == null) {
			System.out.println("You cannot delete planets that do not belong to you or do not exist");
			return;
		}
		if (moonService.deleteMoonById(id)) {
			System.out.println("Planet with id: " + id + " successfully deleted");
		} else {
			System.out.println("Moon deletion unsuccessful");
		}
	}
	
	public void getPlanetMoons(int myPlanetId) {
		List<Moon> moons = moonService.getMoonsFromPlanet(myPlanetId);
		if(moons.isEmpty()) {
			System.out.println("The selected Planet has no moons please add some");
		} else {
			for (Moon moon : moons) {
				System.out.printf("id: %d, name: %s, planetId: %d\n",
						moon.getId(), moon.getName(), moon.getMyPlanetId());
			}
		}
	}
}
