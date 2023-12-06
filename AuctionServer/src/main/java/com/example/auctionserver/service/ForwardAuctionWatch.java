package com.example.auctionserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.auctionserver.entity.Auction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ForwardAuctionWatch {

	

	    private final AuctionDAO auctionDAO;
	    private final ForwardAuctionUpdate forwardAuctionUpdate;
		private final ForwardAuctionSearch forwardAuctionSearch;
	    
	    

	    @Autowired
	    public ForwardAuctionWatch (AuctionDAO auctionDAO, ForwardAuctionUpdate forwardAuctionUpdate, ForwardAuctionSearch forwardAuctionSearch) {
	        this.auctionDAO = auctionDAO;
	        this.forwardAuctionUpdate = forwardAuctionUpdate;
	        this.forwardAuctionSearch = forwardAuctionSearch;
	    }
    
    public void monitorAuctions() {
        try {
            List<Auction> allAuctions = forwardAuctionSearch.getAllForwardAuctions(); 
            Date endDate;
            Date timeNow;
            for (Auction auction : allAuctions) {
                timeNow = new Date();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                String formattedDate = formatter.format(timeNow);

                timeNow = formatter.parse(formattedDate);
                endDate = auction.getEndTimeOfAuction(); 

                if (timeNow.getTime() >= endDate.getTime()) {
                    System.out.println(timeNow);
                    System.out.println(endDate);
                    forwardAuctionUpdate.closeAuction(auction.getAuctionId()); 
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to end auction");
        }
    }
}
