package com.auctionmodule;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.jvnet.hk2.annotations.Service;

@Service
public class DutchAuctionUpdate {

	
    private static AuctionDAO auctionDAO;

    public DutchAuctionUpdate(AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
    }

    public void resetDutchAuctions() {
        List<Auction> dutchAuctions = auctionDAO.getAuctionsByType("Dutch");

        for (Auction auction : dutchAuctions) {
            auction.setCurrentPrice(auction.getInitialPrice());
            auction.setSoldToUserId(0);
            auction.setAuctionEnded(false);
            auctionDAO.updateAuction(auction);
        }
    }

    public Auction decrementPrice(int auctionId) {
        Auction auction = auctionDAO.getAuctionById(auctionId);

        if (auction != null) {
            double newPrice = auction.getCurrentPrice() - ((DutchAuction) auction).getDecrement();
            if (newPrice < ((DutchAuction) auction).getMinimumPrice()) {
                newPrice = ((DutchAuction) auction).getMinimumPrice();
            }

            auction.setCurrentPrice(newPrice);
            auctionDAO.updateAuction(auction);
            return auction;
        }

        return null;
    }

    public Auction closeAuction(int auctionId) {
        Auction auction = auctionDAO.getAuctionById(auctionId);

        if (auction != null) {
            auction.setAuctionEnded(true);
            auctionDAO.updateAuction(auction);
            return auction;
        }

        return null;
    }

    public Auction buyAuction(int auctionId, int userId) throws AccessDeniedException {
        Auction auction = auctionDAO.getAuctionById(auctionId);

        if (auction != null && !auction.isAuctionEnded()) {
            auction.setSoldToUserId(userId);
            closeAuction(auctionId);
            return auction;
        } else {
            throw new AccessDeniedException("Could not buy the auction.");
        }
    }
}
