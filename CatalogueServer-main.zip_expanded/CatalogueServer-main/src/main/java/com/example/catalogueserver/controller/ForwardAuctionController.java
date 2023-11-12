package com.example.catalogueserver.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.catalogueserver.entity.Auction;
import com.example.catalogueserver.entity.Bid;
import com.example.catalogueserver.service.ForwardAuctionSearch;
import com.example.catalogueserver.service.ForwardAuctionUpdate;

@RestController
@CrossOrigin
@RequestMapping("/auction/forward")
public class ForwardAuctionController {

    private final ForwardAuctionSearch forwardAuctionSearch;
    private final ForwardAuctionUpdate forwardAuctionUpdate;

    @Autowired
    public ForwardAuctionController(ForwardAuctionSearch forwardAuctionSearch, ForwardAuctionUpdate forwardAuctionUpdate) {
        this.forwardAuctionSearch = forwardAuctionSearch;
        this.forwardAuctionUpdate = forwardAuctionUpdate;
    }

    @GetMapping("/get-all")
    public List<Auction> getAllForwardAuctions() {
        return forwardAuctionSearch.getAllForwardAuctions();
    }

    @GetMapping("/details")
    public Auction getForwardAuctionDetails(@RequestParam("auctionId") int auctionId) {
        Auction auction = forwardAuctionSearch.getForwardAuctionDetails(auctionId);
        if (auction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found or not a forward auction");
        }
        return auction;
    }

    @GetMapping("/bids")
    public List<Bid> getBidsForForwardAuction(@RequestParam("auctionId") int auctionId) {
        return forwardAuctionSearch.getAllBidsForForwardAuction(auctionId);
    }

    @PostMapping("/place-bid")
    public Auction placeBid(@RequestParam("auctionId") int auctionId, @RequestParam("userId") int userId, @RequestParam("bidAmount") double bidAmount) {
        return forwardAuctionUpdate.placeBid(auctionId, userId, bidAmount);
    }

    @PostMapping("/close")
    public Auction closeAuction(@RequestParam("auctionId") int auctionId) {
        return forwardAuctionUpdate.closeAuction(auctionId);
    }

    @GetMapping("/user/winner")
    public boolean isUserWinner(@RequestParam("auctionId") int auctionId, @RequestParam("userId") int userId) {
        return forwardAuctionSearch.isUserWinner(auctionId, userId);
    }

}
