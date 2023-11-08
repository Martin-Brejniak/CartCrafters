package com.auctionmodule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class AuctionDAO {
    // Implement your database connection method here
    // Example: Connection conn = YourDatabaseConnection.connect();

    // Insert a new auction record
    public void insertAuction(Auction auction) {
        String sql = "INSERT INTO auctions (itemId, auctionType, initialPrice, currentPrice, " +
                     "startTimeOfAuction, endTimeOfAuction, auctionEnded, soldToUserId) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, auction.getItemId());
            pstmt.setString(2, auction.getAuctionType());
            pstmt.setDouble(3, auction.getInitialPrice());
            pstmt.setDouble(4, auction.getCurrentPrice());
            pstmt.setTimestamp(5, new java.sql.Timestamp(auction.getStartTimeOfAuction().getTime()));
            pstmt.setTimestamp(6, new java.sql.Timestamp(auction.getEndTimeOfAuction().getTime()));
            pstmt.setBoolean(7, auction.isAuctionEnded());
            pstmt.setInt(8, auction.getSoldToUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update an existing auction record
    public void updateAuction(Auction auction) {
        String sql = "UPDATE auctions SET itemId = ?, auctionType = ?, initialPrice = ?, " +
                     "currentPrice = ?, startTimeOfAuction = ?, endTimeOfAuction = ?, " +
                     "auctionEnded = ?, soldToUserId = ? WHERE auctionId = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, auction.getItemId());
            pstmt.setString(2, auction.getAuctionType());
            pstmt.setDouble(3, auction.getInitialPrice());
            pstmt.setDouble(4, auction.getCurrentPrice());
            pstmt.setTimestamp(5, new java.sql.Timestamp(auction.getStartTimeOfAuction().getTime()));
            pstmt.setTimestamp(6, new java.sql.Timestamp(auction.getEndTimeOfAuction().getTime()));
            pstmt.setBoolean(7, auction.isAuctionEnded());
            pstmt.setInt(8, auction.getSoldToUserId());
            pstmt.setInt(9, auction.getAuctionId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete an auction record by auctionId
    public void deleteAuction(int auctionId) {
        String sql = "DELETE FROM auctions WHERE auctionId = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, auctionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieve an auction by auctionId
    public Auction getAuctionById(int auctionId) {
        String sql = "SELECT * FROM auctions WHERE auctionId = ?";
        Auction auction = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, auctionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    auction = new Auction();
                    auction.setAuctionId(rs.getInt("auctionId"));
                    auction.setItemId(rs.getInt("itemId"));
                    auction.setAuctionType(rs.getString("auctionType"));
                    auction.setInitialPrice(rs.getDouble("initialPrice"));
                    auction.setCurrentPrice(rs.getDouble("currentPrice"));
                    auction.setStartTimeOfAuction(rs.getTimestamp("startTimeOfAuction"));
                    auction.setEndTimeOfAuction(rs.getTimestamp("endTimeOfAuction"));
                    auction.setAuctionEnded(rs.getBoolean("auctionEnded"));
                    auction.setSoldToUserId(rs.getInt("soldToUserId"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return auction;
    }

    public List<Auction> getAllAuctions() {
        String sql = "SELECT * FROM auctions";
        List<Auction> auctions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
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
                auctions.add(auction);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return auctions;
    }
}