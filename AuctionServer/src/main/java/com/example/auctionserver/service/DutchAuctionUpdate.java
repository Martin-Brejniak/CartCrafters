package com.example.auctionserver.service;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.DutchAuction;
import com.example.auctionserver.exceptions.AuctionEndedException;
import com.example.auctionserver.exceptions.AuctionNotFoundException;
import com.example.auctionserver.exceptions.InvalidAuctionTypeException;
import com.example.auctionserver.entity.DutchAuction;


@Service
public class DutchAuctionUpdate {

    private final DutchAuctionDAO dutchAuctionDAO;

    @Autowired
    public DutchAuctionUpdate(DutchAuctionDAO dutchAuctionDAO) {
        this. dutchAuctionDAO =  dutchAuctionDAO;;
    }

    public void resetDutchAuctions() {
        List<Auction> dutchAuctions =  dutchAuctionDAO.getAuctionsByType("Dutch");

        for (Auction auction : dutchAuctions) {
            auction.setCurrentPrice(auction.getInitialPrice());
            auction.setSoldToUserId(0);
            auction.setAuctionEnded(false);
            dutchAuctionDAO.updateAuction(auction);
        }
    }
    

    public DutchAuction decrementPrice(int auctionId) {
        DutchAuction auction = dutchAuctionDAO.getAuctionById(auctionId);
        System.out.println("Before update");

        if (auction == null) {
            throw new AuctionNotFoundException("Auction not found with ID: " + auctionId);
        }

        if (!(auction instanceof DutchAuction)) {
            throw new InvalidAuctionTypeException("Invalid auction type.");
        }

        DutchAuction dutchAuction = (DutchAuction) auction;

        double newPrice = dutchAuction.getCurrentPrice() - dutchAuction.getDecrement();
        if (newPrice < dutchAuction.getMinimumPrice()) {
            newPrice = dutchAuction.getMinimumPrice();
        }

        if (auction.isAuctionEnded()) {
            throw new AuctionEndedException("Auction has already ended.");
        }

       // System.out.println("Before update: " + dutchAuction);
        dutchAuction.setCurrentPrice(newPrice);

        // Uncomment the lines if you want to update minimumPrice and decrement as well
        // dutchAuction.setMinimumPrice(dutchAuction.getMinimumPrice());
        // dutchAuction.setDecrement(dutchAuction.getDecrement());

        //System.out.println("After update: " + dutchAuction);

        dutchAuctionDAO.updateAuction(dutchAuction);
        return dutchAuction;
    }


    public DutchAuction closeAuction(int auctionId) {
        DutchAuction auction =  dutchAuctionDAO.getAuctionById(auctionId);

        if (auction != null && auction.getAuctionType().equalsIgnoreCase("Dutch")) {
            auction.setAuctionEnded(true);
            auction.setEndTimeOfAuction(new Date());
            dutchAuctionDAO.updateAuction(auction);
            return auction;
        }

        return null;
    }
  
    @Transactional
    public DutchAuction buyAuction(int auctionId, int userId) throws AuctionNotFoundException, AuctionEndedException, AccessDeniedException {
        DutchAuction auction = dutchAuctionDAO.getAuctionById(auctionId);

        if (auction == null) {
            throw new AuctionNotFoundException("Auction not found with ID: " + auctionId);
        }

        if (auction.isAuctionEnded()) {
            throw new AuctionEndedException("Auction has already ended.");
        }

        closeAuction(auctionId);
        auction.setSoldToUserId(userId);
        auction.setAuctionEnded(true);
        auction.setEndTimeOfAuction(new Date());
        dutchAuctionDAO.updateAuction(auction);

        return auction;
    }

}
    