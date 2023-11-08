package com.auctionmodule;

import java.util.Date;

public class DutchAuction extends Auction{
    double minimumPrice;
    int decrement;

    public DutchAuction() {
        
    }

    public DutchAuction(int itemId, String auctionType, double initialPrice, double currentPrice,
                        Date startTimeOfAuction, Date endTimeOfAuction, boolean auctionEnded, int soldToUserId,
                        double minimumPrice, int decrement) {
        super(itemId, auctionType, initialPrice, currentPrice, startTimeOfAuction, endTimeOfAuction, auctionEnded, soldToUserId);
        this.minimumPrice = minimumPrice;
        this.decrement = decrement;
    }

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
}
