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

    private final AuctionDAO auctionDAO;
    private final DutchAuctionUpdate dutchAuctionUpdate;
    private final DutchAuctionSearch dutchAuctionSearch;

    @Autowired
    public DutchAuctionWatch(AuctionDAO auctionDAO, DutchAuctionUpdate dutchAuctionUpdate, DutchAuctionSearch dutchAuctionSearch) {
        this.auctionDAO = auctionDAO;
        this.dutchAuctionUpdate = dutchAuctionUpdate;
        this.dutchAuctionSearch = dutchAuctionSearch;
    }

    // This method will decrement the price of each Dutch auction every 3 minutes
    @Scheduled(fixedRate = 60000) // 180000 milliseconds = 3 minutes
    public void monitorDecrement() {
        List<Auction> allAuctions = dutchAuctionSearch.getAllOpenDutchAuctions();
        for (Auction auction : allAuctions) {
            if (auction instanceof DutchAuction) {
                DutchAuction dutchAuction = (DutchAuction) auction;
                if (!dutchAuction.isAuctionEnded()) {
                    dutchAuctionUpdate.decrementPrice(dutchAuction.getAuctionId());
                }
            }
        }
    }

    // This method will check every minute if 30 minutes have passed since the auction reached its minimum price
    @Scheduled(cron = "0 */1 * * * *") // Every 1 minute
    public void checkAndEndAuctions() {
        List<Auction> allAuctions = dutchAuctionSearch.getAllOpenDutchAuctions();
        Date currentTime = new Date();
        for (Auction auction : allAuctions) {
            if (auction instanceof DutchAuction) {
                DutchAuction dutchAuction = (DutchAuction) auction;
                if (dutchAuction.getCurrentPrice() <= dutchAuction.getMinimumPrice() && !dutchAuction.isAuctionEnded()) {
                    Date minPriceReachedTime = dutchAuction.getMinimumPriceReachedTime();
                    if (minPriceReachedTime != null) {
                        long timeElapsed = currentTime.getTime() - minPriceReachedTime.getTime();
                        if (timeElapsed >= 1800000) { // 1800000 milliseconds = 30 minutes
                            dutchAuctionUpdate.closeAuction(dutchAuction.getAuctionId());
                        }
                    }
                }
            }
        }
    }
}

//testing decrement's push to git

