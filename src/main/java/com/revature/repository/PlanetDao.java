package com.revature.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {

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

		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planetName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return makePlanet(rs);
			}

			return new Planet();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetById(int planetId) {

		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return makePlanet(rs);
			}

			return new Planet();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planet createPlanet(Planet p) {

		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "insert into planets (name, ownerId) values (?,?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getName());
			ps.setInt(2, p.getOwnerId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				p.setId(rs.getInt(1));

			}

			return p;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public boolean deletePlanetById(int planetId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			// create sql we will execute
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			int val = ps.executeUpdate();
			return val == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
		// get all planets
		List<Planet> planets = dao.getAllPlanets();
		System.out.println(planets);
		// get planet by name
		Planet getByName1 = dao.getPlanetByName("urath");
		System.out.println(getByName1);
		Planet getByName2 = dao.getPlanetByName("will return empty planet");
		System.out.println(getByName2);
		// get planet by id
		Planet getById1 = dao.getPlanetById(1);
		System.out.println(getById1);
		Planet getById2 = dao.getPlanetById(-1);
		System.out.println(getById2);
		// create planet
		Planet p = new Planet();
		p.setName("Grymalkin");
		p.setOwnerId(1);
		p = dao.createPlanet(p);
		System.out.println(p);
		// delete planet
		System.out.println(dao.deletePlanetById(2));

	}

}
