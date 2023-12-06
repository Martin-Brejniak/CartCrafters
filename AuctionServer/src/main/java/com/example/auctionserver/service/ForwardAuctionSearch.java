package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.Bid;
import com.example.auctionserver.entity.ForwardAuction;
import com.example.auctionserver.exceptions.AuctionNotFoundException;
import com.example.auctionserver.exceptions.InvalidAuctionTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ForwardAuctionSearch {

    private final ForwardAuctionDAO auctionDAO;
    private final BidDAO bidDAO;

    @Autowired
    public ForwardAuctionSearch(ForwardAuctionDAO auctionDAO, BidDAO bidDAO) {
        this.auctionDAO = auctionDAO;
        this.bidDAO = bidDAO;
    }

    public List<Auction> searchForwardAuctionsByItems(List<String> itemIds) throws NoSuchElementException {
        List<Auction> forwardAuctions = auctionDAO.getAllAuctions().stream()
                .filter(auction -> auction.getAuctionType().equals("forward"))
                .collect(Collectors.toList());
        
        forwardAuctions.removeIf(auction -> !itemIds.contains(String.valueOf(auction.getItemId())));
        
        if (forwardAuctions.isEmpty()) {
            throw new NoSuchElementException("No forward auctions found for the specified items.");
        }
        
        return forwardAuctions;
    }

    public List<Bid> getAllBidsForForwardAuction(int auctionId) {
        // Assuming that BidDAO has a method getBidsByAuctionId
        return bidDAO.getBidsByAuctionId(auctionId);
    }

    public ResultMessage isUserWinner(int auctionId, int userId) {
        Auction auction = getForwardAuctionDetails(auctionId);

        if (auction == null) {
            return new ResultMessage(false, "Auction not found with ID: " + auctionId);
        }

        if (!auction.isAuctionEnded()) {
            return new ResultMessage(false, "Auction has not ended yet.");
        }

        if (auction.getSoldToUserId() == userId) {
            return new ResultMessage(true, "User is the winner!");
        } else {
            return new ResultMessage(false, "User is not the winner.");
        }
    }
    
    public List<Auction> getAllForwardAuctions() {
        return auctionDAO.getAllAuctions().stream()
                .filter(auction -> "forward".equalsIgnoreCase(auction.getAuctionType()))
                .collect(Collectors.toList());
    }

    public ForwardAuction getForwardAuctionDetails(int auctionId) {
        ForwardAuction auction = auctionDAO.getAuctionById(auctionId);

        if (auction == null) {
            throw new AuctionNotFoundException("Auction not found with ID: " + auctionId);
        }

        if (!"forward".equalsIgnoreCase(auction.getAuctionType())) {
            throw new InvalidAuctionTypeException("Invalid auction type for ID: " + auctionId);
        }

        return auction;
    }

    public List<Auction> getAllOpenForwardAuctions() {
        return auctionDAO.getAllForwardAuctions().stream()
                .filter(auction -> "forward".equalsIgnoreCase(auction.getAuctionType()) && !auction.isAuctionEnded())
                .collect(Collectors.toList());
    }
}
