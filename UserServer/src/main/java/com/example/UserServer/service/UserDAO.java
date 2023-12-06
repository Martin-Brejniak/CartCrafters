package com.example.UserServer.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
	public String authenticateUser(String username, String password) {
	    String sql = "SELECT password FROM users WHERE LOWER(username) = LOWER(?)";
	    
	    try {
	        String passwordDB = jdbcTemplate.queryForObject(sql, String.class, username);
	        
	        if (passwordDB != null && passwordDB.trim().equals(password.trim())) {
	            return TokenService.generateToken(username); // Generate token for valid user
	        }

	    } catch (EmptyResultDataAccessException e) {
	        // No user with the given username found
	    } catch (DataAccessException e) {
	        // Handle other database-related exceptions
	    }
	    
	    return null; // Return null or appropriate response for invalid authentication
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
