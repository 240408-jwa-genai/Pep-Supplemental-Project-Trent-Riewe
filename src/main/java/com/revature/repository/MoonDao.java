package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM moons";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Moon> moons = new ArrayList<>();
			while(rs.next()) {
				moons.add(mapMoon(rs));

			}
			return moons;
		} catch (SQLException e) {
            e.printStackTrace();
			return null;
        }
	}

	public List<Moon> getAllCurrentUsersMoons(int currentUserId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT m.id, m.name, m.myPlanetId FROM moons m" +
					" JOIN planets p ON p.id = m.myPlanetId" +
					" WHERE p.ownerId = ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, currentUserId);
			ResultSet rs = ps.executeQuery();
			List<Moon> moons = new ArrayList<>();
			while(rs.next()) {
				moons.add(mapMoon(rs));

			}
			return moons;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Moon getMoonByName(String moonName) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from moons where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, moonName);
			ResultSet rs = ps.executeQuery();
			Moon moon = new Moon();
			if(rs.next()) {
				moon = mapMoon(rs);

			}
			return moon;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Moon getMoonById(int moonId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			ResultSet rs = ps.executeQuery();
			Moon moon = new Moon();
			if(rs.next()) {
				moon = mapMoon(rs);

			}
			return moon;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Moon createMoon(Moon m) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "insert into moons (name, myPlanetId) values (?,?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getName());
			ps.setInt(2, m.getMyPlanetId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				m.setId(rs.getInt(1));

			}

			return m;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteMoonById(int moonId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			// create sql we will execute
			String sql = "delete from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			int val = ps.executeUpdate();
			return val == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public boolean deletePlanetsMoons(int myPlanetId) {
		try (Connection connection = ConnectionUtil.createConnection()){
			// create sql we will execute
			String sql = "delete from moons where myPlanetId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, myPlanetId);
			int val = ps.executeUpdate();
			return val == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM moons" +
					" WHERE myPlanetId = ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			List<Moon> moons = new ArrayList<>();
			while(rs.next()) {
				moons.add(mapMoon(rs));

			}
			return moons;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Moon mapMoon(ResultSet rs) throws SQLException {
		Moon newMoon = new Moon();
		newMoon.setId(rs.getInt(1));
		newMoon.setName(rs.getString(2));
		newMoon.setMyPlanetId(rs.getInt(3));
		return newMoon;
	}

	public static void main(String[] args) {
		MoonDao dao = new MoonDao();
		//get all
		List<Moon> moons = dao.getAllMoons();
		System.out.println(moons);
		moons = dao.getAllCurrentUsersMoons(1);
		System.out.println(moons);
		moons = dao.getMoonsFromPlanet(2);
		System.out.println(moons);

		//get by Name
		Moon moon = dao.getMoonByName("Ceres");
		System.out.println(moon);
		//getById
		moon = dao.getMoonById(1);
		System.out.println(moon);

		Moon toBeMade = new Moon();
		toBeMade.setName("Ganymede");
		toBeMade.setMyPlanetId(5);
		moon = dao.createMoon(toBeMade);
		System.out.println(moon);

		System.out.println(dao.deleteMoonById(3));

	}

}
