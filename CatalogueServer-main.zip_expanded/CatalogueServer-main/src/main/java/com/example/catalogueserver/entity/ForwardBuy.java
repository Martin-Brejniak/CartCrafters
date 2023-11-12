package com.example.catalogueserver.entity;

public class ForwardBuy {
    
    private int auctionId;
    private int userId;
    private double bidAmount;

    public ForwardBuy() {
    }

    public ForwardBuy(int auctionId, int userId, double bidAmount) {
        this.auctionId = auctionId;
        this.userId = userId;
        this.bidAmount = bidAmount;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
