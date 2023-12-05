package com.example.UserServer.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	* If so, a session token is added to the database to keep the user logged in.
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
			String predefinedToken = "session_token";
            updateUserToken(username, predefinedToken);
            System.out.println("User authenticated!");
		}
		return correct;
	}
	
	/**
	 * Retrieves the user token to validate a user's session, assuming one exists. 
	 * If one doesn't exist, null is returned.
	 * 
	 * @param username			The user's username
	 * @return userToken 		The user's session token
	 */
    public String getUserToken(String username) {
        String sql = "SELECT userToken FROM users WHERE username = '" + username + "'";
        List<String> userTokens = jdbcTemplate.queryForList(sql, String.class);
        if (!userTokens.isEmpty()) {
        	System.out.println("User session token exists.");
            return userTokens.get(0);
        }
        return null;
    }
    
    /**
     * A token is added to the database.
     * 
     */
    public void updateUserToken(String username, String newToken) {
        String updateSql = "UPDATE users SET userToken = ? WHERE username = ?";
        jdbcTemplate.update(updateSql, newToken, username);
        System.out.println("User session token updated.");
    }
    
    /**
     * A token is removed from the database.
     * 
     */
    public void removeUserToken(String username) {
        String updateSql = "UPDATE users SET userToken = NULL, tokenExpiration = NULL WHERE username = ?";
        jdbcTemplate.update(updateSql, username);
        System.out.println("User session token removed.");
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