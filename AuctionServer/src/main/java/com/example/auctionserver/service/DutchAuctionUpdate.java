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
    	
    	System.out.println("checking in update");
    	
        DutchAuction auction = dutchAuctionDAO.getAuctionById(auctionId);
        if (auction == null || !(auction instanceof DutchAuction)) {
            throw new AuctionNotFoundException("Auction not found with ID: " + auctionId);
        }

        DutchAuction dutchAuction = (DutchAuction) auction;

        if (dutchAuction.isAuctionEnded()) {
            throw new AuctionEndedException("Auction has already ended.");
        }

        double newPrice = dutchAuction.getCurrentPrice() - dutchAuction.getDecrement();

        // Check if the new price falls below the minimum price
        if (newPrice <= dutchAuction.getMinimumPrice()) {
            newPrice = dutchAuction.getMinimumPrice();

            // Set the minimumPriceReachedTime if it's not already set
            if (dutchAuction.getMinimumPriceReachedTime() == null) {
                dutchAuction.setMinimumPriceReachedTime(new Date());
            }
        }

        dutchAuction.setCurrentPrice(newPrice);
        dutchAuctionDAO.updateAuction(dutchAuction); // Persist changes to the database

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