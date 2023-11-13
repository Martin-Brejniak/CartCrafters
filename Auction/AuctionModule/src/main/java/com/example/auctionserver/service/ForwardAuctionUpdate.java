package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.Bid;
import com.example.auctionserver.entity.ForwardAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ForwardAuctionUpdate {

    private final AuctionDAO auctionDAO;

    @Autowired
    public ForwardAuctionUpdate(AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
    }

    public Auction placeBid(int auctionId, int userId, double bidAmount) {
        Auction auction = auctionDAO.getAuctionById(auctionId);

        if (auction != null && !auction.isAuctionEnded() && auction instanceof ForwardAuction) {
            ForwardAuction forwardAuction = (ForwardAuction) auction;

            if (bidAmount > forwardAuction.getCurrentPrice()) {
                forwardAuction.setCurrentPrice(bidAmount);
                forwardAuction.setHighestBidderUserId(userId);
                forwardAuction.setHighestBid(bidAmount);
                auctionDAO.updateAuction(forwardAuction);
            }

            return forwardAuction;
        }

        return null;
    }

    public Auction closeAuction(int auctionId) {
        Auction auction = auctionDAO.getAuctionById(auctionId);

        if (auction != null && auction instanceof ForwardAuction) {
            auction.setAuctionEnded(true);
            auctionDAO.updateAuction(auction);
            return auction;
        }

        return null;
    }

    public ForwardAuction updateAuctionBid(Bid bid) {
        try {
            ForwardAuction auction = (ForwardAuction) auctionDAO.getAuctionById(bid.getAuctionId());
            if (auction.getHighestBid() >= bid.getBidAmount() || auction.isAuctionEnded()) {
                throw new IllegalArgumentException("Error with auction");
            }

            auction.setHighestBid(bid.getBidAmount());
            auction.setCurrentPrice(bid.getBidAmount());
            auction.setHighestBidderUserId(bid.getUserId());

            auctionDAO.updateAuction(auction);

            return auction;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid auction");
        }
    }
    
    public void resetAuction(int time) {
        List<Auction> auctionList = auctionDAO.getAllAuctions();

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
                forwardAuction.setEndTimeOfAuction(new Timestamp(now.getTime() + (time * 60000))); // Assuming time is in minutes

                auctionDAO.updateAuction(forwardAuction);
            }
        }
    }
    
}
    
    