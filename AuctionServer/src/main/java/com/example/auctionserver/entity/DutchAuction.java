package com.example.auctionserver.entity;

import java.util.Date;

public class DutchAuction extends Auction {
    private double minimumPrice;
    private int decrement;
    private Date minimumPriceReachedTime; // New field to track when minimum price is first reached

    // Existing constructors
    public DutchAuction() {
        super();
    }

    public DutchAuction(int itemId, String auctionType, double initialPrice, double currentPrice,
                        Date startTimeOfAuction, Date endTimeOfAuction, boolean auctionEnded, int soldToUserId,
                        double minimumPrice, int decrement) {
        super(itemId, auctionType, initialPrice, currentPrice, startTimeOfAuction, endTimeOfAuction, auctionEnded, soldToUserId);
        this.minimumPrice = minimumPrice;
        this.decrement = decrement;
    }

    // Existing getters and setters
    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public int getDecrement() {
        return decrement;
    }

    public void setDecrement(int decrement) {
        this.decrement = decrement;
    }

    // Getter and setter for minimumPriceReachedTime
    public Date getMinimumPriceReachedTime() {
        return minimumPriceReachedTime;
    }

    public void setMinimumPriceReachedTime(Date minimumPriceReachedTime) {
        this.minimumPriceReachedTime = minimumPriceReachedTime;
    }
}
