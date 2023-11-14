package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.DutchAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DutchAuctionWatch {

    private final AuctionDAO auctionDAO;
    private final DutchAuctionUpdate dutchAuctionUpdate;
	private final DutchAuctionSearch dutchAuctionSearch;
    
    

    @Autowired
    public DutchAuctionWatch (AuctionDAO auctionDAO, DutchAuctionUpdate dutchAuctionUpdate, DutchAuctionSearch dutchAuctionSearch) {
        this.auctionDAO = auctionDAO;
        this.dutchAuctionUpdate = dutchAuctionUpdate;
        this.dutchAuctionSearch = dutchAuctionSearch;
    }

    @Scheduled(fixedRate = 10000)
    public void monitorDecrement() {
        try {
            List<Auction> allAuctions = dutchAuctionSearch.getAllDutchAuctions();
            for (Auction auction : allAuctions) {
            	dutchAuctionUpdate.decrementPrice(auction.getAuctionId());
            }
        } catch (Exception e) {
            System.out.println("Unable to decrement auction");
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void monitorClosure() {
        try {
            List<Auction> allAuctions = dutchAuctionSearch.getAllDutchAuctions();
            Date timeNow;
            for (Auction auction : allAuctions) {
                timeNow = new Date();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                String formattedDate = formatter.format(timeNow);

                timeNow = formatter.parse(formattedDate);
                Date endDate = auction.getEndTimeOfAuction();

                if (timeNow.getTime() >= endDate.getTime()) {
                    System.out.println(timeNow);
                    System.out.println(endDate);
                    dutchAuctionUpdate.closeAuction(auction.getAuctionId());
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to end auction");
        }
    }
}
