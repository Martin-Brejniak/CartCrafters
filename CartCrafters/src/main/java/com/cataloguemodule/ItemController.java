package com.cataloguemodule;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/item")
public class ItemController {
	
	private ItemDAO itemDAO = new ItemDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getAllItem() {
		return itemDAO.readAll();
	}

}
