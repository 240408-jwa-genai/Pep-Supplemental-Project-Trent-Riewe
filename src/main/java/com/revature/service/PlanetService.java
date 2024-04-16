package com.revature.service;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.controller.UserController;
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
		return null;
	}

	public boolean deletePlanetById(int planetId) {
		// TODO Auto-generated method stub
		return false;
	}
}
