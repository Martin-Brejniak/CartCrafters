package com.example.auctionserver.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auctionserver.entity.Auction;


@Service
public class DutchAuctionSearch {

    private final AuctionDAO auctionDAO;

    @Autowired
    public DutchAuctionSearch(AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
    }

    public List<Auction> getAllDutchAuctions() {
        if (auctionDAO == null) {
            throw new IllegalStateException("AuctionDAO has not been set");
        }
        return auctionDAO.getAuctionsByType("Dutch");
    }

    public List<Auction> getAllOpenDutchAuctions() {
        List<Auction> dutchAuctions = getAllDutchAuctions();
        dutchAuctions.removeIf(Auction::isAuctionEnded);
        return dutchAuctions;
    }

    public Auction getDutchAuctionDetails(int auctionId) {
        if (auctionDAO == null) {
            throw new IllegalStateException("AuctionDAO has not been set");
        }
        return auctionDAO.getAuctionById(auctionId);
    }

    public ResultMessage isUserWinner(int auctionId, int userId) {
        Auction auction = getDutchAuctionDetails(auctionId);

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


    public List<Auction> searchDutchAuctionsByItems(List<String> itemIds) throws NoSuchElementException {
        List<Auction> auctions = getAllDutchAuctions();
        auctions.removeIf(auction -> !auction.isAuctionEnded() || !itemIds.contains(String.valueOf(auction.getItemId())));
        if (auctions.isEmpty()) {
            throw new NoSuchElementException("No Dutch auctions found for the specified items.");
        }
        return auctions;
    }
}
