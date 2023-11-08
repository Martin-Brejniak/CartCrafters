package com.auctionmodule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class BidDAO {

    public List<Bid> getBidsByAuctionId(int auctionId) {
        String sql = "SELECT bidId, userId, bidAmount FROM bids WHERE auctionId = ?";
        List<Bid> bids = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, auctionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Bid bid = new Bid();
                    bid.setBidId(rs.getInt("bidId"));
                    bid.setUserId(rs.getInt("userId"));
                    bid.setBidAmount(rs.getDouble("bidAmount"));
                    bids.add(bid);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bids;
    }

    public void placeBid(Bid bid) {
        String sql = "INSERT INTO bids(auctionId, userId, bidAmount) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bid.getAuctionId());
            pstmt.setInt(2, bid.getUserId());
            pstmt.setDouble(3, bid.getBidAmount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Bid> getBidsByUserId(int userId) {
        String sql = "SELECT bidId, auctionId, bidAmount FROM bids WHERE userId = ?";
        List<Bid> bids = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Bid bid = new Bid();
                    bid.setBidId(rs.getInt("bidId"));
                    bid.setAuctionId(rs.getInt("auctionId"));
                    bid.setBidAmount(rs.getDouble("bidAmount"));
                    bids.add(bid);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bids;
    }
}
