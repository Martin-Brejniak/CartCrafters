package com.example.auctionserver.service;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.DutchAuction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class AuctionDAO {

	public JdbcTemplate jdbcTemplate;

	@Autowired
	public AuctionDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private RowMapper<Auction> auctionRowMapper = (rs, rowNum) -> {
		Auction auction = new Auction();
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

	public void insertAuction(Auction auction) {
		String sql = "INSERT INTO auctions (itemId, auctionType, initialPrice, currentPrice, startTimeOfAuction, endTimeOfAuction, auctionEnded, soldToUserId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, auction.getItemId(), auction.getAuctionType(), auction.getInitialPrice(),
				auction.getCurrentPrice(), new Timestamp(auction.getStartTimeOfAuction().getTime()),
				new Timestamp(auction.getEndTimeOfAuction().getTime()), auction.isAuctionEnded(),
				auction.getSoldToUserId());
	}

	public void updateAuction(Auction auction) {
		String sql = "UPDATE auctions SET itemId = ?, auctionType = ?, initialPrice = ?, currentPrice = ?, startTimeOfAuction = ?, endTimeOfAuction = ?, auctionEnded = ?, soldToUserId = ? WHERE auctionId = ?";
		jdbcTemplate.update(sql, auction.getItemId(), auction.getAuctionType(), auction.getInitialPrice(),
				auction.getCurrentPrice(), new Timestamp(auction.getStartTimeOfAuction().getTime()),
				new Timestamp(auction.getEndTimeOfAuction().getTime()), auction.isAuctionEnded(),
				auction.getSoldToUserId(), auction.getAuctionId());
	}

	public void deleteAuction(int auctionId) {
		String sql = "DELETE FROM auctions WHERE auctionId = ?";
		jdbcTemplate.update(sql, auctionId);
	}

	public Auction getAuctionById(int auctionId) {
	    try {
	        String sql = "SELECT * FROM auctions WHERE auctionId = ?";
	        return jdbcTemplate.queryForObject(sql, new Object[]{auctionId}, auctionRowMapper);
	    } catch (EmptyResultDataAccessException e) {
	        // Handle the case where no result is found
	        return null;
	    }
	}

	public List<Auction> getAllAuctions() {
		String sql = "SELECT * FROM auctions";
		return jdbcTemplate.query(sql, auctionRowMapper);
	}

	public List<Auction> getAuctionsByType(String auctionType) {
	    String sql = "SELECT * FROM auctions WHERE auctionType = ?";
	    return jdbcTemplate.query(
	        sql,
	        new Object[]{auctionType},
	        (rs, rowNum) -> {
	            Auction auction = new Auction();
	            auction.setAuctionId(rs.getInt("auctionId"));
	            auction.setItemId(rs.getInt("itemId"));
	            auction.setAuctionType(rs.getString("auctionType"));
	            auction.setInitialPrice(rs.getDouble("initialPrice"));
	            auction.setCurrentPrice(rs.getDouble("currentPrice"));
	            auction.setStartTimeOfAuction(rs.getTimestamp("startTimeOfAuction"));
	            auction.setEndTimeOfAuction(rs.getTimestamp("endTimeOfAuction"));
	            auction.setAuctionEnded(rs.getBoolean("auctionEnded"));
	            auction.setSoldToUserId(rs.getInt("soldToUserId"));
	            // Set other fields as necessary
	            return auction;
	        }
	    );
	}



}
