package com.checkout;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.usermodule.User;
import com.usermodule.UserDatabaseConnection;

public class CheckOutDAO {
	
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
		
		/**
		* Gets item with a matching ID from the items database.
		*
		* @param  id   a number.
		* @return         item.
		*/
		public Item readID(int id) {
			String sql = "SELECT name, price, type, endTime, winner FROM items WHERE itemID = ?";
			Item item = null;
			try (Connection conn = ItemDatabaseConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// Set the corresponding parameter
				pstmt.setInt(1, id);
				// Execute the query and get the result set
				try (ResultSet rs = pstmt.executeQuery()) {
					// Check if a result was returned
					if (rs.next()) {
						item = new Item();
						// Set the properties of the student object
						item.setName(rs.getString("name"));
						item.setPrice(rs.getDouble("price"));
						item.setType(rs.getString("type"));
						item.setEndTime(rs.getString("endTime"));
						item.setWinner(rs.getString("winner"));
						item.setItemID(id);
					}
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return item;
		}
		
		/**
		* Gets items from the items database that match with the keyword typed in by the user.
		*
		* @param  term   a search term.
		* @return         a list of items.
		*/
		public List<Item> search(String term) {
			String sql = "SELECT name, price, type, endTime, winner, itemID FROM items WHERE name LIKE '%" + term + "%'";
			
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
		
		/**
		* Add item to item database.
		*
		* @param  item   an item.
		*/
		public void create(Item item) {
			String sql = "INSERT INTO items(name, price, type, endTime) VALUES(?,?,?,?)";
			try (Connection conn = ItemDatabaseConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, item.getName());
				pstmt.setDouble(2, item.getPrice());
				pstmt.setString(3, item.getType());
				pstmt.setString(4, item.getEndTime());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		/**
		* Updates item in item database.
		*
	    * @param  id   a number.
		* @param  item  an item.
		*/
		public void update(int id, Item item) {
			//use prepared statements
			String sql = "UPDATE items SET name = ?, price = ?, type = ?, endTime = ? WHERE itemID = ?";
			try (Connection conn = ItemDatabaseConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// Set the corresponding parameters
				pstmt.setString(1, item.getName());
				pstmt.setDouble(2, item.getPrice());
				pstmt.setString(3, item.getType());
				pstmt.setString(4, item.getEndTime());
				pstmt.setInt(5, id);
				// Update the student record
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		/**
		* Deletes item in item database.
		*
	    * @param  id   a number.
		*/
		public void delete(int id) {
			String sql = "DELETE FROM items WHERE itemID = ?";
			try (Connection conn = ItemDatabaseConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// Set the corresponding parameter
				pstmt.setInt(1, id);
				// Delete the student record
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

}
