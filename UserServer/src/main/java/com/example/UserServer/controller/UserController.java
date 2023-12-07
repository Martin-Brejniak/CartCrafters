package com.example.UserServer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserServer.entity.User;
import com.example.UserServer.service.UserDAO;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
private final UserDAO userDAO;
public String session_username = "blank";
public String session_password = "also blank";
	
	@Autowired
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@GetMapping("/get-all")
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
	
	@GetMapping("/get-id")
    public List<User> getUserByID(@RequestParam("userID") int userID) {
		return userDAO.getUserByID(userID);
    }
	
	@GetMapping("/get-username")
    public List<User> getUserByID(@RequestParam("username") String username) {
		System.out.println(userDAO.getUserByUsername(username));
		return userDAO.getUserByUsername(username);
    }
	
	@GetMapping("/get-sessionuser")
    public void getSessionUsername(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println("this line of code is reached!");
		System.out.println(username);
		session_username = username;
    }
	
	@GetMapping("/checkout-get-username")
    public String getUsernameCheckout() {
		System.out.println("checkout-get-username accessed: retunring " + session_username);
		return session_username;
    }
	
	@GetMapping("/authenticate")
    public boolean authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		return userDAO.authenticateUser(username, password);
    }
	
	@GetMapping("/token-check")
    public String getUserToken(@RequestParam("username") String username) {
		return userDAO.getUserToken(username);
    }
	
	@PostMapping
    public void createUser(@RequestBody User user) {
		userDAO.createUser(user);
    }
	
	@PutMapping
    public void updateUser(@RequestBody User user, @RequestParam("userID") int userID) {
		userDAO.updateUser(userID, user);
    }
	
	@PutMapping("/token-update")
    public void updateUserToken(@RequestParam String username, @RequestParam("token") String token) {
		userDAO.updateUserToken(username, token);
    }
	
	@DeleteMapping
    public void deleteUser(@RequestParam("userID") int userID) {
		userDAO.deleteUser(userID);
    }

}
