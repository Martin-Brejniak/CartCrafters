package com.cartcrafters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	
    /**
    * Gets all users from the users database.
    *
    * @return         all users.
    */
	public List<User> readAll() {
		String sql = "SELECT fname, lname, address, postal, city, country, province, username, userID FROM users";
		List<User> users = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.connect();
		Statement stmt = conn.createStatement();
				
		ResultSet rs = stmt.executeQuery(sql)){
			while (rs.next()) {
				User user = new User();
				user.setfName(rs.getString("fName"));
				user.setlName(rs.getString("lName"));
				user.setAddress(rs.getString("address"));
				user.setPostal(rs.getString("postal"));
				user.setCity(rs.getString("city"));
				user.setCountry(rs.getString("country"));
				user.setProvince(rs.getString("province"));
				user.setUsername(rs.getString("username"));
				user.setUserID(rs.getInt("userID"));
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
	
    /**
    * Add user to user database.
    *
    * @param  user   a user.
    */
	public void create(User user) {
		String sql = "INSERT INTO users(fname, lname, address, postal, city, country, province, username, password) VALUES(?,?,?,?,?,?,?,?,?)";
		try (Connection conn = DatabaseConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getfName());
			pstmt.setString(2, user.getlName());
			pstmt.setString(3, user.getAddress());
			pstmt.setString(4, user.getPostal());
			pstmt.setString(5, user.getCity());
			pstmt.setString(6, user.getCountry());
			pstmt.setString(7, user.getProvince());
			pstmt.setString(8, user.getUsername());
			pstmt.setString(9, user.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public User readID(int id) {
		String sql = "SELECT fname, lname, address, postal, city, country, province, username FROM users WHERE userID = ?";
		User user = null;
		try (Connection conn = DatabaseConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setInt(1, id);
			// Execute the query and get the result set
			try (ResultSet rs = pstmt.executeQuery()) {
				// Check if a result was returned
				if (rs.next()) {
					user = new User();
					// Set the properties of the student object
					user.setfName(rs.getString("fName"));
					user.setlName(rs.getString("lName"));
					user.setAddress(rs.getString("address"));
					user.setPostal(rs.getString("postal"));
					user.setCity(rs.getString("city"));
					user.setCountry(rs.getString("country"));
					user.setProvince(rs.getString("province"));
					user.setUsername(rs.getString("username"));
					user.setUserID(id);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	public User readName(String username) {
		String sql = "SELECT fname, lname, address, postal, city, country, province, userID FROM users WHERE username = ?";
		User user = null;
		try (Connection conn = DatabaseConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setString(1, username);
			// Execute the query and get the result set
			try (ResultSet rs = pstmt.executeQuery()) {
				// Check if a result was returned
				if (rs.next()) {
					user = new User();
					// Set the properties of the student object
					user.setfName(rs.getString("fName"));
					user.setlName(rs.getString("lName"));
					user.setAddress(rs.getString("address"));
					user.setPostal(rs.getString("postal"));
					user.setCity(rs.getString("city"));
					user.setCountry(rs.getString("country"));
					user.setProvince(rs.getString("province"));
					user.setUsername(username);
					user.setUserID(rs.getInt("userID"));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
		
	public void update(int id, User user) {
		//use prepared statements
		String sql = "UPDATE users SET fname = ?, lname = ?, address = ?, postal = ?, city = ?, country = ?, province = ?, username = ?, password = ? WHERE userID =?";
		try (Connection conn = DatabaseConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameters
			pstmt.setString(1, user.getfName());
			pstmt.setString(2, user.getlName());
			pstmt.setString(3, user.getAddress());
			pstmt.setString(4, user.getPostal());
			pstmt.setString(5, user.getCity());
			pstmt.setString(6, user.getCountry());
			pstmt.setString(7, user.getProvince());
			pstmt.setString(8, user.getUsername());
			pstmt.setString(9, user.getPassword());
			pstmt.setInt(10, id);
			// Update the student record
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
		
//	public void delete(int id) {
//		String sql = "DELETE FROM students WHERE id = ?";
//		try (Connection conn = DatabaseConnection.connect();
//		PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			// Set the corresponding parameter
//			pstmt.setInt(1, id);
//			// Delete the student record
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//	}

}
