package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.Bid;
import com.example.auctionserver.entity.ForwardAuction;
import com.example.auctionserver.exceptions.AuctionEndedException;
import com.example.auctionserver.exceptions.AuctionNotFoundException;
import com.example.auctionserver.exceptions.InvalidBidException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ForwardAuctionUpdate {

	private final ForwardAuctionDAO forwardAuctionDAO;

	@Autowired
	public ForwardAuctionUpdate(ForwardAuctionDAO forwardAuctionDAO) {
		this.forwardAuctionDAO = forwardAuctionDAO;
	}

	public Auction placeBid(int auctionId, int userId, double bidAmount) {
		ForwardAuction auction = forwardAuctionDAO.getAuctionById(auctionId);
		System.out.println("Before f update");
		

        if (auction == null) {
            throw new AuctionNotFoundException("Auction not found with ID: " + auctionId);
        }

        if (auction.isAuctionEnded()) {
            throw new AuctionEndedException("Auction has already ended.");
        }

        if (bidAmount <= auction.getCurrentPrice()) {
            throw new InvalidBidException("Bid amount must be higher than the current price.");
        }
        
		if (auction != null && !auction.isAuctionEnded() && auction instanceof ForwardAuction) {
			ForwardAuction forwardAuction = (ForwardAuction) auction;

			if (bidAmount > forwardAuction.getCurrentPrice()) {
				forwardAuction.setCurrentPrice(bidAmount);
				forwardAuction.setHighestBidderUserId(userId);
				forwardAuction.setHighestBid(bidAmount);
				forwardAuctionDAO.updateAuction(forwardAuction);
			}

			return forwardAuction;
		}

		return auction;
	}

	public Auction closeAuction(int auctionId) {
	    ForwardAuction forwardAuction = forwardAuctionDAO.getAuctionById(auctionId);

	    if (forwardAuction != null && forwardAuction.getAuctionType().equalsIgnoreCase("forward")) {
	        System.out.println("Before closing auction: " + forwardAuction);

	        if (forwardAuction.isAuctionEnded()) {
	            throw new AuctionEndedException("Auction with ID: " + auctionId + " has already ended.");
	        }

	        forwardAuction.setAuctionEnded(true);
	        forwardAuction.setEndTimeOfAuction(new Date());

	        forwardAuctionDAO.updateAuction(forwardAuction);
	        System.out.println("After closing auction: " + forwardAuction);
	        return forwardAuction;
	    }

	    return null;
	}


	public ForwardAuction updateAuctionBid(Bid bid) {
		try {
			ForwardAuction auction = (ForwardAuction) forwardAuctionDAO.getAuctionById(bid.getAuctionId());
			if (auction.getHighestBid() >= bid.getBidAmount() || auction.isAuctionEnded()) {
				throw new IllegalArgumentException("Error with auction");
			}

			auction.setHighestBid(bid.getBidAmount());
			auction.setCurrentPrice(bid.getBidAmount());
			auction.setHighestBidderUserId(bid.getUserId());
			auction.setEndTimeOfAuction(new Date());

			forwardAuctionDAO.updateAuction(auction);

			return auction;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid auction");
		}
	}

	public void resetAuction(int time) {
		List<Auction> auctionList = forwardAuctionDAO.getAllAuctions();

		for (Auction auction : auctionList) {
			if (auction instanceof ForwardAuction) {
				ForwardAuction forwardAuction = (ForwardAuction) auction;
				// Resetting various fields for Forward auctions
				forwardAuction.setCurrentPrice(forwardAuction.getInitialPrice());
				forwardAuction.setHighestBid(forwardAuction.getInitialPrice());
				forwardAuction.setSoldToUserId(0); // Assuming 0 represents no buyer
				forwardAuction.setHighestBidderUserId(0); // Assuming 0 represents no bidder
				forwardAuction.setAuctionEnded(false);

				Timestamp now = new Timestamp(System.currentTimeMillis());
				forwardAuction.setStartTimeOfAuction(now);
				forwardAuction.setEndTimeOfAuction(new Timestamp(now.getTime() + (time * 60000))); // Assuming time is
																									// in minutes

				forwardAuctionDAO.updateAuction(forwardAuction);
			}
		}
	}

}
