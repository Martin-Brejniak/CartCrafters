package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.DutchAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
            String sql = "UPDATE auctions SET minimumPrice = ?, decrement = ? WHERE auctionId = ?";
            jdbcTemplate.update(sql, dutchAuction.getMinimumPrice(), dutchAuction.getDecrement(), auction.getAuctionId());
        }
    }

    @Override
    public void updateAuction(Auction auction) {
        super.updateAuction(auction);

        if (auction instanceof DutchAuction) {
            DutchAuction dutchAuction = (DutchAuction) auction;
            String sql = "UPDATE auctions SET minimumPrice = ?, decrement = ? WHERE auctionId = ?";
            jdbcTemplate.update(sql, dutchAuction.getMinimumPrice(), dutchAuction.getDecrement(), auction.getAuctionId());
        }
    }

    @Override
    public DutchAuction getAuctionById(int auctionId) {
        Auction auction = super.getAuctionById(auctionId);
        DutchAuction dutchAuction = null;

        if (auction != null) {
            dutchAuction = new DutchAuction();
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
            String sql = "SELECT minimumPrice, decrement FROM auctions WHERE auctionId = ?";
            Object[] params = new Object[]{auctionId};
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

            if (!rows.isEmpty()) {
                Map<String, Object> row = rows.get(0);

                // Check for null values before accessing them
                if (row.get("minimumPrice") != null) {
                    dutchAuction.setMinimumPrice((Double) row.get("minimumPrice"));
                }

                if (row.get("decrement") != null) {
                    dutchAuction.setDecrement((Integer) row.get("decrement"));
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
            // End the auction if no buyer
            auction.setAuctionEnded(true);
        }

        auction.setCurrentPrice(newPrice);
        updateAuction(auction);
        return auction;
    }


}