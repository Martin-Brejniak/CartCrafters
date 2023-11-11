package com.auctionmodule;

import java.util.Date;

public class ForwardAuction extends Auction{
	 int highestBidderUserId; 
     double highestBid;

    public ForwardAuction() {
        
    }

    public ForwardAuction(int itemId, String auctionType, double initialPrice, double currentPrice,
                        Date startTimeOfAuction, Date endTimeOfAuction, boolean auctionEnded, int soldToUserId,
                        double minimumPrice, int decrement) {
        super(itemId, auctionType, initialPrice, currentPrice, startTimeOfAuction, endTimeOfAuction, auctionEnded, soldToUserId);
        this.highestBidderUserId = highestBidderUserId;
        this.highestBid = highestBid;
    }

	public int getHighestBidderUserId() {
		return highestBidderUserId;
	}

	public void setHighestBidderUserId(int highestBidderUserId) {
		this.highestBidderUserId = highestBidderUserId;
	}

	public double getHighestBid() {
		return highestBid;
	}

	public void setHighestBid(double highestBid) {
		this.highestBid = highestBid;
	}

}
