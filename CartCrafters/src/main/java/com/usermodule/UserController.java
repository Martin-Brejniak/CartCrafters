package com.usermodule;

import java.util.List;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserController {
	
	private UserDAO userDAO = new UserDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUser() {
		return userDAO.readAll();
	}
	
//	@GET
//	@Path("/{authToken}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public User getUserByID(@PathParam("authToken") int id) {
//		return userDAO.readID(id);
//	}
	
	@GET
	@Path("/lookup/{authToken}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByUsername(@PathParam("authToken") String username) {
		return userDAO.readName(username);
	}
	
	@GET
	@Path("/authenticate")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean authenticateUser(@QueryParam("username") String username, @QueryParam("password") String password) {
		return userDAO.authenticateUser(username, password);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createUser(User user) {
		userDAO.create(user);
	}

	@PUT
	@Path("/{authToken}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateUser(@PathParam("authToken") int id, User user) {
		userDAO.update(id, user);
	}
}
