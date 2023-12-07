package com.example.UserServer.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.UserServer.entity.User;

@Repository
public class UserDAO {
	
	public JdbcTemplate jdbcTemplate;

	@Autowired
	public UserDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private RowMapper<User> userRowMapper = (rs, rowNum) -> {
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
		return user;
	};
	
	private RowMapper<String> passwordRowMapper = (rs, rowNum) -> {
		String password = rs.getString("password");
		return password;
	};
	
	private RowMapper<String> tokenRowMapper = (rs, rowNum) -> {
		String userToken = rs.getString("userToken");
		return userToken;
	};
	
	/**
	* Gets all items from the items database.
	*
	* @return         all items.
	*/
	public List<User> getAllUsers() {
		String sql = "SELECT * FROM users";
		return jdbcTemplate.query(sql, userRowMapper);
	}
	
	/**
	* Gets user with a matching ID from the users database.
	*
	* @param  id   a number.
	* @return         user.
	*/
	public List<User> getUserByID(int id) {
		String sql = "SELECT * FROM users WHERE userID = " + id;
		return jdbcTemplate.query(sql, userRowMapper);
	}
		
	/**
	* Gets user with a matching username from the users database.
	*
	* @param  id   a username.
	* @return         user.
	*/
	public List<User> getUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username +"'";
		return jdbcTemplate.query(sql, userRowMapper);
	}	
	
	/**
	* Checks to see if the username and password the user typed in, matches with what's in the database.
	*
	* @param  username   a username.
	* @param  password   a password.
	* @return         true if username and password are correct. False if not.
	*/
	public boolean authenticateUser(String username, String password) {
		String sql = "SELECT password FROM users WHERE username = '" + username +"'";
		String passwordDB = null;
		boolean correct = false;
		
		passwordDB = jdbcTemplate.query(sql, passwordRowMapper).get(0);
		
		if (password.equals(passwordDB) ) {
			correct = true;
		}
		return correct;
	}
	
	public String getUserToken(String username) {
		String sql = "SELECT userToken FROM users WHERE username = '" + username +"'";
		return jdbcTemplate.query(sql, tokenRowMapper).get(0);
	}	
	
    /**
    * Add user to user database.
    *
    * @param  user   a user.
    */
	public void createUser(User user) {
		String sql = "INSERT INTO users(fname, lname, address, postal, city, country, province, username, password) VALUES('"+user.getfName()+"','"+user.getlName()+"','"+user.getAddress()+"','"+user.getPostal()+"','"+user.getCity()+"','"+user.getCountry()+"','"+user.getProvince()+"','"+user.getUsername()+"','"+user.getPassword()+"')";
		jdbcTemplate.update(sql);
	}

	/**
	* Updates user in user database.
	*
    * @param  id   a number.
	* @param  user   a user.
	*/
	public void updateUser(int id, User user) {
		//use prepared statements
		String sql = "UPDATE users SET fname = '"+user.getfName()+"', lname = '"+user.getlName()+"', address = '"+user.getAddress()+"', postal = '"+user.getPostal()+"', city = '"+user.getCity()+"', country = '"+user.getCountry()+"', province = '"+user.getProvince()+"', username = '"+user.getUsername()+"', password = '"+user.getPassword()+"' WHERE userID = "+id;
		jdbcTemplate.update(sql);
	}	
	
	public void updateUserToken(String username, String token) {
		//use prepared statements
		String sql = "UPDATE users SET userToken = '"+token+"' WHERE username = '"+username+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	* Deletes user in user database.
	*
    * @param  id   a number.
	*/
	public void deleteUser(int id) {
		String sql = "DELETE FROM users WHERE userID = "+ id;
		jdbcTemplate.update(sql);
	}
	

}
