package com.example.catalogueserver.service;

import com.example.catalogueserver.entity.Auction;
import com.example.catalogueserver.entity.ForwardAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
