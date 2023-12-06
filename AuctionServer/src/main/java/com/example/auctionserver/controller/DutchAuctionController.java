package com.example.auctionserver.controller;


import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.DutchAuction;
import com.example.auctionserver.entity.DutchBuy;
import com.example.auctionserver.exceptions.AuctionEndedException;
import com.example.auctionserver.exceptions.AuctionNotFoundException;
import com.example.auctionserver.exceptions.InvalidAuctionTypeException;
import com.example.auctionserver.service.DutchAuctionSearch;
import com.example.auctionserver.service.DutchAuctionUpdate;
import com.example.auctionserver.service.ResultMessage;


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
   

    @GetMapping("/get-all-open")
    public List<Auction> getAllOpenDutchAuctions() {
        return dutchAuctionSearch.getAllOpenDutchAuctions();
    }

    @GetMapping("/details")
    public Auction getDutchAuctionDetails(@RequestParam("auctionId") int auctionId) {
        return dutchAuctionSearch.getDutchAuctionDetails(auctionId);
    }

    @GetMapping("/user/winner")
    public ResultMessage isUserWinner(@RequestParam("auctionId") int auctionId, @RequestParam("userId") int userId) {
        return dutchAuctionSearch.isUserWinner(auctionId, userId);
    }

    @PostMapping("/decrement")
    public ResponseEntity<?> decrementPrice(@RequestParam("auctionId") int auctionId) {
        try {
            DutchAuction result = dutchAuctionUpdate.decrementPrice(auctionId);
            return ResponseEntity.ok(result);
        } catch (AuctionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidAuctionTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AuctionEndedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

	/*
	 * @PostMapping("/close") public Auction closeAuction(@RequestParam("auctionId")
	 * int auctionId) { return dutchAuctionUpdate.closeAuction(auctionId); }
	 */

    @PostMapping("/buy")
    public ResponseEntity<Object> buyAuction(@RequestBody DutchBuy buy) {
        try {
            Auction result = dutchAuctionUpdate.buyAuction(buy.getAuctionId(), buy.getUserId());
            return ResponseEntity.ok(result);
        } catch (AuctionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction not found with ID: " + buy.getAuctionId());
        } catch (AuctionEndedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Auction has already ended.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Could not buy the auction.");
        }
    }

}
