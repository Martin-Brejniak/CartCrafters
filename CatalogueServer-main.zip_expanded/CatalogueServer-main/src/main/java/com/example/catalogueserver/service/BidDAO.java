package com.example.catalogueserver.service;

import com.example.catalogueserver.entity.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BidDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BidDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Bid> bidRowMapper = (rs, rowNum) -> {
        Bid bid = new Bid();
        bid.setBidId(rs.getInt("bidId"));
        bid.setAuctionId(rs.getInt("auctionId"));
        bid.setUserId(rs.getInt("userId"));
        bid.setBidAmount(rs.getDouble("bidAmount"));
        return bid;
    };

    public List<Bid> getBidsByAuctionId(int auctionId) {
        String sql = "SELECT bidId, userId, bidAmount FROM bids WHERE auctionId = ?";
        return jdbcTemplate.query(sql, new Object[]{auctionId}, bidRowMapper);
    }

    public void placeBid(Bid bid) {
        String sql = "INSERT INTO bids (auctionId, userId, bidAmount) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, bid.getAuctionId(), bid.getUserId(), bid.getBidAmount());
    }

    public List<Bid> getBidsByUserId(int userId) {
        String sql = "SELECT bidId, auctionId, bidAmount FROM bids WHERE userId = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, bidRowMapper);
    }
}
