package com.revature.repository;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

import javax.swing.text.Utilities;
import java.sql.*;

public class UserDao {
    
    public User getUserByUsername(String username) {

        try (Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from users where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            User possibleUser = new User();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                possibleUser.setId(rs.getInt("id"));
                possibleUser.setUsername(rs.getString("username"));
                possibleUser.setPassword(rs.getString("password"));
            }
            return possibleUser;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User createUser(User registerRequest) {

        try (Connection connection = ConnectionUtil.createConnection()){
            // create sql we will execute
            String query = "insert into users (username, password) values(?,?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                registerRequest.setId(rs.getInt(1));
            }
            return registerRequest;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();
        User retrievedUser = dao.getUserByUsername("myUser");
        System.out.println(retrievedUser);
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("newPass");
        newUser = dao.createUser(newUser);
        System.out.println(newUser);
    }

}
