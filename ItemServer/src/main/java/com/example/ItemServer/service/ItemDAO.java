package com.example.ItemServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.ItemServer.entity.Item;

@Repository
public class ItemDAO {
	
	public JdbcTemplate jdbcTemplate;

	@Autowired
	public ItemDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
		private RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
		    Item item = new Item();
		    item.setName(rs.getString("name"));
		    item.setPrice(rs.getDouble("price"));
		    item.setAuctionType(rs.getString("auctionType"));
		    item.setEndTime(rs.getString("endTime"));
		    item.setWinner(rs.getString("winner"));
		    item.setItemID(rs.getInt("itemID"));
		    return item;
		};
	
	/**
	* Gets all items from the items database.
	*
	* @return         all items.
	*/
	public List<Item> getAllItems() {
		String sql = "SELECT * FROM items";
		return jdbcTemplate.query(sql, itemRowMapper);
	}

	/**
	* Gets item with a matching ID from the items database.
	*
	* @param  id   a number.
	* @return         item.
	*/
	public List<Item> getItemByID(int id) {
		String sql = "SELECT * FROM items WHERE itemID = " + id;
		return jdbcTemplate.query(sql, itemRowMapper);
	}
	
	/**
	* Gets items from the items database that match with the keyword typed in by the user.
	*
	* @param  term   a search term.
	* @return         a list of items.
	*/
	public List<Item> searchItem(String term) {
		String sql = "SELECT * FROM items WHERE name LIKE '%" + term + "%'";
		return jdbcTemplate.query(sql, itemRowMapper);
	}
	
	/**
	* Add item to item database.
	*
	* @param  item   an item.
	*/
	public void createItem(Item item) {
	    String sql = "INSERT INTO items (name, price, type, endTime, auctionType) VALUES (?, ?, ?, ?, ?)";
	    jdbcTemplate.update(sql, item.getName(), item.getPrice(), item.getAuctionType(), item.getEndTime());
	}
	/**
	* Updates item in item database.
	*
    * @param  id   a number.
	* @param  item  an item.
	*/
	public void updateItem(int id, Item item) {
	    String sql = "UPDATE items SET name = ?, price = ?, type = ?, endTime = ?, auctionType = ? WHERE itemID = ?";
	    jdbcTemplate.update(sql, item.getName(), item.getPrice(), item.getAuctionType(), item.getEndTime(), id);
	}
	
	/**
	* Updates the winner of an item in item database.
	*
    * @param  id   a number.
	* @param  winner  the winner.
	*/
	public void updateItemWinner(int id, String winner) {
		String sql = "UPDATE items SET winner = '"+ winner +"' WHERE itemID = "+ id;
		jdbcTemplate.update(sql);
	}
	
	/**
	* Deletes item in item database.
	*
    * @param  id   a number.
	*/
	public void deleteItem(int id) {
		String sql = "DELETE FROM items WHERE itemID = "+ id;
		jdbcTemplate.update(sql);
	}
	
}
