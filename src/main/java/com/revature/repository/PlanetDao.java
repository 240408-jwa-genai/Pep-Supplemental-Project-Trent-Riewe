package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets";
			PreparedStatement ps = connection.prepareStatement(sql);
			List<Planet> planets = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {

				planets.add(makePlanet(rs));
			}

			return planets;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetByName(String planetName) {
		// TODO: implement


		return null;
	}

	public Planet getPlanetById(int planetId) {
		// TODO: implement
		return null;
	}

	public Planet createPlanet(Planet p) {
		// TODO: implement
		return null;
	}

	public boolean deletePlanetById(int planetId) {
		// TODO: implement
		return false;
	}

	private Planet makePlanet(ResultSet rs) throws SQLException {
		Planet planet = new Planet();
		planet.setId(rs.getInt("id"));
		planet.setName(rs.getString("name"));
		planet.setOwnerId(rs.getInt("ownerId"));
		return planet;
	}

	public static void main(String[] args) {
		PlanetDao dao = new PlanetDao();
		List<Planet> planets = dao.getAllPlanets();
		System.out.println(planets);
	}

}
