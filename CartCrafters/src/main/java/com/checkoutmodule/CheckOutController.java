package com.checkoutmodule;

import java.util.ArrayList;
import java.util.List;

import com.cataloguemodule.Item;
import com.cataloguemodule.ItemDAO;
//import com.usermodule.User;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/payment")
public class CheckOutController {
	
private CheckOutDAO checkoutDAO = new CheckOutDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getAllItem() {		
		return checkoutDAO.readAll();
	}
	
	@GET
	@Path("/{authToken}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getProductById(@PathParam("authToken") int id) {
		return checkoutDAO.readID(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> searchProduct(@QueryParam("term") String term) {
		return checkoutDAO.search(term);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createProduct(Item item) {
		checkoutDAO.create(item);
	}
	
	@PUT
	@Path("/{authToken}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProduct(@PathParam("authToken") int id, Item item) {
		checkoutDAO.update(id, item);
	}
	
	@DELETE
	@Path("/{authToken}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProduct(@PathParam("authToken") int id) {
		checkoutDAO.delete(id);
	}
}
