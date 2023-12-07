package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.DutchAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DutchAuctionWatch {

    private final DutchAuctionDAO dutchAuctionDAO;
    private final DutchAuctionUpdate dutchAuctionUpdate;
    private final DutchAuctionSearch dutchAuctionSearch;

    @Autowired
    public DutchAuctionWatch(DutchAuctionDAO dutchAuctionDAO, DutchAuctionUpdate dutchAuctionUpdate, DutchAuctionSearch dutchAuctionSearch) {
        this.dutchAuctionDAO = dutchAuctionDAO;
        this.dutchAuctionUpdate = dutchAuctionUpdate;
        this.dutchAuctionSearch = dutchAuctionSearch;
    }

    // This method will decrement the price of each Dutch auction every 3 minutes

    @Scheduled(fixedRate = 60000) // 180000 milliseconds = 3 minutes
    public void monitorDecrement() {
        List<Auction> allDutchAuctions = dutchAuctionSearch.getAllDutchAuctions();
        for (Auction dutchAuction : allDutchAuctions) {
            System.out.println("checking in watch");
            if (!dutchAuction.isAuctionEnded()) {
                dutchAuctionUpdate.decrementPrice(dutchAuction.getAuctionId());
            }
        }
    }

    // This method will check every minute if 30 minutes have passed since the auction reached its minimum price
    @Scheduled(cron = "0 */1 * * * *") // Every 1 minute
    public void checkAndEndAuctions() {
        List<Auction> allDutchAuctions = dutchAuctionSearch.getAllDutchAuctions();
        Date currentTime = new Date();
        for (Auction auction : allDutchAuctions) {
            if (!auction.isAuctionEnded()) {
                DutchAuction dutchAuction = dutchAuctionDAO.getAuctionById(auction.getAuctionId());
                if (dutchAuction != null && dutchAuction.getCurrentPrice() <= dutchAuction.getMinimumPrice()) {
                    Date minPriceReachedTime = dutchAuction.getMinimumPriceReachedTime();
                    if (minPriceReachedTime != null) {
                        long timeElapsed = currentTime.getTime() - minPriceReachedTime.getTime();
                        if (timeElapsed >= 18000) { // 1800000 milliseconds = 30 minutes
                            dutchAuctionUpdate.closeAuction(dutchAuction.getAuctionId());
                        }
                    }
                }
            }
        }
    }
}