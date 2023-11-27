package com.example.ItemServer.controller;

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

import com.example.ItemServer.entity.Item;
import com.example.ItemServer.service.ItemDAO;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {
	
	private final ItemDAO itemDAO;
	
	@Autowired
	public ItemController(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	
	@GetMapping("/get-all")
    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }
	
	@GetMapping("/get-id")
    public List<Item> getItemByID(@RequestParam("itemID") int itemID) {
		return itemDAO.getItemByID(itemID);
    }
	
	@GetMapping("/search")
    public List<Item> searchItem(@RequestParam("term") String term) {
		return itemDAO.searchItem(term);
    }
	
	@PostMapping
    public void createItem(@RequestBody Item item) {
		itemDAO.createItem(item);
    }
	
	@PutMapping ("/update-all")
    public void updateItem(@RequestBody Item item, @RequestParam("itemID") int itemID) {
		itemDAO.updateItem(itemID, item);
    }
	
	@PutMapping ("/update-winner")
    public void updateItemWinner(@RequestParam("winner") String winner, @RequestParam("itemID") int itemID) {
		itemDAO.updateItemWinner(itemID, winner);
    }
	
	@DeleteMapping
    public void deleteItem(@RequestParam("itemID") int itemID) {
		itemDAO.deleteItem(itemID);
    }
	
}
