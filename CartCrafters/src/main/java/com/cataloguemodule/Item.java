package com.cataloguemodule;

public class Item {
	private String name;
	private double price;
	private String type;
	private String endTime;
	private int itemID;
	private String winner;
	
	/**
	* Get the name of item.
	*
	* @return         the item's name.
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Set the name of item.
	*
	* @param  name   the item's name.
	*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	* Get the price of item.
	*
	* @return         the item's price.
	*/
	public double getPrice() {
		return price;
	}
	
	/**
	* Set the price of item.
	*
	* @param  price   the item's price.
	*/
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	* Get the auction type of item.
	*
	* @return         the item's auction type.
	*/
	public String getType() {
		return type;
	}
	
	/**
	* Set the auction type of item.
	*
	* @param  type   the item's auction type.
	*/
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	* Get the end time of item.
	*
	* @return         the item's end time.
	*/
	public String getEndTime() {
		return endTime;
	}
	
	/**
	* Set the end time of item.
	*
	* @param  endTime   the item's end time.
	*/
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	/**
	* Get the ID of item.
	*
	* @return         the item's ID.
	*/
	public int getItemID() {
		return itemID;
	}
	
	/**
	* Set the ID of item.
	*
	* @param  itemID   the item's ID.
	*/
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	/**
	* Get the winner of item.
	*
	* @return         the item's winner.
	*/
	public String getWinner() {
		return winner;
	}
	
	/**
	* Set the winner of item.
	*
	* @param  winner   the item's winner.
	*/
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
}
