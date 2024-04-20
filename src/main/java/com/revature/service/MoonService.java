package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;
	private PlanetService planetService;

	public MoonService(MoonDao dao, PlanetService planetService) {
		this.dao = dao;
		this.planetService = planetService;
	}

	public List<Moon> getAllMoons() {

		return dao.getAllMoons();
	}

	public Moon getMoonByName(int myPlanetId, String moonName) {
		Moon foundMoon = dao.getMoonByName(moonName);
		if (foundMoon.getMyPlanetId() != myPlanetId) {
			return new Moon();
		}
		return foundMoon;
	}

	public Moon getMoonById(int myPlanetId, int moonId) {
		Moon foundMoon = dao.getMoonById(moonId);
		if (foundMoon.getMyPlanetId() != myPlanetId) {
			return new Moon();
		}
		return foundMoon;
	}

	public Moon createMoon(Moon m) throws MoonFailException {
		String message = "";
		List<Moon> moons = dao.getAllMoons();
		Moon possibleDup = dao.getMoonByName(m.getName());
		if (m.getName().length() > 30) {
			message += "The Moon's name cannot be have more 30 characters.\n";
		}
		if (possibleDup.getName() != null) {
			message += "The moon's name must be unique.\n";
		}
		if(!message.isEmpty()) throw new MoonFailException(message);

		return dao.createMoon(m);
	}

	public boolean deleteMoonById(int moonId) {

		return dao.deleteMoonById(moonId);
	}

	public boolean deletePlanetsMoons(int myPlanetId) {

		return dao.deletePlanetsMoons(myPlanetId);
	}

	public List<Moon> getMoonsFromPlanet(int myPlanetId) {
		return dao.getMoonsFromPlanet(myPlanetId);
	}
}
