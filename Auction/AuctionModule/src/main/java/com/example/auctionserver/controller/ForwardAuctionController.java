package com.example.auctionserver.controller;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.ForwardAuction;
import com.example.auctionserver.service.ForwardAuctionUpdate;
import com.example.auctionserver.service.ForwardAuctionSearch;
import com.example.auctionserver.entity.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/get-all-open")
    public List<Auction> getAllOpenForwardAuctions() {
        return forwardAuctionSearch.getAllForwardAuctions();
    }

    @GetMapping("/details")
    public Auction getForwardAuctionDetails(@RequestParam("auctionId") int auctionId) {
        return forwardAuctionSearch.getForwardAuctionDetails(auctionId);
    }

    @GetMapping("/user/winner")
    public boolean isUserWinner(@RequestParam("auctionId") int auctionId, @RequestParam("userId") int userId) {
        return forwardAuctionSearch.isUserWinner(auctionId, userId);
    }


    @PostMapping("/bid")
    public Auction placeBid(@RequestBody Bid bid) {
        return forwardAuctionUpdate.placeBid(bid.getAuctionId(), bid.getUserId(), bid.getBidAmount());
    }

    @PostMapping("/close")
    public Auction closeAuction(@RequestParam("auctionId") int auctionId) {
        return forwardAuctionUpdate.closeAuction(auctionId);
    }
}
