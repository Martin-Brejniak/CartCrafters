package com.auctionmodule;

import java.util.List;
import java.util.NoSuchElementException;

public class DutchAuctionSearch {

    private AuctionDAO auctionDAO;

    public DutchAuctionSearch(AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
    }

    public List<Auction> getAllDutchAuctions() {
        return auctionDAO.getAuctionsByType("Dutch");
    }

    public List<Auction> getAllOpenDutchAuctions() {
        List<Auction> dutchAuctions = getAllDutchAuctions();
        dutchAuctions.removeIf(Auction::isAuctionEnded);
        return dutchAuctions;
    }

    public Auction getDutchAuctionDetails(int auctionId) {
        return auctionDAO.getAuctionById(auctionId);
    }

    public boolean isUserWinner(int auctionId, int userId) {
        Auction auction = auctionDAO.getAuctionById(auctionId);
        return auction != null && auction.isAuctionEnded() && auction.getSoldToUserId() == userId;
    }

    public List<Auction> searchDutchAuctionsByItems(List<String> itemIds) throws NoSuchElementException {
        List<Auction> auctions = auctionDAO.getAuctionsByType("Dutch");
        auctions.removeIf(auction -> !auction.isAuctionEnded() || !itemIds.contains(String.valueOf(auction.getItemId())));
        if (auctions.isEmpty()) {
            throw new NoSuchElementException("No Dutch auctions found for the specified items.");
        }
        return auctions;
    }
}
