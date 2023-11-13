package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ForwardAuctionSearch {

    private final AuctionDAO auctionDAO;
    private final BidDAO bidDAO;

    @Autowired
    public ForwardAuctionSearch(AuctionDAO auctionDAO, BidDAO bidDAO) {
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

    public boolean isUserWinner(int auctionId, int userId) {
        Auction auction = auctionDAO.getAuctionById(auctionId);
        return auction != null && auction.isAuctionEnded() && auction.getSoldToUserId() == userId;
    }
    
    public List<Auction> getAllForwardAuctions() {
        return auctionDAO.getAllAuctions().stream()
                .filter(auction -> "forward".equalsIgnoreCase(auction.getAuctionType()))
                .collect(Collectors.toList());
    }
    

    public Auction getForwardAuctionDetails(int auctionId) {
        Auction auction = auctionDAO.getAuctionById(auctionId);
        if(auction != null && "forward".equalsIgnoreCase(auction.getAuctionType())) {
            return auction;
        }
        return null; 
        
    }

}
