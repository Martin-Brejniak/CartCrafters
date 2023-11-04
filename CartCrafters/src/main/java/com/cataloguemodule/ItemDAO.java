package com.cataloguemodule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
	
	/**
	    * Gets all items from the items database.
	    *
	    * @return         all items.
	    */
		public List<Item> readAll() {
			String sql = "SELECT name, price, type, endTime, winner, itemID FROM items";
			List<Item> items = new ArrayList<>();
			
			try (Connection conn = ItemDatabaseConnection.connect();
			Statement stmt = conn.createStatement();
					
			ResultSet rs = stmt.executeQuery(sql)){
				while (rs.next()) {
					Item item = new Item();
					item.setName(rs.getString("name"));
					item.setPrice(rs.getDouble("price"));
					item.setType(rs.getString("type"));
					item.setEndTime(rs.getString("endTime"));
					item.setWinner(rs.getString("winner"));
					item.setItemID(rs.getInt("itemID"));
					items.add(item);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return items;
		}

}
