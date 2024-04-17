package com.revature.service;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.exceptions.PlanetFailException;
import com.revature.exceptions.UserFailException;
import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets(int currentUserId) {
		// TODO Auto-generated method stub
		return dao.getAllPlanets().stream()
				.filter(planet -> planet.getOwnerId() == currentUserId)
				.collect(Collectors.toList());
	}

	public Planet getPlanetByName(int ownerId, String planetName) {

		Planet possiblePlanet = dao.getPlanetByName(planetName);
		return ownerId == possiblePlanet.getOwnerId() ? possiblePlanet : new Planet();
	}

	public Planet getPlanetById(int ownerId, int planetId) {

		Planet possiblePlanet = dao.getPlanetById(planetId);
		return ownerId == possiblePlanet.getOwnerId() ? possiblePlanet : new Planet();
	}

	public Planet createPlanet(int ownerId, Planet planet) {
		// TODO Auto-generated method stub
		String message = validatePlanet(ownerId, planet);
		if (!message.isEmpty()) {
			throw new PlanetFailException(message);
		}
		planet.setOwnerId(ownerId);
		return dao.createPlanet(planet);

	}

	public boolean deletePlanetById(int planetId) {
		// TODO Auto-generated method stub
		return dao.deletePlanetById(planetId);
	}

	private String validatePlanet(int ownerId, Planet planet) {
		String message = "";
		if (planet.getName().length() > 30) {
			message += "Planet name is too long \n";
		}
		if (planetNameNotUnique(ownerId, planet)) {
			message += "Planets must have unique names \n";
		}
		return message;
	}

	private boolean planetNameNotUnique(int ownerId, Planet planet) {
		return getPlanetByName(ownerId, planet.getName()).getName() != null;
	}

}
