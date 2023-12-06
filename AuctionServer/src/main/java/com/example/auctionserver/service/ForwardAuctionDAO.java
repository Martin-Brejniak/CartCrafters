package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.ForwardAuction;
import com.example.auctionserver.entity.ForwardAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ForwardAuctionDAO extends AuctionDAO {
	
	private RowMapper<ForwardAuction> forwardauctionRowMapper = (rs, rowNum) -> {
		ForwardAuction auction = new ForwardAuction();
		auction.setAuctionId(rs.getInt("auctionId"));
		auction.setItemId(rs.getInt("itemId"));
		auction.setAuctionType(rs.getString("auctionType"));
		auction.setInitialPrice(rs.getDouble("initialPrice"));
		auction.setCurrentPrice(rs.getDouble("currentPrice"));
		auction.setStartTimeOfAuction(new Date(rs.getTimestamp("startTimeOfAuction").getTime()));
		auction.setEndTimeOfAuction(new Date(rs.getTimestamp("endTimeOfAuction").getTime()));
		auction.setAuctionEnded(rs.getBoolean("auctionEnded"));
		auction.setSoldToUserId(rs.getInt("soldToUserId"));
		return auction;
	};

    @Autowired
    public ForwardAuctionDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void insertAuction(Auction auction) {
        super.insertAuction(auction);

        if (auction instanceof ForwardAuction) {
            ForwardAuction forwardAuction = (ForwardAuction) auction;
            String sql = "UPDATE auctions SET highestBidderUserId = ?, highestBid = ? WHERE auctionId = ?";
            jdbcTemplate.update(sql, forwardAuction.getHighestBidderUserId(), forwardAuction.getHighestBid(), auction.getAuctionId());
        }
    }

    @Override
    public void updateAuction(Auction auction) {
        super.updateAuction(auction);

        if (auction instanceof ForwardAuction) {
            ForwardAuction forwardAuction = (ForwardAuction) auction;
            String sql = "UPDATE auctions SET highestBidderUserId = ?, highestBid = ? WHERE auctionId = ?";
            jdbcTemplate.update(sql, forwardAuction.getHighestBidderUserId(), forwardAuction.getHighestBid(), auction.getAuctionId());
        }
    }

    @Override
    public ForwardAuction getAuctionById(int auctionId) {
        Auction auction = super.getAuctionById(auctionId);
        ForwardAuction forwardAuction = null;

        if (auction != null) {
            forwardAuction = new ForwardAuction();
            forwardAuction.setAuctionId(auction.getAuctionId());
            forwardAuction.setItemId(auction.getItemId());
            forwardAuction.setAuctionType(auction.getAuctionType());
            forwardAuction.setInitialPrice(auction.getInitialPrice());
            forwardAuction.setCurrentPrice(auction.getCurrentPrice());
            forwardAuction.setStartTimeOfAuction(auction.getStartTimeOfAuction());
            forwardAuction.setEndTimeOfAuction(auction.getEndTimeOfAuction());
            forwardAuction.setAuctionEnded(auction.isAuctionEnded());
            forwardAuction.setSoldToUserId(auction.getSoldToUserId());

            // Retrieve additional properties for DutchAuction
            String sql = "SELECT highestBid, highestBidderUserId FROM auctions WHERE auctionId = ?";
            Object[] params = new Object[]{auctionId};
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

            if (!rows.isEmpty()) {
                Map<String, Object> row = rows.get(0);

                // Check for null values before accessing them
                if (row.get("highestBid") != null) {
                    forwardAuction.setHighestBid((Double) row.get("highestBid"));
                }

                if (row.get("highestBidderUserId") != null) {
                    forwardAuction.setHighestBidderUserId((Integer) row.get("highestBidderUserId"));
                }
            }
        }

        return forwardAuction;
    }
}
