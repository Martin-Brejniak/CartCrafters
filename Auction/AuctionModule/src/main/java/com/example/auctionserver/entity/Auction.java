package com.example.auctionserver.entity;



import java.util.Date;

import jakarta.persistence.Entity;
public class Auction {
	 int auctionId;
	    int itemId;
	    String auctionType;
	    double initialPrice;
	    double currentPrice;
	    Date startTimeOfAuction;
	    Date endTimeOfAuction;
	    boolean auctionEnded;
	    int soldToUserId; public Auction() {
        
    }

    public Auction(int itemId, String auctionType, double initialPrice, double currentPrice,
                   Date startTimeOfAuction, Date endTimeOfAuction, boolean auctionEnded, int soldToUserId) {
        this.itemId = itemId;
        this.auctionType = auctionType;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.startTimeOfAuction = startTimeOfAuction;
        this.endTimeOfAuction = endTimeOfAuction;
        this.auctionEnded = auctionEnded;
        this.soldToUserId = soldToUserId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getStartTimeOfAuction() {
        return startTimeOfAuction;
    }

    public void setStartTimeOfAuction(Date startTimeOfAuction) {
        this.startTimeOfAuction = startTimeOfAuction;
    }

    public Date getEndTimeOfAuction() {
        return endTimeOfAuction;
    }

    public void setEndTimeOfAuction(Date endTimeOfAuction) {
        this.endTimeOfAuction = endTimeOfAuction;
    }

    public boolean isAuctionEnded() {
        return auctionEnded;
    }

    public void setAuctionEnded(boolean auctionEnded) {
        this.auctionEnded = auctionEnded;
    }

    public int getSoldToUserId() {
        return soldToUserId;
    }

    public void setSoldToUserId(int id) {
        this.soldToUserId = id;
    }
}
