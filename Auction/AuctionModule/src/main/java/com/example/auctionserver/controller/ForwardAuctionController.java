package com.example.auctionserver.controller;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.ForwardAuction;
import com.example.auctionserver.exceptions.AuctionEndedException;
import com.example.auctionserver.exceptions.AuctionNotFoundException;
import com.example.auctionserver.exceptions.InvalidBidException;
import com.example.auctionserver.service.ForwardAuctionUpdate;
import com.example.auctionserver.service.ResultMessage;
import com.example.auctionserver.service.ForwardAuctionSearch;
import com.example.auctionserver.entity.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	/*
	 * @GetMapping("/hello") public String sayHello() { return "Hello, World!"; }
	 */

    @GetMapping("/get-all-open")
    public List<Auction> getAllOpenForwardAuctions() {
        return forwardAuctionSearch.getAllForwardAuctions();
    }

    @GetMapping("/details")
    public Auction getForwardAuctionDetails(@RequestParam("auctionId") int auctionId) {
        return forwardAuctionSearch.getForwardAuctionDetails(auctionId);
    }

    @GetMapping("/user/winner")
    public ResultMessage isUserWinner(@RequestParam("auctionId") int auctionId, @RequestParam("userId") int userId) {
        return forwardAuctionSearch.isUserWinner(auctionId, userId);
    }



    @PostMapping("/bid")
    public ResponseEntity<?> placeBid(@RequestBody Bid bid) {
        try {
            Auction auction = forwardAuctionUpdate.placeBid(bid.getAuctionId(), bid.getUserId(), bid.getBidAmount());
            return new ResponseEntity<>(auction, HttpStatus.OK);
        } catch (AuctionNotFoundException e) {
            return new ResponseEntity<>("Auction not found with ID: " + bid.getAuctionId(), HttpStatus.NOT_FOUND);
        } catch (AuctionEndedException e) {
            return new ResponseEntity<>("Auction has already ended.", HttpStatus.BAD_REQUEST);
        } catch (InvalidBidException e) {
            return new ResponseEntity<>("Invalid bid amount. Bid must be higher than the current price.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the bid.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







    @PostMapping("/close")
    public Auction closeAuction(@RequestParam("auctionId") int auctionId) {
        return forwardAuctionUpdate.closeAuction(auctionId);
    }
}
