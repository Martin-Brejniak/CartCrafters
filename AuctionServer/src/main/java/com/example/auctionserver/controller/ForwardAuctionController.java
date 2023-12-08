package com.example.auctionserver.controller;

import com.example.auctionserver.entity.Auction;
import com.example.auctionserver.entity.ForwardAuction;
import com.example.auctionserver.exceptions.AuctionEndedException;
import com.example.auctionserver.exceptions.AuctionNotFoundException;
import com.example.auctionserver.exceptions.InvalidAuctionTypeException;
import com.example.auctionserver.exceptions.InvalidBidException;
import com.example.auctionserver.service.ForwardAuctionUpdate;
import com.example.auctionserver.service.ResultMessage;
import com.example.auctionserver.service.ForwardAuctionDAO;
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
	private final ForwardAuctionDAO forwardAuctionDAO;

	@Autowired
	public ForwardAuctionController(ForwardAuctionSearch forwardAuctionSearch,
			ForwardAuctionUpdate forwardAuctionUpdate, ForwardAuctionDAO forwardAuctionDAO) {
		this.forwardAuctionSearch = forwardAuctionSearch;
		this.forwardAuctionUpdate = forwardAuctionUpdate;
		this.forwardAuctionDAO = forwardAuctionDAO;
	}
    @GetMapping("/get-all")
    public List<Auction> getAllForwardAuctions() {
        return forwardAuctionSearch.getAllForwardAuctions();
    }

    @GetMapping("/get-all-open")
    public List<Auction> getAllOpenForwardAuctions() {
        return forwardAuctionSearch.getAllOpenForwardAuctions();
    }

    @GetMapping("/details")
    public ResponseEntity<?> getForwardAuctionDetails(@RequestParam("auctionId") int auctionId) {
        try {
            Auction auction = forwardAuctionSearch.getForwardAuctionDetails(auctionId);
            return ResponseEntity.ok(auction);
        } catch (AuctionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidAuctionTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
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
    public ResponseEntity<Object> closeAuction(@RequestParam("auctionId") int auctionId) {
        try {
            Auction closedAuction = forwardAuctionUpdate.closeAuction(auctionId);
            return ResponseEntity.ok(closedAuction);
        } catch (AuctionEndedException e) {
            // Handle the case where the auction has already ended
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AuctionNotFoundException e) {
            // Handle the case where the auction is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
    @PostMapping("/create")
    public void createForwardAuction(@RequestBody ForwardAuction auction) {
        forwardAuctionDAO.createForwardAuction(auction);
    }
}
