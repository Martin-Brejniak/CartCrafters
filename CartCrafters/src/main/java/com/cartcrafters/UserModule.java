package com.cartcrafters;

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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserModule {
	
	private UserDAO userDAO = new UserDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUser() {
		return userDAO.readAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createStudent(User user) {
		userDAO.create(user);
	}
//	
//	@GET
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Student getStudent(@PathParam("id") int id) {
//		return studentDAO.read(id);
//	}
//	
//	@PUT
//	@Path("/{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public void updateStudent(@PathParam("id") int id, Student student) {
//		studentDAO.update(id, student);
//	}
//	
//	@DELETE
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void deleteStudent(@PathParam("id") int id) {
//		studentDAO.delete(id);
//	}

}
