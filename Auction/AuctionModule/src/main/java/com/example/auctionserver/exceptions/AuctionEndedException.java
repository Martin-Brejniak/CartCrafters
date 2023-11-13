package com.example.auctionserver.exceptions;

public class AuctionEndedException extends RuntimeException {
    public AuctionEndedException(String message) {
        super(message);
    }
}
