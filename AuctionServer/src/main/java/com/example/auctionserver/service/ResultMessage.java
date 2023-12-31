package com.example.auctionserver.service;

public class ResultMessage {
    private boolean success;
    private String message;

    public ResultMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
