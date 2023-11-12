package com.example.catalogueserver.controller;


import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.catalogueserver.entity.Auction;
import com.example.catalogueserver.entity.DutchBuy;
import com.example.catalogueserver.service.DutchAuctionSearch;
import com.example.catalogueserver.service.DutchAuctionUpdate;


@RestController
@CrossOrigin
@RequestMapping("/auction/dutch")
public class DutchAuctionController {

    private final DutchAuctionSearch dutchAuctionSearch;
    private final DutchAuctionUpdate dutchAuctionUpdate;

    @Autowired
    public DutchAuctionController(DutchAuctionSearch dutchAuctionSearch, DutchAuctionUpdate dutchAuctionUpdate) {
        this.dutchAuctionSearch = dutchAuctionSearch;
        this.dutchAuctionUpdate = dutchAuctionUpdate;
    }

    @GetMapping("/get-all")
    public List<Auction> getAllDutchAuctions() {
        return dutchAuctionSearch.getAllDutchAuctions();
    }
    
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/get-all-open")
    public List<Auction> getAllOpenDutchAuctions() {
        return dutchAuctionSearch.getAllOpenDutchAuctions();
    }

    @GetMapping("/details")
    public Auction getDutchAuctionDetails(@RequestParam("auctionId") int auctionId) {
        return dutchAuctionSearch.getDutchAuctionDetails(auctionId);
    }

    @GetMapping("/user/winner")
    public boolean isUserWinner(@RequestParam("auctionId") int auctionId, @RequestParam("userId") int userId) {
        return dutchAuctionSearch.isUserWinner(auctionId, userId);
    }

    @PostMapping("/search/items")
    public List<Auction> searchDutchAuctionsByItems(@RequestBody List<String> itemIds) throws Exception {
        try {
            return dutchAuctionSearch.searchDutchAuctionsByItems(itemIds);
        } catch (NoSuchElementException e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/decrement")
    public Auction decrementPrice(@RequestParam("auctionId") int auctionId) {
        return dutchAuctionUpdate.decrementPrice(auctionId);
    }

    @PostMapping("/close")
    public Auction closeAuction(@RequestParam("auctionId") int auctionId) {
        return dutchAuctionUpdate.closeAuction(auctionId);
    }

    @PostMapping("/buy")
    public Auction buyAuction(@RequestBody DutchBuy buy) throws Exception {
        try {
            return dutchAuctionUpdate.buyAuction(buy.getAuctionId(), buy.getUserId());
        } catch (AccessDeniedException e) {
            throw new Exception("Could not buy the auction.");
        }
    }
}
