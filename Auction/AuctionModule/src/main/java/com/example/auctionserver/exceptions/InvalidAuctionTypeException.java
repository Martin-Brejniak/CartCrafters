package com.example.auctionserver.exceptions;

public class InvalidAuctionTypeException extends RuntimeException {
    public InvalidAuctionTypeException(String message) {
        super(message);
    }
}

