package com.example.auctionserver.exceptions;

public class InvalidBidException extends RuntimeException {

    public InvalidBidException(String message) {
        super(message);
    }
}
