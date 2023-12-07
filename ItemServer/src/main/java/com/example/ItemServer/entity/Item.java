package com.example.ItemServer.entity;

public class Item {
	private String name;
	private double price;
	private String type;
	private String endTime;
	private int itemID;
	private String winner;
	private String auctionType;
	private String description;
    private double shipcost;
    private double expShipCost;
	
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
	// Getter and Setter for auctionType
    public String getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
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
	
	 /**
	    * Get the description of item.
	    *
	    * @return the item's description.
	    */
	    public String getDescription() {
	        return description;
	    }

	    /**
	    * Set the description of item.
	    *
	    * @param description the item's description.
	    */
	    public void setDescription(String description) {
	        this.description = description;
	    }

	    /**
	    * Get the shipping cost of item.
	    *
	    * @return the item's shipping cost.
	    */
	    public double getShipcost() {
	        return shipcost;
	    }

	    /**
	    * Set the shipping cost of item.
	    *
	    * @param shipcost the item's shipping cost.
	    */
	    public void setShipcost(double shipcost) {
	        this.shipcost = shipcost;
	    }

	    /**
	    * Get the expediated shipping cost of item.
	    *
	    * @return the item's expediated shipping cost.
	    */
	    public double getExpShipCost() {
	        return expShipCost;
	    }

	    /**
	    * Set the expediated shipping cost of item.
	    *
	    * @param expShipCost the item's expediated shipping cost.
	    */
	    public void setExpShipCost(double expShipCost) {
	        this.expShipCost = expShipCost;
	    }
	
}
