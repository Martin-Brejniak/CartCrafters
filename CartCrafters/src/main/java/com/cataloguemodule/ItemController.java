package com.cataloguemodule;

import java.util.List;

import com.usermodule.User;

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

@Path("/item")
public class ItemController {
	
	private ItemDAO itemDAO = new ItemDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getAllItem() {
		return itemDAO.readAll();
	}
	
	@GET
	@Path("/{authToken}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getProductById(@PathParam("authToken") int id) {
		return itemDAO.readID(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> searchProduct(@QueryParam("term") String term) {
		return itemDAO.search(term);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createProduct(Item item) {
		itemDAO.create(item);
	}
	
	@PUT
	@Path("/{authToken}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProduct(@PathParam("authToken") int id, Item item) {
		itemDAO.update(id, item);
	}
	
	@PUT
	@Path("/winner/{authToken}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProductWinner(@PathParam("authToken") int id, @QueryParam("winner") String winner) {
		itemDAO.updateWinner(id, winner);
	}
	
	@DELETE
	@Path("/{authToken}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProduct(@PathParam("authToken") int id) {
		itemDAO.delete(id);
	}

}
