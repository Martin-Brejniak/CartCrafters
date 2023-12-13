package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.DutchAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class DutchAuctionDAO extends AuctionDAO {

    @Autowired
    public DutchAuctionDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void insertAuction(Auction auction) {
        super.insertAuction(auction);

        if (auction instanceof DutchAuction) {
            DutchAuction dutchAuction = (DutchAuction) auction;
            String sql = "INSERT INTO auctions (auctionId, minimumPrice, decrement, minimumPriceReachedTime) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, 
                auction.getAuctionId(), 
                dutchAuction.getMinimumPrice(), 
                dutchAuction.getDecrement(),
                dutchAuction.getMinimumPriceReachedTime() != null ? new Timestamp(dutchAuction.getMinimumPriceReachedTime().getTime()) : null);
        }
    }

    @Override
    public void updateAuction(Auction auction) {
        super.updateAuction(auction);

        if (auction instanceof DutchAuction) {
            DutchAuction dutchAuction = (DutchAuction) auction;
            String sql = "UPDATE auctions SET minimumPrice = ?, decrement = ?, minimumPriceReachedTime = ? WHERE auctionId = ?";
            jdbcTemplate.update(sql, 
                dutchAuction.getMinimumPrice(), 
                dutchAuction.getDecrement(),
                dutchAuction.getMinimumPriceReachedTime() != null ? new Timestamp(dutchAuction.getMinimumPriceReachedTime().getTime()) : null,
                dutchAuction.getAuctionId());
        }
    }

    @Override
    public DutchAuction getAuctionById(int auctionId) {
        Auction auction = super.getAuctionById(auctionId);
        DutchAuction dutchAuction = null;

        if (auction != null) {
            dutchAuction = new DutchAuction();
            // Set properties from the parent class
            dutchAuction.setAuctionId(auction.getAuctionId());
            dutchAuction.setItemId(auction.getItemId());
            dutchAuction.setAuctionType(auction.getAuctionType());
            dutchAuction.setInitialPrice(auction.getInitialPrice());
            dutchAuction.setCurrentPrice(auction.getCurrentPrice());
            dutchAuction.setStartTimeOfAuction(auction.getStartTimeOfAuction());
            dutchAuction.setEndTimeOfAuction(auction.getEndTimeOfAuction());
            dutchAuction.setAuctionEnded(auction.isAuctionEnded());
            dutchAuction.setSoldToUserId(auction.getSoldToUserId());

            // Retrieve additional properties for DutchAuction
            String sql = "SELECT minimumPrice, decrement, minimumPriceReachedTime FROM auctions WHERE auctionId = ?";
            Object[] params = new Object[]{auctionId};
            Map<String, Object> row = jdbcTemplate.queryForMap(sql, params);

            if (row != null) {
                dutchAuction.setMinimumPrice((Double) row.get("minimumPrice"));
                dutchAuction.setDecrement((Integer) row.get("decrement"));

                if (row.get("minimumPriceReachedTime") != null) {
                    Object timeValue = row.get("minimumPriceReachedTime");
                    
                    Date minimumPriceReachedTime = null;

                    // Check if timeValue is an instance of Timestamp
                    if (timeValue instanceof Timestamp) {
                        minimumPriceReachedTime = new Date(((Timestamp) timeValue).getTime());
                    }
                    // Check if timeValue is a Long (epoch milliseconds)
                    else if (timeValue instanceof Long) {
                        minimumPriceReachedTime = new Date((Long) timeValue);
                    }
                    // Check if timeValue is a String
                    else if (timeValue instanceof String) {
                        // Parse the String value - adjust the format as per your database's format
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            minimumPriceReachedTime = dateFormat.parse((String) timeValue);
                        } catch (ParseException e) {
                            System.err.println("Error parsing date string: " + e.getMessage());
                        }
                    }

                    // Set the parsed Date to the auction object
                    if (minimumPriceReachedTime != null) {
                        dutchAuction.setMinimumPriceReachedTime(minimumPriceReachedTime);
                    }
                }
            }
        }

        return dutchAuction;
    }
    
    // DECREMENT
    public DutchAuction decrementPrice(int auctionId) {
        DutchAuction auction = getAuctionById(auctionId);

        if (auction == null || auction.isAuctionEnded()) {
            return null;
        }

        double newPrice = auction.getCurrentPrice() - auction.getDecrement();
        if (newPrice <= auction.getMinimumPrice()) {
            newPrice = auction.getMinimumPrice();
            // Set the time when minimum price is first reached
            if(auction.getMinimumPriceReachedTime() == null){
                auction.setMinimumPriceReachedTime(new Date());
            }
            auction.setAuctionEnded(true);
        }

        auction.setCurrentPrice(newPrice);
        updateAuction(auction);
        return auction;
    }
    
    public void createDutchAuction(DutchAuction auction) {
        // SQL INSERT statement with appropriate column names for Dutch Auction
        String sql = "INSERT INTO auctions (itemId, auctionType, initialPrice, decrement, minimumPrice, currentPrice, startTimeOfAuction, endTimeOfAuction, auctionEnded) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Setting default values
        Date startTimeOfAuction = new Date(); // Current time as start time
        boolean auctionEnded = false; // Default value for auctionEnded

        jdbcTemplate.update(sql, 
                            auction.getItemId(), 
                            "Dutch", 
                            auction.getInitialPrice(), 
                            auction.getDecrement(), 
                            auction.getMinimumPrice(),
                            auction.getInitialPrice(),
                            new Timestamp(startTimeOfAuction.getTime()), 
                            new Timestamp(auction.getEndTimeOfAuction().getTime()), 
                            auctionEnded);
   
    }
}