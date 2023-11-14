package com.example.auctionserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardAuctionViewController {

    @GetMapping("/forward-auction")
    public String forwardAuctionPage() {
        return "forward-auction"; 
    }
    
    @GetMapping("/dutch-auction")
    public String dutchAuctions() {
        return "dutch-auction";
    }
}
